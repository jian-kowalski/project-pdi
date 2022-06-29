package br.com.jiankowalski.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(
    packages = "br.com.jiankowalski",
    importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTest {

  @ArchTest
  public static final ArchRule architectureValidator =
      Architectures.layeredArchitecture()
          .layer("Api")
          .definedBy("br.com.jiankowalski.api..")
          .layer("Service")
          .definedBy("br.com.jiankowalski.domain.service..")
          .layer("Repository")
          .definedBy("br.com.jiankowalski.domain.repository..")
          .layer("Core")
          .definedBy("br.com.jiankowalski.core..")
          .whereLayer("Api")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("Service")
          .mayOnlyBeAccessedByLayers("Api")
          .whereLayer("Repository")
          .mayOnlyBeAccessedByLayers("Service")
          .whereLayer("Core")
          .mayOnlyBeAccessedByLayers("Service");
}
