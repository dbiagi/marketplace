package org.dbiagi.marketplace;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

@SpringBootTest(classes = Application.class)
@ExtendWith(SpringExtension.class)
public abstract class BaseSpringRunner {
    protected static Faker faker;

    @BeforeAll
    public static void setup() {
        faker = new Faker(Locale.getDefault());
    }
}

