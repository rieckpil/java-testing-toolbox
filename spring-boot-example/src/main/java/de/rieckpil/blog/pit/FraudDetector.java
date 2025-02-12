package de.rieckpil.blog.pit;

import java.util.Set;

public class FraudDetector {

  private static final Set<String> HIGH_RISK_COUNTRIES =
    Set.of("WONDERLAND", "MARS", "URANUS");

  public boolean isFraudulentTransaction(double amount, String country) {
    // Any transaction above 10k is flagged
    if (amount > 10_000) {
      return true;
    }

    // Transactions from high-risk countries are flagged
    if (HIGH_RISK_COUNTRIES.contains(country)) {
      return true;
    }

    return false;
  }
}
