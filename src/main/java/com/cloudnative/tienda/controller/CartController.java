package com.cloudnative.tienda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudnative.tienda.model.dtos.CartRequest;
import com.cloudnative.tienda.model.dtos.CartResponse;
import com.cloudnative.tienda.model.dtos.ReceiptResponse;
import com.cloudnative.tienda.service.CartService;

@Controller
@RequestMapping("/api/cart")
public class CartController {

  private CartService cartService;

  @Autowired
  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  /**
  * Crea un nuevo carro con sus ítems y genera una boleta para este.
  * El cuerpo del request debe ser un JSON representando el Cart
  * con sus CartItems anidados.
  *
  * Ejemplo de cuerpo de request:
  * {
  * "cartItems": [
  * {
  * "product": { "id": 1 }, // Solo necesitamos el ID del producto
  * "quantity": 2
  * },
  * {
  * "product": { "id": 2 },
  * "quantity": 1
  * }
  * ]
  * }
  */
    
  @PostMapping("/checkout")
  public ResponseEntity<ReceiptResponse> processCartAndCheckout(@RequestBody CartRequest cartRequest) {
    try {
      ReceiptResponse generatedReceipt = cartService.processCartAndGenerateReceipt(cartRequest);
      return new ResponseEntity<>(generatedReceipt, HttpStatus.CREATED);
    } catch (IllegalArgumentException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } catch (RuntimeException e) {
      System.err.println("Error al procesar el carro: " + e.getMessage());
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

    // Opcional: Puedes mantener endpoints para obtener un carro o boleta por ID si es útil para consultar
    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponse> getCartById(@PathVariable Long cartId) {
    return cartService.getCartResponseById(cartId)
      .map(cart -> new ResponseEntity<>(cart, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/receipts/{receiptId}")
    public ResponseEntity<ReceiptResponse> getReceiptById(@PathVariable Long receiptId) {
    return cartService.getReceiptResponseById(receiptId)
      .map(receipt -> new ResponseEntity<>(receipt, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
