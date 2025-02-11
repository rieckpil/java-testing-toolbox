package de.rieckpil.blog.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class StringConcatenationBenchmark {

  @Param({"5", "10", "15"})
  private int iterations;

  public static void main(String[] args) throws Exception {
    Options opt = new OptionsBuilder()
      .include(StringConcatenationBenchmark.class.getSimpleName())
      .forks(1)
      .warmupIterations(3)
      .measurementIterations(5)
      .build();

    new Runner(opt).run();
  }

  @Benchmark
  public String testStringBuilder() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < iterations; i++) {
      sb.append("test");
    }
    return sb.toString();
  }

  @Benchmark
  public String testStringConcatenation() {
    String result = "";
    for (int i = 0; i < iterations; i++) {
      result += "test";
    }
    return result;
  }
}
