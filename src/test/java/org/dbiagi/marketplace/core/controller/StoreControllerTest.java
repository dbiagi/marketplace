package org.dbiagi.marketplace.core.controller;

import org.dbiagi.marketplace.core.repository.StoreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(StoreController.class)
public class StoreControllerTest extends ControllerTest {

    @MockBean
    private StoreRepository storeRepository;

    @Before
    public void mock() {

    }

    @Test
    public void testAll() throws Exception {

    }

    @Test
    public void testRegister() {

    }
}
