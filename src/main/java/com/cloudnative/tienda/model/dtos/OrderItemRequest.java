package com.cloudnative.tienda.model.dtos;

import com.cloudnative.tienda.model.entities.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    private Long id;
    private String sku;
    private Double price;
    private Integer quantity;
    private Order order;
}