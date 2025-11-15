package es.pausegarra.russian_cheatsheet.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestResponse;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(
  packages = BaseArchTest.BASE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class}
)
public class ResourceTest extends BaseArchTest {

  @ArchTest
  static final ArchRule controllers_should_not_be_accessed_by_any_other_layer = classes().that()
    .resideInAPackage(REST_PACKAGE)
    .should()
    .onlyBeAccessed()
    .byAnyPackage(REST_PACKAGE);

  // @ArchTest
  // static final ArchRule controllers_should_reside_in_rest_layer = classes().that()
  // .areAnnotatedWith(Path.class)
  // .or()
  // .haveSimpleNameEndingWith(RESOURCE)
  // .should()
  // .resideInAPackage(REST_PACKAGE);

  @ArchTest
  static final ArchRule rest_controllers_should_return_response_entities = methods().that()
    .areDeclaredInClassesThat()
    .areAnnotatedWith(Path.class)
    .and()
    .arePublic()
    .should()
    .haveRawReturnType(RestResponse.class);

}
