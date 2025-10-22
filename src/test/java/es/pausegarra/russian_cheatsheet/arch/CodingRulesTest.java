package es.pausegarra.russian_cheatsheet.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.CompositeArchRule;
import com.tngtech.archunit.library.GeneralCodingRules;
import jakarta.inject.Inject;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

@AnalyzeClasses(
  packages = BaseArchTest.BASE_PACKAGE,
  importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class CodingRulesTest extends BaseArchTest {

  @ArchTest
  static final ArchRule no_classes_should_throw_generic_exceptions = GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

  @ArchTest
  static final ArchRule exceptions_should_be_in_a_exceptions_package = classes().that()
    .areAssignableTo(RuntimeException.class)
    .or()
    .haveSimpleNameEndingWith(EXCEPTION)
    .should()
    .resideInAPackage(ANY_EXCEPTION_PACKAGE);

  //  @ArchTest
  //  static final ArchRule custom_exceptions_should_be_final = classes().that()
  //    .areAssignableTo(RuntimeException.class)
  //    .or()
  //    .resideInAPackage(ANY_EXCEPTION_PACKAGE)
  //    .and()
  //    .resideOutsideOfPackage(BASE_PACKAGE + COMMON_EXCEPTION_PACKAGE)
  //    .and()
  //    .doNotHaveSimpleName(NotFound.class.getSimpleName())
  //    .and()
  //    .doNotHaveSimpleName(BadRequest.class.getSimpleName())
  //    .and()
  //    .doNotHaveSimpleName(Forbidden.class.getSimpleName())
  //    .and()
  //    .doNotHaveSimpleName(Unauthenticated.class.getSimpleName())
  //    .and()
  //    .doNotHaveSimpleName(InternalServerError.class.getSimpleName())
  //    .should()
  //    .haveModifier(FINAL);

  @ArchTest
  static final ArchRule field_injection_should_not_be_used = CompositeArchRule.of(
    GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION
  )
    .and(noFields().should(GeneralCodingRules.BE_ANNOTATED_WITH_AN_INJECTION_ANNOTATION))
    .and(noFields().should().beAnnotatedWith(Inject.class))
    .and(noMethods().should().beAnnotatedWith(Inject.class));

}
