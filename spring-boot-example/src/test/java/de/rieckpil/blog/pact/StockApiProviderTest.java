package au.com.dius.pactworkshop.provider;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import de.rieckpil.blog.Application;
import org.apache.hc.core5.http.HttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Disabled("Requires running Pact Broker, see Docker Compose")
@Provider("stock-api")
@PactBroker(url = "http://localhost:9292/", authentication =  @PactBrokerAuth(username = "pact-sample", password = "pact-sample"))
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
public class StockApiProviderTest {

  @LocalServerPort
  int port;

  @BeforeEach
  void setUp(PactVerificationContext context) {
    context.setTarget(new HttpTestTarget("localhost", port));
  }

  @TestTemplate
  @ExtendWith(PactVerificationInvocationContextProvider.class)
  void verifyPact(PactVerificationContext context, HttpRequest request) {
    context.verifyInteraction();
  }

  @State("Stock AAPL exists and market is open")
  void toAppleStockExists() {
    // Prepare provider data for the test, e.g., inserting stock data into a mock database
  }
}
