package com.capgemini.OnlineBookstore.repository;

import com.capgemini.OnlineBookstore.model.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
}