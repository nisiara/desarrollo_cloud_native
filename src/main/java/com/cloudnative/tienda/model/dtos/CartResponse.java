package com.cloudnative.tienda.model.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class CartResponse {
  private Long id;
  // Si decides reintroducirlo en la entidad Cart
  //private String cartCode; 
  private List<CartItemResponse> cartItems; // Los ítems del carro detallados
  private Long receiptId; // El ID de la boleta asociada
}
