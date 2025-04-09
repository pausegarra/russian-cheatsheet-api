package es.pausegarra.russkiy_po_moyemu.arch;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = BaseArchTest.BASE_PACKAGE, importOptions = {ImportOption.DoNotIncludeTests.class})
public class ResourceTest extends BaseArchTest {

  @ArchTest
  static final ArchRule controllers_should_not_be_accessed_by_any_other_layer = classes().that()
    .resideInAPackage(REST_PACKAGE)
    .should()
    .onlyBeAccessed()
    .byAnyPackage(REST_PACKAGE);

}
