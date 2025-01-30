package de.rieckpil.blog.archunit;

import java.time.Clock;
import java.time.LocalDate;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureTest {

  @Test
  void servicesShouldNotDependOnControllers() {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("de.rieckpil.blog");

    noClasses()
      .that().resideInAPackage("de.rieckpil.blog.service")
      .should().dependOnClassesThat().resideInAPackage("de.rieckpil.blog.controller")
      .check(importedClasses);
  }

  @Test
  void localDateNowShouldUseClock() {
    ArchRule rule = noClasses().should()
      .callMethod(LocalDate.class, "now");

    rule.check(new ClassFileImporter()
      .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
      .importPackages("de.rieckpil"));
  }

  @Test
  void noCyclicDependencies() {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("de.rieckpil");

    SlicesRuleDefinition.slices()
      .matching("de.rieckpil.(*)..")
      .should().beFreeOfCycles()
      .check(importedClasses);
  }

  @Test
  void serviceClassesShouldHaveProperNaming() {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("de.rieckpil");

    ArchRuleDefinition.classes()
      .that().resideInAPackage("de.rieckpil.blog.service")
      .should().haveSimpleNameEndingWith("Service")
      .check(importedClasses);
  }

  @Test
  void shouldNotUseJavaUtilLogging() {
    JavaClasses importedClasses = new ClassFileImporter()
      .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
      .importPackages("de.rieckpil");

    noClasses()
      .should().dependOnClassesThat().belongToAnyOf(java.util.logging.Logger.class)
      .check(importedClasses);
  }

  public static final ArchRule serviceLayerShouldNotAccessRepositoriesDirectly =
    noClasses()
      .that().resideInAPackage("de.rieckpil")
      .should().dependOnClassesThat().resideInAPackage("de.rieckpil");
}
