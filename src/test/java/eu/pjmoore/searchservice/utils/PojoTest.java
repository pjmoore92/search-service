package eu.pjmoore.searchservice.utils;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterPackageInfo;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class PojoTest {
    // Configured for expectation, so we know when a class gets added or removed.
    private static final int EXPECTED_CLASS_COUNT = 4;

    // The package to test
    private static final String POJO_PACKAGE = "eu.pjmoore.searchservice.model";

    @Test
    void ensureExpectedPojoCount() {
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE,
                new FilterPackageInfo());
        Assertions.assertEquals(EXPECTED_CLASS_COUNT, pojoClasses.size(), "Classes added / removed?");
    }

    @Test
    void testPojoStructureAndBehavior() {
        Validator validator = ValidatorBuilder.create()
                // Add Rules to validate structure for POJO_PACKAGE
                // See com.openpojo.validation.rule.impl for more ...
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                // Add Testers to validate behaviour for POJO_PACKAGE
                // See com.openpojo.validation.test.impl for more ...
                .with(new SetterTester())
                .with(new GetterTester())
                .build();

        validator.validate(POJO_PACKAGE, new FilterPackageInfo());
    }
}
