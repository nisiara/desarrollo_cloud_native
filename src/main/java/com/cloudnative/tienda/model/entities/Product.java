package com.cloudnative.tienda.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

@Entity
@Table(name = "tbl_products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  private Long id;

  private String name;
  private String sku;
  private Double price;
  private Boolean status;
}
