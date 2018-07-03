package org.dbiagi.marketplace.dto;

import lombok.Data;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Tag;

import java.util.List;

@Data
public class ListingDTO {
    private Long id;

    private String name;

    private String shortDescription;

    private String longDescription;

    private Store store;

    private List<Category> categories;

    private List<Tag> tags;

    public interface ListingWithTagsAndCategories{}
}
