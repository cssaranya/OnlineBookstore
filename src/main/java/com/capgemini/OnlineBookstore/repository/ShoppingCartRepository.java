package com.capgemini.OnlineBookstore.repository;

import com.capgemini.OnlineBookstore.model.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
    Optional<ShoppingCartEntity> findByUserId(Long Id);
    boolean existsByUserId(Long userId);
}