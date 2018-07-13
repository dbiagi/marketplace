package org.dbiagi.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dbiagi.marketplace.entity.Store;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListingDTO {
    private Long id;

    private String title;

    private String slug;

    private boolean featured;

    private boolean active;

    private String shortDescription;

    private String longDescription;

    private Store store;

    private List<Long> categories = new LinkedList<>();

    private List<Long> tags = new LinkedList<>();
}
