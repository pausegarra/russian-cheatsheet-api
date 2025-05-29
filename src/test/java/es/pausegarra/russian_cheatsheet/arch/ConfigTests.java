package es.pausegarra.russian_cheatsheet.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;

@AnalyzeClasses(
  packages = BaseArchTest.BASE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class ConfigTests extends BaseArchTest {

  //  @ArchTest
  //  static final ArchRule config_should_be_in_the_right_package = classes().that()
  //      .haveSimpleNameEndingWith("Config")
  //      .should()
  //      .resideInAPackage(CONFIG_PACKAGE);

}
