package org.dbiagi.marketplace.core.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dbiagi.marketplace.core.validation.ValidationError;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationErrorResponseTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testSerialization() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ValidationErrorResponse response = new ValidationErrorResponse();

        for (int i = 0; i < 10; i++) {
            response.addValidationError(new ValidationError("00" + i, "field " + i, "Validation Error " + i));
        }

        String result = objectMapper.writeValueAsString(response);
        logger.debug(result);

        Assert.assertTrue(result.length() > 0);
    }
}
