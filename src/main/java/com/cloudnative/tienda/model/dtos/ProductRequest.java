package com.cloudnative.tienda.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductRequest {
  private String name;
  private String sku;
  private Double price;
  private Boolean status;  

}
