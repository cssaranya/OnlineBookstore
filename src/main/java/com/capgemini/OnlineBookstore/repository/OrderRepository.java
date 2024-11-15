package com.capgemini.OnlineBookstore.repository;

import com.capgemini.OnlineBookstore.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

}