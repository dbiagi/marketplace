package org.dbiagi.marketplace.entity;

import org.junit.Test;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.*;

@Tag("model")
public class SettingTest extends EntityTest {
    @Test
    public void testValidSetting() {
        Setting setting = new Setting("config", "value");

        Set<ConstraintViolation<Setting>> violations = validator.validate(setting);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidSetting() {
        Setting setting = new Setting("", "");

        Set<ConstraintViolation<Setting>> violations = validator.validate(setting);

        assertEquals(2, violations.size());
    }
}
