package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.BaseSpringRunner;
import org.dbiagi.marketplace.entity.Listing;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ListingRestRepositoryTest extends BaseSpringRunner {

    private Listing getValidListing() {
        Listing listing = new Listing();
        listing.setTitle(faker.lorem().sentence(2));
        listing.setShortDescription(faker.lorem().sentence(5));
        listing.setLongDescription(faker.lorem().paragraph(2));

        return listing;
    }

    @Test
    void given_ValidListing_When_Posting_Should_ReturnStatusCreated() throws Exception {
        Listing listing = getValidListing();

        mvc.perform(post(LISTINGS_URI)
            .header("Authorization", basicAuth("admin", "123"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(listing)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.shortDescription").value(listing.getShortDescription()));
    }

}
