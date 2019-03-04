package org.dbiagi.marketplace.entity;

import org.junit.Test;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Tag("model")
public class SettingTest extends EntityTest {
    @Test
    public void given_ValidSetting_When_Validating_Then_ViolationArrayShouldBeEmpty() {
        Setting setting = new Setting("config", "value");

        Set<ConstraintViolation<Setting>> violations = validator.validate(setting);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void given_InvalidSetting_When_Validating_Then_ViolationArrayShouldNotBeEmpty() {
        Setting setting = new Setting("", "");

        Set<ConstraintViolation<Setting>> violations = validator.validate(setting);

        assertEquals(2, violations.size());
    }
}
