package org.dbiagi.marketplace.normalizer;

import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class UsernameNormalizerTest {

    @ParameterizedTest
    @ValueSource(strings = {"valid_user_name", "se_loco_meu"})
    void givenNormalizedUsernameShouldBeEqualsAfterNormalization(String username) {
        UsernameNormalizer normalizer = new UsernameNormalizer(new Slugify());

        assertEquals(username, normalizer.normalize(username));
    }
}