package de.rieckpil.blog.portfolio;


import org.springframework.web.reactive.function.client.WebClient;

public class StockApiClient {
  private final WebClient webClient;

  public StockApiClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public StockPrice getStockPrice(String symbol) {
    return webClient.get()
      .uri("/api/stocks/{symbol}/price", symbol)
      .retrieve()
      .bodyToMono(StockPrice.class)
      .block();
  }
}
