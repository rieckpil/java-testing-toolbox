package de.rieckpil.blog.portfolio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

  private final ObjectMapper objectMapper;

  public StockController(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  // Dummy implementation that would reside in a separate project/application
  @GetMapping
  @RequestMapping("/{symbol}/price")
  public ObjectNode getStockPrice(@PathVariable("symbol") String symbol) {
    return objectMapper.createObjectNode()
      .put("symbol", symbol)
      .put("price", 42.42)
      .put("currency", "USD")
      .put("timestamp", "2020-01-01T12:00:00Z");
  }
}
