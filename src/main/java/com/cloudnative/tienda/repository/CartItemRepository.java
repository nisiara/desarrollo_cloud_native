package com.cloudnative.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudnative.tienda.model.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
