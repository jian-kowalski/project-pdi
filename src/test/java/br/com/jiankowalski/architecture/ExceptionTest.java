package br.com.jiankowalski.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "br.com.jiankowalski")
public class ExceptionTest {

  @ArchTest
  static ArchRule naoDeveLancarExceptionGenerica =
      noClasses().should(GeneralCodingRules.THROW_GENERIC_EXCEPTIONS);
}
