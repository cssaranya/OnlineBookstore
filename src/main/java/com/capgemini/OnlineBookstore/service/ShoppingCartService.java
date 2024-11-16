package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.CartItem;
import com.capgemini.OnlineBookstore.dto.ShoppingCart;
import com.capgemini.OnlineBookstore.exception.*;
import com.capgemini.OnlineBookstore.mapper.CartItemResponseMapper;
import com.capgemini.OnlineBookstore.mapper.ShoppingCartResponseMapper;
import com.capgemini.OnlineBookstore.model.BookEntity;
import com.capgemini.OnlineBookstore.model.CartItemEntity;
import com.capgemini.OnlineBookstore.model.ShoppingCartEntity;
import com.capgemini.OnlineBookstore.repository.BookRepository;
import com.capgemini.OnlineBookstore.repository.CartItemRepository;
import com.capgemini.OnlineBookstore.repository.ShoppingCartRepository;
import com.capgemini.OnlineBookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);
    private final ShoppingCartRepository cartRepository;

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    private final CartItemResponseMapper cartItemResponseMapper;
    private final ShoppingCartResponseMapper shoppingCartResponseMapper;

    public ShoppingCart getCartByUserId(Long userId) {
        if (userId == null) {
            logger.error("UserId is null");
            throw new InvalidRequestException("UserId is required");
        }
        ShoppingCartEntity shoppingCartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found for user: " + userId));
        return shoppingCartResponseMapper.map(shoppingCartEntity);
    }

    public ShoppingCart createShoppingCart(Long userId) {
        if (userId == null) {
            logger.error("UserId is null");
            throw new InvalidRequestException("UserId is required");
        }

        if (cartRepository.existsByUserId(userId)){
            throw new InvalidRequestException("Cart already exists for the user "+ userId);
        }

        ShoppingCartEntity shoppingCartEntity = ShoppingCartEntity.builder()
                .user(userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found: " + userId)))
                .build();
        return shoppingCartResponseMapper.map(cartRepository.save(shoppingCartEntity));
    }

    public ShoppingCart addItemToCart(Long userId, Long bookId, Integer quantity) {
        if (userId == null || bookId == null || quantity == null || quantity < 0) {
            logger.info("UserId " + userId + " BookId" + bookId + " quantity"+ quantity);
            throw new InvalidRequestException("UserId BookId and Quantity are required");
        }
        if (!userRepository.existsById(userId)){
            throw new UserNotFoundException("User not found with the id "+ userId);
        }
        if (!bookRepository.existsById(bookId)){
            throw new BookNotFoundException("Book not found for id: " + bookId);
        }
        ShoppingCartEntity shoppingCartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Shopping cart not found for user: " + userId));

        Long updatedShoppingCartId = getUpdatedCartId(bookId, quantity, shoppingCartEntity);

        ShoppingCartEntity updatedCartEntity = cartRepository.findById(updatedShoppingCartId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found: " + updatedShoppingCartId));
        return shoppingCartResponseMapper.map(updatedCartEntity);
    }

    private Long getUpdatedCartId(Long bookId, Integer quantity, ShoppingCartEntity shoppingCartEntity) {
        Long updatedShoppingCartId;
        CartItem updatedCartItem = updateItemIfExists(shoppingCartEntity, bookId, quantity);

        if (updatedCartItem == null){
             BookEntity bookEntity = bookRepository.findById(bookId)
                     .orElseThrow(() -> new BookNotFoundException("Book not found for id: " + bookId));

             CartItemEntity cartItemEntity = CartItemEntity.builder()
                     .quantity(quantity)
                     .shoppingCart(shoppingCartEntity)
                     .book(bookEntity)
                     .build();
            updatedShoppingCartId = cartItemResponseMapper.map(cartItemRepository.save(cartItemEntity)).getShoppingcartId();
         } else{
            updatedShoppingCartId = updatedCartItem.getShoppingcartId();
        }
        return updatedShoppingCartId;
    }

    private CartItem updateItemIfExists(ShoppingCartEntity shoppingCartEntity, Long bookId, Integer quantity) {
        Optional<CartItemEntity> itemEntity = shoppingCartEntity.getItems().stream()
                .filter(cartItemEntity -> cartItemEntity.getBook().getId().equals(bookId))
                .findFirst();

        return itemEntity.map(cartItemEntity -> updateItemQuantity(cartItemEntity.getId(), quantity + cartItemEntity.getQuantity()))
                .orElse(null);
    }

    public CartItem updateItemQuantity(Long itemId, Integer quantity) {
        if (itemId == null || quantity == null || quantity <= 0) {
            logger.error("ItemId " + itemId + " quantity"+ quantity);
            throw new InvalidRequestException("ItemId and Quantity are required");
        }

        CartItemEntity itemEntity = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found: " + itemId));
        itemEntity.setQuantity(quantity);
        return cartItemResponseMapper.map(cartItemRepository.save(itemEntity));
    }


    public void removeCartItem(Long itemId) {
        if (!cartItemRepository.existsById(itemId)) {
            throw new EntityNotFoundException("Cart item not found: " + itemId);
        }
        cartItemRepository.deleteById(itemId);
    }
}