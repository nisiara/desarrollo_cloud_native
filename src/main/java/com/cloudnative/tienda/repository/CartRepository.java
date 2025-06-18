package com.cloudnative.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudnative.tienda.model.entities.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{}
