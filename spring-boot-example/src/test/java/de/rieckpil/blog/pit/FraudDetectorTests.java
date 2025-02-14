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
  void highAmountTransactionShouldBeFraudulent() {
    FraudDetector detector = new FraudDetector();
    assertTrue(detector.isFraudulentTransaction(15_000, "USA"));
  }

  @Test
  void highRiskCountryTransactionShouldBeFraudulent() {
    FraudDetector detector = new FraudDetector();
    assertTrue(detector.isFraudulentTransaction(500, "MARS"));
  }

//  @Test
//  void lowAmountTransactionShouldNotBeFraudulent() {
//    FraudDetector detector = new FraudDetector();
//    assertFalse(detector.isFraudulentTransaction(1_000, "USA"));
//  }
//
//  @Test
//  void thresholdAmountTransactionShouldNotBeFraudulent() {
//    FraudDetector detector = new FraudDetector();
//    assertFalse(detector.isFraudulentTransaction(10_000, "GER"));
//  }
}
