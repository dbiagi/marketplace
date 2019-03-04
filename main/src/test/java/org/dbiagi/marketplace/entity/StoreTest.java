package org.dbiagi.marketplace.entity;

import org.junit.Test;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Tag("model")
public class StoreTest extends EntityTest {
    @Test
    public void given_InvalidStore_When_Validating_Then_ViolationArrayShouldNotBeEmpty() {
        Store store = new Store();

        Set<ConstraintViolation<Store>> result = validator.validate(store);

        assertTrue(result.size() > 0);
    }

    @Test
    public void given_ValidStore_When_Validating_Then_ValidationArrayShouldBeEmpty() {
        Store store = new Store();

        store.setEmail(faker.internet().emailAddress());
        store.setName(faker.lorem().sentence(2));
        store.setPhone(faker.phoneNumber().phoneNumber());
        store.setCellphone(faker.phoneNumber().cellPhone());

        Set<ConstraintViolation<Store>> result = validator.validate(store);

        assertEquals(0, result.size());
    }
}
