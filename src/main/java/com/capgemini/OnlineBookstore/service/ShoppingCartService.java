package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.CartItem;
import com.capgemini.OnlineBookstore.dto.ShoppingCart;
import com.capgemini.OnlineBookstore.mapper.CartItemResponseMapper;
import com.capgemini.OnlineBookstore.mapper.ShoppingCartResponseMapper;
import com.capgemini.OnlineBookstore.model.BookEntity;
import com.capgemini.OnlineBookstore.model.CartItemEntity;
import com.capgemini.OnlineBookstore.model.ShoppingCartEntity;
import com.capgemini.OnlineBookstore.model.UserEntity;
import com.capgemini.OnlineBookstore.repository.BookRepository;
import com.capgemini.OnlineBookstore.repository.CartItemRepository;
import com.capgemini.OnlineBookstore.repository.ShoppingCartRepository;
import com.capgemini.OnlineBookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    ShoppingCartResponseMapper shoppingCartResponseMapper = new ShoppingCartResponseMapper();
    CartItemResponseMapper cartItemResponseMapper = new CartItemResponseMapper();

    public ShoppingCart getCartByUserId(Long userId) {
        ShoppingCartEntity shoppingCartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found for user: " + userId));
        return shoppingCartResponseMapper.map(shoppingCartEntity);
    }

    public ShoppingCart createShoppingCart(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        ShoppingCartEntity shoppingCartEntity = cartRepository.save(ShoppingCartEntity.builder().user(userEntity).build());
        return shoppingCartResponseMapper.map(shoppingCartEntity);
    }

    public CartItem addItemToCart(Long userId, Long bookId, Integer quantity) {
        ShoppingCartEntity shoppingCartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found for user: " + userId));

        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found for id: " + bookId));

        CartItemEntity cartItemEntity = CartItemEntity.builder()
                                    .quantity(quantity)
                                    .shoppingCart(shoppingCartEntity)
                                    .book(bookEntity)
                                    .build();

        return cartItemResponseMapper.map(cartItemRepository.save(cartItemEntity));
    }

    public CartItem updateItemQuantity(Long itemId, Integer quantity) {
        CartItemEntity itemEntity = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found: " + itemId));
        itemEntity.setQuantity(quantity);
        return cartItemResponseMapper.map(cartItemRepository.save(itemEntity));
    }


    public void removeCartItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }
}