package org.dbiagi.marketplace.entity.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
public @interface Username {
    String message() default "{org.dbiagi.marketplace.entity.constraints.Username.default}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
