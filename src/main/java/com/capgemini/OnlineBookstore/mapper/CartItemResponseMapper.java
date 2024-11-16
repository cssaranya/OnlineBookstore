package com.capgemini.OnlineBookstore.mapper;

import com.capgemini.OnlineBookstore.dto.Book;
import com.capgemini.OnlineBookstore.dto.CartItem;
import com.capgemini.OnlineBookstore.model.CartItemEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CartItemResponseMapper {
    ModelMapper modelMapper = new ModelMapper();
    public CartItem map(CartItemEntity cartItemEntity){
        return CartItem.builder()
                .book(modelMapper.map(cartItemEntity.getBook(), Book.class))
                .id(cartItemEntity.getId())
                .quantity(cartItemEntity.getQuantity())
                .shoppingcartId(cartItemEntity.getShoppingCart().getId())
                .build();
    }
}
