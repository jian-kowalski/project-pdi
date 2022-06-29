package br.com.jiankowalski.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.mapstruct.Mapper;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "br.com.jiankowalski")
public class PackageTest {

  @ArchTest
  static final ArchRule classesAnotadasComEntityDeveFicarNoPacoteModel =
      classes()
          .that()
          .areAnnotatedWith(Document.class)
          .should()
          .resideInAPackage("..domain..model..")
          .as("Classes anotadas com @Document deve fica no pacote {domain.model} ");

  @ArchTest
  static final ArchRule classesAnotadasComRepositoryDeveFicarNoPacoteRepository =
      classes()
          .that()
          .areAnnotatedWith(Repository.class)
          .should()
          .resideInAPackage("..domain..repository..")
          .as("Classes anotadas com @Repository deve fica no pacote {domain.repository} ");

  @ArchTest
  static final ArchRule classesAnotadasComServiceDeveFicarNoPacoteService =
      classes()
          .that()
          .areAnnotatedWith(Service.class)
          .should()
          .resideInAPackage("..domain..service..")
          .as("Classes anotadas com @Service deve fica no pacote {domain.service} ");

  @ArchTest
  static final ArchRule classesAnotadasComRestControllerDeveFicarNoPacoteController =
      classes()
          .that()
          .areAnnotatedWith(RestController.class)
          .should()
          .resideInAPackage("..api..controller..")
          .as("Classes anotadas com @RestController deve fica no pacote {api.controller} ");

  @ArchTest
  static final ArchRule classesAnotadasComMappperDeveFicarNoPacoteMappers =
      classes()
          .that()
          .areAnnotatedWith(Mapper.class)
          .should()
          .resideInAPackage("..api..mapper..")
          .as("Classes anotadas com @Mapper deve fica no pacote {api.controller.mapper} ");
}
