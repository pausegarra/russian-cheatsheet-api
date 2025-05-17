package es.pausegarra.russian_cheatsheet.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
  packages = BaseArchTest.BASE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class NamingConventionsTests extends BaseArchTest {

  @ArchTest
  static final ArchRule application_services_should_not_be_suffixed = classes().that()
    .implement(Service.class)
    .and()
    .resideInAPackage(APPLICATION_PACKAGE)
    .should()
    .haveSimpleNameEndingWith(SERVICE)
    .andShould()
    .haveSimpleNameNotEndingWith(SERVICE_IMPL);

  //  @ArchTest
  //  static final ArchRule config_properties_should_be_suffixed = classes().that()
  //      .areAnnotatedWith(ConfigMapping.class)
  //      .should()
  //      .haveSimpleNameEndingWith(CONFIG_PROPERTIES);

}
