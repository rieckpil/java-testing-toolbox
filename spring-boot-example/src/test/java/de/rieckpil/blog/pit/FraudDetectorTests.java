package de.rieckpil.blog.pit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class FraudDetectorTest {

  @Test
  void testHighAmountTransaction() {
    FraudDetector detector = new FraudDetector();

    assertTrue(detector.isFraudulentTransaction(15_000, "USA"));
  }

  @Test
  void testHighRiskCountryTransaction() {
    FraudDetector detector = new FraudDetector();

    assertTrue(detector.isFraudulentTransaction(500, "MARS"));
  }

  @Test
  void testSafeTransaction() {
    FraudDetector cut = new FraudDetector();

    assertFalse(cut.isFraudulentTransaction(200, "USA"));
  }
}
