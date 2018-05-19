package org.dbiagi.marketplace.core.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.*;

@Tag("entity")
public class StoreTest extends EntityTest {
    @Test
    public void testInvalidEntity() {
        Store store = new Store();

        Set<ConstraintViolation<Store>> result = validator.validate(store);

        Assert.assertTrue(result.size() > 0);
    }

    @Test
    public void testValidEntity() {
        Store store = new Store();

        store.setEmail(faker.internet().emailAddress());
        store.setName(faker.lorem().sentence(2));
        store.setCnpj(faker.numerify("############"));
        store.setPhone(faker.phoneNumber().phoneNumber());
        store.setCellphone(faker.phoneNumber().cellPhone());

        Set<ConstraintViolation<Store>> result = validator.validate(store);

        assertEquals(0, result.size());
    }
}
