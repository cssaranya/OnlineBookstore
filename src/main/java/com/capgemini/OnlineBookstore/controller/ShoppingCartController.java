package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.dto.CartItem;
import com.capgemini.OnlineBookstore.dto.ShoppingCart;
import com.capgemini.OnlineBookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
    private final ShoppingCartService cartService;

    @GetMapping("/getCart/{userId}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable Long userId) {
        ShoppingCart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/createCart/{userId}")
    public ResponseEntity<ShoppingCart> createShoppingCart(@PathVariable Long userId) {
        ShoppingCart cart = cartService.createShoppingCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add/{userId}/items/{bookId}")
    public ResponseEntity<ShoppingCart> addItemToCart(
            @PathVariable Long userId,
            @PathVariable Long bookId,
            @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, bookId, quantity));
    }

    @PutMapping("/update/items/{itemId}")
    public ResponseEntity<CartItem> updateItemQuantity(
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        logger.info("received request updateItemQuantity");
        CartItem updatedItem = cartService.updateItemQuantity(itemId, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/delete/items/{itemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Long itemId) {
        cartService.removeCartItem(itemId);
        return ResponseEntity.ok("Cart item deleted successfully");
    }
}