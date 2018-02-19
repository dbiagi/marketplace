package org.dbiagi.marketplace.core.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class UserTest extends EntityTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    public void testBidirectionalRelationship() throws JsonProcessingException {
        User user = new User();
        Store store = new Store();

        user.setStore(store);
        user.setName("Diego");
        user.setUsername("diego");
        user.setEmail("diego@bol.com.br");

        store.setName("Store 1");
        store.setEmail("store@bol.com.br");

        Assert.assertNotNull(new ObjectMapper().writeValueAsString(store));
        Assert.assertNotNull(new ObjectMapper().writeValueAsString(user));
    }

    @Test
    public void testInvalidEntity() {
        User user = new User();

        Set<ConstraintViolation<User>> result = validator.validate(user);

        Assert.assertTrue(result.size() >= 3);
    }

    @Test
    public void testValidEntity() {
        User user = new User();

        user.setName(faker.name().name());
        user.setUsername(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setStore(new Store());
        user.setPassword(faker.internet().password());
        user.setRole(User.Role.ADMIN);

        Set<ConstraintViolation<User>> result = validator.validate(user);

        Assert.assertTrue(result.size() == 0);
    }
}
