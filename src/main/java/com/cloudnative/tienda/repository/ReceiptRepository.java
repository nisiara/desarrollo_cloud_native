package com.cloudnative.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudnative.tienda.model.entities.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long>{

}
