package es.pausegarra.russian_cheatsheet.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
  packages = BaseArchTest.BASE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class ApplicationTests extends BaseArchTest {

  @ArchTest
  static final ArchRule application_classes_should_not_be_accessed_from_domain = classes().that()
    .resideInAPackage(APPLICATION_PACKAGE)
    .should()
    .onlyBeAccessed()
    .byClassesThat()
    .resideOutsideOfPackage(DOMAIN_PACKAGE);

  @ArchTest
  static final ArchRule application_classes_should_not_depend_on_infrastructure = noClasses().that()
    .resideInAPackage(APPLICATION_PACKAGE)
    .should()
    .dependOnClassesThat()
    .resideInAnyPackage(REST_PACKAGE, INFRASTRUCTURE_PACKAGE);

}
