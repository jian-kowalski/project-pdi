package br.com.jiankowalski.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "br.com.jiankowalski")
public class NameTest {

  @ArchTest
  static final ArchRule classesAnotadasComRepositoryDeveTeroSufixoServiceRespository =
      classes()
          .that()
          .areAnnotatedWith(Repository.class)
          .should()
          .haveSimpleNameEndingWith("Repository")
          .as("Classes anotadas com @Entity deve o sufixo repository ");

  @ArchTest
  static final ArchRule classesAnotadasComServiceDeveTeroSufixoService =
      classes()
          .that()
          .areAnnotatedWith(Service.class)
          .should()
          .haveSimpleNameEndingWith("Service")
          .as("Classes anotadas com @Service deve o sufixo Service ");

  @ArchTest
  static final ArchRule classesAnotadasComRestControllerDeveTeroSufixoServiceController =
      classes()
          .that()
          .areAnnotatedWith(RestController.class)
          .should()
          .haveSimpleNameEndingWith("Controller")
          .as("Classes anotadas com @Entity deve o sufixo Controller ");
}
