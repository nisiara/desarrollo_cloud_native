package com.cloudnative.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloudnative.tienda.model.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
