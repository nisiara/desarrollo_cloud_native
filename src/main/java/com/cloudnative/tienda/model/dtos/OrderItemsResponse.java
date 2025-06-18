package com.cloudnative.tienda.model.dtos;

import com.cloudnative.tienda.model.entities.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class OrderItemsResponse {
  private Long id;
  private String sku;
  private Integer quantity;
  private Double price;
  private Order order;
}
