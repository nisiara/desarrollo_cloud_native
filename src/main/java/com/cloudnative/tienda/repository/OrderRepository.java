package com.cloudnative.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudnative.tienda.model.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{}
