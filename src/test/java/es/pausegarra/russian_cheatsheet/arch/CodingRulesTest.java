package es.pausegarra.russian_cheatsheet.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(
  packages = BaseArchTest.BASE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class CodingRulesTest extends BaseArchTest {

  @ArchTest
  static final ArchRule no_classes_should_throw_generic_exceptions =
    GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

  @ArchTest
  static final ArchRule exceptions_should_be_in_a_exceptions_package = classes().that()
    .areAssignableTo(RuntimeException.class)
    .or()
    .haveSimpleNameEndingWith(EXCEPTION)
    .should()
    .resideInAPackage(ANY_EXCEPTION_PACKAGE);

  //  @ArchTest
  //  static final ArchRule custom_exceptions_should_be_final = classes().that()
  //      .areAssignableTo(RuntimeException.class)
  //      .or()
  //      .resideInAPackage(ANY_EXCEPTION_PACKAGE)
  //      .and()
  //      .resideOutsideOfPackage(BASE_PACKAGE + COMMON_EXCEPTION_PACKAGE)
  //      .and()
  //      .doNotHaveSimpleName(Forbidden.class.getSimpleName())
  //      .and()
  //      .doNotHaveSimpleName(NotFound.class.getSimpleName())
  //      .and()
  //      .doNotHaveSimpleName(BadRequest.class.getSimpleName())
  //      .should()
  //      .haveModifier(FINAL);

}
