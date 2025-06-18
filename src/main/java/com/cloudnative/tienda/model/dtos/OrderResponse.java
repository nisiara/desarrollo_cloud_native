package com.cloudnative.tienda.model.dtos;

import java.util.List;
import com.cloudnative.tienda.model.entities.Receipt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OrderResponse {
  private Long id;
  private String orderCode;
  private List<OrderItemsResponse> orderItems;
  private Receipt receipt;
}
