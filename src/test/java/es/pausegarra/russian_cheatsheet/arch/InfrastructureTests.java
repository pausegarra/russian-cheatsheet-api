package es.pausegarra.russian_cheatsheet.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
  packages = BaseArchTest.BASE_PACKAGE,
  importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class InfrastructureTests extends BaseArchTest {

  @ArchTest
  static final ArchRule infrastructure_classes_should_not_be_accessed_from_application_or_domain = classes().that()
    .resideInAPackage(INFRASTRUCTURE_PACKAGE)
    .should()
    .onlyBeAccessed()
    .byClassesThat()
    .resideOutsideOfPackages(DOMAIN_PACKAGE, APPLICATION_PACKAGE);

  @ArchTest
  static final ArchRule repository_implementations_should_reside_in_infrastructure_package = classes().that()
    .areNotInterfaces()
    .and()
    .haveSimpleNameEndingWith(REPOSITORY)
    .should()
    .resideInAPackage(INFRASTRUCTURE_PACKAGE)
    .allowEmptyShould(true);

}
