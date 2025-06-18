package com.cloudnative.tienda.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class ProductResponse {
  private Long id;
  private String sku;
  private String name;
  private Double price;
  private Boolean status;
}
