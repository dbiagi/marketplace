package org.dbiagi.marketplace.service;

import com.github.slugify.Slugify;
import org.dbiagi.marketplace.entity.classification.Context;
import org.dbiagi.marketplace.entity.classification.Tag;
import org.dbiagi.marketplace.repository.TagRepository;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TagServiceTest {

    private Tag getTag() {
        Tag tag = new Tag();
        tag.setContext(new Context("default"));
        tag.setName("Tag Name");

        return tag;
    }

    @Test
    public void testPrepare() {
        TagRepository tagRepository = mock(TagRepository.class, RETURNS_MOCKS);

        Tag tag = getTag();

        when(tagRepository.findOneBySlugEquals(""))
            .thenReturn(Optional.of(tag));

        TagService tagService = new TagService(new Slugify(), tagRepository);

        tagService.prepare(tag);

        assertNotNull(tag.getSlug());
        assertFalse(tag.getSlug().isEmpty());
    }
}
