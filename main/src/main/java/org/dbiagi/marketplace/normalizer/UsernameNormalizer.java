package org.dbiagi.marketplace.normalizer;

import com.github.slugify.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class UsernameNormalizer {

    private Slugify slugify;

    @Autowired
    public UsernameNormalizer(Slugify slugify){
        this.slugify = slugify;
    }

    public String normalize(String username) {
        return slugify.withLowerCase(true)
            .withUnderscoreSeparator(true)
            .slugify(username);
    }
}
