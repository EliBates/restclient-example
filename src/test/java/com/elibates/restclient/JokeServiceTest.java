package com.elibates.restclient;

import com.elibates.restclient.services.JokeService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JokeServiceTest {

  private final Logger logger = LoggerFactory.getLogger(JokeServiceTest.class);

  @Autowired
  private JokeService service;

  @Test
  public void autowiringWorked() {
    assertNotNull(service);
  }

  @Test
  public void getJokeSync() {
    String joke = service.getJokeSync("Eli", "Bates");
    logger.info(joke);
    assertTrue(joke.contains("Eli") || joke.contains("Bates"));
  }

  @Test
  public void getJokeAsync() {
    String joke = Objects.requireNonNull(service.getJokeAsync("Eli", "Bates").block(Duration.ofSeconds(2))).orElse("Joke Not found");
    logger.info(joke);
    assertTrue(joke.contains("Eli") || joke.contains("Bates"));
  }

  @Test
  public void useStepVerifier() {
    StepVerifier.create(service.getJokeAsync("Eli", "Bates"))
        .assertNext(joke -> {
          assertTrue(joke.isPresent());
          logger.info(joke.get());
          assertTrue(joke.get().contains("Eli") || joke.get().contains("Bates"));
        })
        .verifyComplete();
  }
}
