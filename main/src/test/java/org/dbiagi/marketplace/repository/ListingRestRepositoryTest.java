package org.dbiagi.marketplace.repository;

import org.dbiagi.marketplace.entity.Listing;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ListingRestRepositoryTest extends BaseDataRestTest {

    @Autowired
    private ListingRepository listingRepository;

    private Listing getValidListing() {
        Listing listing = new Listing();
        listing.setTitle(faker.lorem().sentence(2));
        listing.setShortDescription(faker.lorem().sentence(5));
        listing.setLongDescription(faker.lorem().paragraph(2));

        return listing;
    }

    @Test
    public void givenValidListingWhenPostingShouldReturnStatusCreated() throws Exception {
        Listing listing = getValidListing();

        mvc.perform(post(LISTINGS_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(listing)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.shortDescription").value(listing.getShortDescription()));
    }

    @Test
    @Ignore
    public void givenValidListingWhenPutingShouldReturnSuccessStatus() throws Exception {
        List<Listing> listings = listingRepository.findAllFeatured(null);

        listings.isEmpty();
    }
}
