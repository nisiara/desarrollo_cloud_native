package com.cloudnative.tienda.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class CartItemRequest {
  private Long productId; // Solo necesitamos el ID del producto
  private Integer quantity;
}
