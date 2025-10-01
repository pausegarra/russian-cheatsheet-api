package es.pausegarra.template.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(
  packages = BaseArchTest.BASE_PACKAGE,
  importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class DomainTest extends BaseArchTest {

  @ArchTest
  static final ArchRule domain_should_not_depend_on_any_other_layer = noClasses().that()
    .resideInAPackage(DOMAIN_PACKAGE)
    .should()
    .dependOnClassesThat()
    .resideInAnyPackage(REST_PACKAGE, INFRASTRUCTURE_PACKAGE, APPLICATION_PACKAGE);

  @ArchTest
  static final ArchRule repositories_in_domain_layer_should_be_interfaces = classes().that()
    .resideInAPackage(DOMAIN_PACKAGE)
    .and()
    .haveSimpleNameEndingWith(REPOSITORY)
    .should()
    .beInterfaces();

}
