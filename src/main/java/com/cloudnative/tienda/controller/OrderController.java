package com.cloudnative.tienda.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cloudnative.tienda.model.dtos.OrderRequest;
import com.cloudnative.tienda.model.dtos.OrderResponse;
import com.cloudnative.tienda.service.OrderService;

@Controller
@RequestMapping("/api/order")
public class OrderController {

  private OrderService orderService;

  @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
      this.orderService.placeOrder(orderRequest);
      return "Order placed successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getOrders() {
      return this.orderService.getAllOrders();
    }
}
