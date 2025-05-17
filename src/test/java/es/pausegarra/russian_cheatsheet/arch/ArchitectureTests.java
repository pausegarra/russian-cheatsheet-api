package es.pausegarra.russian_cheatsheet.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import jakarta.enterprise.context.ApplicationScoped;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
  packages = BaseArchTest.BASE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class ArchitectureTests extends BaseArchTest {

  @ArchTest
  static final ArchRule services_should_reside_in_application = classes().that()
    .areAnnotatedWith(ApplicationScoped.class)
    .and()
    .implement(Service.class)
    .should()
    .resideInAnyPackage(APPLICATION_PACKAGE);

  @ArchTest
  static final ArchRule services_should_implement_service = classes().that()
    .areAnnotatedWith(ApplicationScoped.class)
    .and()
    .haveSimpleNameEndingWith(SERVICE)
    .should()
    .implement(Service.class);

}
