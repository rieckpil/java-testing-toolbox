package de.rieckpil.blog.pact;

import java.math.BigDecimal;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import de.rieckpil.blog.portfolio.StockApiClient;
import de.rieckpil.blog.portfolio.StockPrice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.reactive.function.client.WebClient;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "stock-api")
class StockApiContractTest {

  @Pact(consumer = "portfolio-dashboard")
  public V4Pact createPact(PactDslWithProvider builder) {
    // Define what we expect from the Stock API
    return builder
      .given("Stock AAPL exists and market is open")
      .uponReceiving("A request for AAPL stock price")
      .path("/api/stocks/AAPL/price")
      .method("GET")
      .willRespondWith()
      .status(200)
      .body(new PactDslJsonBody()
        .stringType("symbol", "AAPL")
        .decimalType("price", 150.25)
        .stringType("currency", "USD")
        .datetime("timestamp", "yyyy-MM-dd'T'HH:mm:ss'Z'"))
      .toPact(V4Pact.class);
  }

  @Test
  @PactTestFor(pactMethod = "createPact")
  void testGetStockPrice(MockServer mockServer) {
    // Create client pointing to mock server
    StockApiClient client = new StockApiClient(WebClient.builder().baseUrl(mockServer.getUrl()).build());

    // Test the interaction
    StockPrice price = client.getStockPrice("AAPL");

    assertThat(price)
      .isNotNull()
      .satisfies(p -> {
        assertThat(p.getSymbol()).isEqualTo("AAPL");
        assertThat(p.getPrice()).isGreaterThan(BigDecimal.ZERO);
        assertThat(p.getCurrency()).isEqualTo("USD");
      });
  }
}
