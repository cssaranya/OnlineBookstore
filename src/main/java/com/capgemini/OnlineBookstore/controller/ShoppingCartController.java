package com.capgemini.OnlineBookstore.controller;

import com.capgemini.OnlineBookstore.dto.CartItem;
import com.capgemini.OnlineBookstore.dto.ShoppingCart;
import com.capgemini.OnlineBookstore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCart> getCart(@PathVariable Long userId) {
        ShoppingCart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ShoppingCart> createShoppingCart(@PathVariable Long userId) {
        ShoppingCart cart = cartService.createShoppingCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<CartItem> addItemToCart(
            @PathVariable Long userId,
            @PathVariable Long bookId,
            @PathVariable Integer quantity) {
        CartItem addedItem = cartService.addItemToCart(userId, bookId, quantity);
        return ResponseEntity.ok(addedItem);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartItem> updateItemQuantity(
            @PathVariable Long itemId,
            @RequestBody Integer quantity) {
        CartItem updatedItem = cartService.updateItemQuantity(itemId, quantity);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable Long itemId) {
        cartService.removeCartItem(itemId);
        return ResponseEntity.ok().build();
    }
}