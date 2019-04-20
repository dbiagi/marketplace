package org.dbiagi.marketplace.service;

import com.github.slugify.Slugify;
import org.dbiagi.marketplace.entity.classification.Tag;
import org.dbiagi.marketplace.repository.rest.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {
    private Slugify slugify;
    private TagRepository tagRepository;

    @Autowired
    public TagService(Slugify slugify, TagRepository tagRepository) {
        this.slugify = slugify;
        this.tagRepository = tagRepository;
    }

    private void createSlug(Tag tag) {
        String slug = slugify.slugify((tag.getSlug() == null || tag.getSlug().isEmpty()) ? tag.getName() : tag.getSlug());

        Optional<Tag> optional = tagRepository.findOneBySlugEquals(slug);

        if (optional.isPresent()) {
            slug = String.format("%s-%d", slug, System.currentTimeMillis());
        }

        tag.setSlug(slug);
    }

    public void prepare(Tag tag) {
        createSlug(tag);
    }
}
