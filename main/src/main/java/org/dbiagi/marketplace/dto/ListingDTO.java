package org.dbiagi.marketplace.dto;

import lombok.Data;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Tag;

import java.util.List;

public class ListingDTO {
    @Data
    public class ListingWithStores {
        private Long id;

        private String name;

        private Store store;

        private List<Category> categories;

        private List<Tag> tags;
    }
}
