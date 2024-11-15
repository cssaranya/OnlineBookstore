package com.capgemini.OnlineBookstore.service;

import com.capgemini.OnlineBookstore.dto.CartItem;
import com.capgemini.OnlineBookstore.dto.ShoppingCart;
import com.capgemini.OnlineBookstore.exception.BookNotFoundException;
import com.capgemini.OnlineBookstore.exception.CartNotFoundException;
import com.capgemini.OnlineBookstore.exception.InvalidRequestException;
import com.capgemini.OnlineBookstore.exception.EntityNotFoundException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class ShoppingCartService {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);
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

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        ShoppingCartEntity shoppingCartEntity = cartRepository.save(ShoppingCartEntity.builder().user(userEntity).build());
        return shoppingCartResponseMapper.map(shoppingCartEntity);
    }

    public ShoppingCart addItemToCart(Long userId, Long bookId, Integer quantity) {
        System.out.println("UserId " + userId + " BookId " + bookId + " quantity "+ quantity);
        if (userId == null || bookId == null || quantity == null || quantity < 0) {
            logger.info("UserId " + userId + " BookId" + bookId + " quantity"+ quantity);
            throw new InvalidRequestException("UserId BookId and Quantity are required");
        }

        ShoppingCartEntity shoppingCartEntity = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Shopping cart not found for user: " + userId));

        System.out.println("shoppingCartEntity " + shoppingCartEntity.toString());

        Long updatedShoppingCartId = getUpdatedCartId(bookId, quantity, shoppingCartEntity);

        ShoppingCartEntity updatedCartEntity = cartRepository.findById(updatedShoppingCartId)
                .orElseThrow(() -> new EntityNotFoundException("Shopping cart not found: " + updatedShoppingCartId));
        return shoppingCartResponseMapper.map(updatedCartEntity);
    }

    private Long getUpdatedCartId(Long bookId, Integer quantity, ShoppingCartEntity shoppingCartEntity) {
        Long updatedShoppingCartId;
        System.out.println("shoppingCartEntity " + shoppingCartEntity.toString() + " BookId " + bookId + " quantity "+ quantity);
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
        System.out.println("shoppingCartEntity " + shoppingCartEntity.toString() + " BookId " + bookId + " quantity "+ quantity);
        Optional<CartItemEntity> itemEntity = shoppingCartEntity.getItems().stream()
                .filter(cartItemEntity -> cartItemEntity.getBook().getId() == bookId)
                .findFirst();
        CartItem updatedCartItem = new CartItem();
        if(itemEntity.isPresent()) {
            System.out.println("itemEntity " + itemEntity.get().toString());
            updatedCartItem  = updateItemQuantity(itemEntity.get().getId(), quantity+itemEntity.get().getQuantity());
        }
        System.out.println("updatedCartItem " + updatedCartItem.toString());
        return updatedCartItem;
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
        cartItemRepository.deleteById(itemId);
    }
}