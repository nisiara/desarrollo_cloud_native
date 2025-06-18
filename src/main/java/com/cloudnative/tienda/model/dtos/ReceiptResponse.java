package com.cloudnative.tienda.model.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class ReceiptResponse {
  private Long id;
  private String receiptNumber;
  private LocalDateTime issueDate;
  private Double totalAmount;
  private Long cartId; // El ID del carro al que pertenece esta boleta
}
