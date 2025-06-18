package com.cloudnative.tienda.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudnative.tienda.model.dtos.OrderItemRequest;
import com.cloudnative.tienda.model.dtos.OrderItemsResponse;
import com.cloudnative.tienda.model.dtos.OrderRequest;
import com.cloudnative.tienda.model.dtos.OrderResponse;
import com.cloudnative.tienda.model.entities.Order;
import com.cloudnative.tienda.model.entities.OrderItem;
import com.cloudnative.tienda.repository.OrderRepository;

@Service
public class OrderService {

  private OrderRepository orderRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository){
    this.orderRepository = orderRepository;
  }

    public void placeOrder(OrderRequest orderRequest) {
      Order order = new Order();
      order.setOrderCode(UUID.randomUUID().toString());
      order.setOrderItem(orderRequest.getOrderItems().stream()
                    .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                    .toList());
            this.orderRepository.save(order);
       
    }


    public List<OrderResponse> getAllOrders() {
      List<Order> orders = this.orderRepository.findAll();
      return orders.stream().map(this::mapToOrderResponse).toList();
    }

     
    private OrderResponse mapToOrderResponse(Order order) {
      return new OrderResponse(
        order.getId(), 
        order.getOrderCode(),
        order.getOrderItem().stream().map(this::mapToOrderItemRequest).toList(),
        order.getReceipt());
    }

    private OrderItemsResponse mapToOrderItemRequest(OrderItem orderItems) {
        return new OrderItemsResponse(
          orderItems.getId(), 
          orderItems.getSku(), 
          orderItems.getQuantity(), 
          orderItems.getPrice(),
          orderItems.getOrder()
        );
    }

    private OrderItem mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return OrderItem.builder()
          .id(orderItemRequest.getId())
          .sku(orderItemRequest.getSku())
          .price(orderItemRequest.getPrice())
          .quantity(orderItemRequest.getQuantity())
          .order(order)
          .build();
    }


}
