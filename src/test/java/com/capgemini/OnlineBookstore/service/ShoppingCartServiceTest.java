package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartServiceTest {
    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    ShoppingCartService shoppingCartService;

    @Test
    public void testGetCart(){

    }

}