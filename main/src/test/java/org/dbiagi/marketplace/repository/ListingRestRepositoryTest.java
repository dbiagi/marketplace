package org.dbiagi.marketplace.repository;

import lombok.Builder;
import lombok.Data;
import org.dbiagi.marketplace.entity.Listing;
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

    private ListingVO getValidListing() {
        Listing listing = new Listing();


        return ListingVO.builder()
            .title(faker.lorem().sentence(2))
            .active(true)
            .featured(true)
            .shortDescription(faker.lorem().sentence(5))
            .longDescription(faker.lorem().paragraph(2))
            .build();
    }

    @Test
    public void givenValidListingWhenPostingShouldReturnStatusCreated() throws Exception {
        ListingVO listingVO = getValidListing();

        mvc.perform(post(LISTINGS_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(listingVO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.shortDescription").value(listingVO.shortDescription));
    }

    @Test
    public void givenValidListingWhenPutingShouldReturnSuccessStatus() throws Exception {
        List<Listing> listings = listingRepository.findAllFeatured(null);

        listings.isEmpty();
    }

    @Data
    @Builder
    private static class ListingVO {
        private String shortDescription;
        private String longDescription;
        private String title;
        private boolean active = true;
        private boolean featured = false;
    }
}
