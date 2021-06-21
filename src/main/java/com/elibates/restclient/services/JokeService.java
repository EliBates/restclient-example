package com.elibates.restclient.services;

import com.elibates.restclient.json.JokeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service  // provides business logic and transaction boundaries
public class JokeService {
  private final RestTemplate template;
  private final WebClient client;

  @Autowired // ask spring if there is a RestTemplateBuilder bean available in app ctx, if so dependency inject it.
  public JokeService(RestTemplateBuilder builder,
                     WebClient.Builder webClientBuilder) {
    template = builder.build();
    client = webClientBuilder.baseUrl("http://api.icndb.com").build();
  }

  /**
   * Synchronously get joke from api
   */
  public String getJokeSync(String first, String last) {
    String base = "http://api.icndb.com/jokes/random?limitTo=[nerdy]";
    String url = String.format("%s&firstName=%s&lastName=%s", base, first, last);

    var joke = Optional.ofNullable(template.getForObject(url, JokeResponse.class));
    return joke.isPresent() ? joke.get().getValue().getJoke() : "Error getting joke";
  }

  /**
   * Asynchronously get joke from api
   */
  public Mono<Optional<String>> getJokeAsync(String first, String last) {
    return client.get()
        .uri(uriBuilder -> uriBuilder.path("/jokes/random")
            .queryParam("limitTo", "[nerdy]")
            .queryParam("firstName", first)
            .queryParam("lastName", last)
            .build())
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(JokeResponse.class)
        .map(jokeResponse -> Optional.ofNullable(jokeResponse.getValue().getJoke()));
  }
}
