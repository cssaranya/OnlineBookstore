package com.capgemini.OnlineBookstore.mapper;

import com.capgemini.OnlineBookstore.dto.CartItem;
import com.capgemini.OnlineBookstore.dto.ShoppingCart;
import com.capgemini.OnlineBookstore.dto.User;
import com.capgemini.OnlineBookstore.model.ShoppingCartEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShoppingCartResponseMapper {
    private final CartItemResponseMapper cartItemResponseMapper = new CartItemResponseMapper();
    private final ModelMapper modelMapper = new ModelMapper();
    public ShoppingCart map(ShoppingCartEntity shoppingCartEntity){
        List<CartItem> cartItemList = new ArrayList<>();
        if (shoppingCartEntity.getItems() != null && !shoppingCartEntity.getItems().isEmpty()) {
            cartItemList = shoppingCartEntity.getItems().stream()
                    .map(cartItemResponseMapper::map)
                    .collect(Collectors.toList());
        }

        return ShoppingCart.builder()
                .items(cartItemList)
                .id(shoppingCartEntity.getId())
                .user(modelMapper.map(shoppingCartEntity.getUser(), User.class))
                .build();
    }
}
