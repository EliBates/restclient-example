package com.elibates.restclient.json;

import java.util.Arrays;

public class JokeValue {
  private int id;
  private String joke;
  private String[] categories;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getJoke() {
    return joke;
  }

  public void setJoke(String joke) {
    this.joke = joke;
  }

  public String[] getCategories() {
    return categories;
  }

  public void setCategories(String[] categories) {
    this.categories = categories;
  }

  @Override
  public String toString() {
    return "JokeValue{" +
        "id=" + id +
        ", joke='" + joke + '\'' +
        ", categories=" + Arrays.toString(categories) +
        '}';
  }
}
