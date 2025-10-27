package es.pausegarra.russian_cheatsheet.arch;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.enterprise.context.ApplicationScoped;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
  packages = BaseArchTest.BASE_PACKAGE,
  importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class ArchitectureTests extends BaseArchTest {

  @ArchTest
  static final ArchRule services_should_reside_in_application = classes().that()
    .areAnnotatedWith(ApplicationScoped.class)
    .and()
    .implement(UseCase.class)
    .should()
    .resideInAnyPackage(APPLICATION_PACKAGE);

  @ArchTest
  static final ArchRule services_should_implement_service = classes().that()
    .areAnnotatedWith(ApplicationScoped.class)
    .and()
    .haveSimpleNameEndingWith(SERVICE)
    .should()
    .implement(UseCase.class);

}
