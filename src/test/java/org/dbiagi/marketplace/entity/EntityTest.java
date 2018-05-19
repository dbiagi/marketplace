package org.dbiagi.marketplace.entity;

import com.github.javafaker.Faker;
import org.junit.BeforeClass;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Locale;

public class EntityTest {
    static Validator validator;
    static Faker faker;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        faker = new Faker(new Locale("pt"));
    }
}
