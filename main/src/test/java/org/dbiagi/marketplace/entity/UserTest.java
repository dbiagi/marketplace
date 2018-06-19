package org.dbiagi.marketplace.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Tag("model")
public class UserTest extends EntityTest {

    @Test
    public void given_StoreThatHaveUsers_When_SerializeToJson_ShouldNotThrowException()
        throws JsonProcessingException {
        User user = new User();
        Store store = new Store();

        user.setStore(store);
        user.setName("Diego");
        user.setUsername("diego");
        user.setEmail("diego@bol.com.br");

        store.setName("StoreInterface 1");
        store.setEmail("store@bol.com.br");

        assertNotNull(new ObjectMapper().writeValueAsString(store));
        assertNotNull(new ObjectMapper().writeValueAsString(user));
    }

    @Test
    public void given_InvalidUser_When_Validating_Then_ViolationArrayShouldNotBeEmpty() {
        User user = new User();

        Set<ConstraintViolation<User>> result = validator.validate(user);

        assertTrue(result.size() >= 3);
    }

    @Test
    public void given_ValidUser_When_Validating_Then_ViolationArrayShouldBeEmpty() {
        User user = new User();

        user.setName(faker.name().name());
        user.setUsername(faker.name().username());
        user.setEmail(faker.internet().emailAddress());
        user.setStore(new Store());
        user.setPassword(faker.internet().password());
        user.setRole(User.Role.ADMIN);

        Set<ConstraintViolation<User>> result = validator.validate(user);

        assertEquals(0, result.size());
    }

    @Test
    public void when_UsingLombokBuilder_Then_ReturnShouldBeAUser() {
        assertThat(User.builder().build(), isA(User.class));
    }

    @Test
    public void when_UsingLombokBuilder_Then_PropertiesShouldBeSetOnUser() {
        String name = "some name";
        String email = "some email";

        User user = User.builder()
            .name(name)
            .email(email)
            .build();

        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }
}
