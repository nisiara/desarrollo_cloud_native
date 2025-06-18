package com.cloudnative.tienda.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class CartItemResponse {
  private Long id; // ID del CartItem
  private Long productId; // ID del producto asociado
  private String productName; // Nombre del producto para mayor claridad
  private String productSku; // SKU del producto
  private Integer quantity;
  private Double price; // Precio unitario del CartItem (precio al momento de añadir)
}

