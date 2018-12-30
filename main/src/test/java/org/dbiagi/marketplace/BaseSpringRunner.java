package org.dbiagi.marketplace;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public abstract class BaseSpringRunner {
    public Faker faker;

    @Before
    public void setup() {
        faker = new Faker(Locale.getDefault());
    }
}

