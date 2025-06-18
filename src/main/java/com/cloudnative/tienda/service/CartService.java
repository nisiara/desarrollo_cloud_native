package com.cloudnative.tienda.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudnative.tienda.model.dtos.CartItemRequest;
import com.cloudnative.tienda.model.dtos.CartItemResponse;
import com.cloudnative.tienda.model.dtos.CartRequest;
import com.cloudnative.tienda.model.dtos.CartResponse;
import com.cloudnative.tienda.model.dtos.ReceiptResponse;
import com.cloudnative.tienda.model.entities.Cart;
import com.cloudnative.tienda.model.entities.CartItem;
import com.cloudnative.tienda.model.entities.Product;
import com.cloudnative.tienda.model.entities.Receipt;
import com.cloudnative.tienda.repository.CartItemRepository;
import com.cloudnative.tienda.repository.CartRepository;
import com.cloudnative.tienda.repository.ProductRepository;
import com.cloudnative.tienda.repository.ReceiptRepository;

import jakarta.transaction.Transactional;

@Service
public class CartService {

  private CartRepository cartRepository;
  private CartItemRepository cartItemRepository;
  private ProductRepository productRepository;
  private ReceiptRepository receiptRepository;

  @Autowired
  public CartService(
    CartRepository cartRepository, 
    CartItemRepository cartItemRepository, 
    ProductRepository productRepository, 
    ReceiptRepository receiptRepository){
      this.cartRepository = cartRepository;
      this.cartItemRepository = cartItemRepository;
      this.productRepository = productRepository;
      this.receiptRepository = receiptRepository;
  }

  /**
  * Procesa un nuevo carro, crea sus ítems asociados y genera una boleta.
  * @param incomingCart El objeto Cart que contiene los CartItems iniciales.
  * Cada CartItem debe tener el 'productId' y 'quantity'.
  * @return La boleta generada para el carro.
  */
    
  @Transactional // Asegura que toda la operación sea atómica
  public ReceiptResponse processCartAndGenerateReceipt(CartRequest cartRequest) {
    if (cartRequest.getItems() == null || cartRequest.getItems().isEmpty()) {
      throw new IllegalArgumentException("El carro debe contener al menos un ítem.");
    }

    // 1. Guardar el carro base primero para obtener un ID si es nuevo
    Cart newCart = new Cart();
    // Si decides reintroducirlo, o null
    newCart.setCartItems(new ArrayList<>()); // Inicializar para añadir los ítems procesados
    newCart = cartRepository.save(newCart); // Guarda el Cart vacío para obtener su ID

    double totalAmount = 0.0;
    List<CartItem> processedCartItems = new ArrayList<>();

    // 2. Procesar cada CartItem recibido
    for (CartItemRequest itemRequest : cartRequest.getItems()) {
      // Asegurarse de que el producto exista y obtener su precio actual
      Product product = productRepository.findById(itemRequest.getProductId())
        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + itemRequest.getProductId()));

      CartItem newCartItem = new CartItem();
      newCartItem.setCart(newCart); // Asignar el nuevo carro
      newCartItem.setProduct(product); // Asignar el producto encontrado
      newCartItem.setQuantity(itemRequest.getQuantity());
      // Important: Use the current product price for the cart item
      newCartItem.setPrice(product.getPrice()); // Precio actual del producto

      processedCartItems.add(newCartItem); // Añadir al listado para el carro
      totalAmount += newCartItem.getPrice() * newCartItem.getQuantity();
    }

    // 3. Guardar los ítems del carro
    cartItemRepository.saveAll(processedCartItems); // Guarda todos los CartItems de una vez
    newCart.setCartItems(processedCartItems); // Actualiza la lista de ítems en el objeto Cart

    // 4. Generar y asociar la boleta
    Receipt receipt = new Receipt();
    receipt.setCart(newCart); // Asociar la boleta al nuevo carro
    receipt.setIssueDate(LocalDateTime.now());
    receipt.setTotalAmount(totalAmount);
    receipt.setReceiptNumber(generateUniqueReceiptNumber()); // Generar número de boleta

    Receipt savedReceipt = receiptRepository.save(receipt);

    // 5. Vincular la boleta al carro y guardar el carro actualizado
    newCart.setReceipt(savedReceipt);
    cartRepository.save(newCart);

    return convertToReceiptResponse(savedReceipt); // Guarda el Cart para actualizar la referencia a la Boleta
   
  }

  // Opcional: Métodos para obtener un carro o boleta específica si se necesitan por separado
  // Opcional: Métodos para obtener DTOs específicos
    public Optional<CartResponse> getCartResponseById(Long id) {
      return cartRepository.findById(id)
        .map(this::convertToCartResponse);
    }

    public Optional<ReceiptResponse> getReceiptResponseById(Long id) {
      return receiptRepository.findById(id)
        .map(this::convertToReceiptResponse);
    }

    // --- Métodos de Conversión ---

  private ReceiptResponse convertToReceiptResponse(Receipt receipt) {
    return ReceiptResponse.builder()
      .id(receipt.getId())
      .receiptNumber(receipt.getReceiptNumber())
      .issueDate(receipt.getIssueDate())
      .totalAmount(receipt.getTotalAmount())
      .cartId(receipt.getCart() != null ? receipt.getCart().getId() : null)
      .build();
    }

    private CartItemResponse convertToCartItemResponse(CartItem cartItem) {
      return CartItemResponse.builder()
        .id(cartItem.getId())
        .productId(cartItem.getProduct().getId())
        .productName(cartItem.getProduct().getName()) // Obtener el nombre del producto
        .productSku(cartItem.getProduct().getSku()) // Obtener el SKU del producto
        .quantity(cartItem.getQuantity())
        .price(cartItem.getPrice())
        .build();
    }

    private CartResponse convertToCartResponse(Cart cart) {
      List<CartItemResponse> itemResponses = cart.getCartItems().stream()
        .map(this::convertToCartItemResponse)
        .collect(Collectors.toList());

        return CartResponse.builder()
          .id(cart.getId())
          .cartItems(itemResponses)
          .receiptId(cart.getReceipt() != null ? cart.getReceipt().getId() : null)
          .build();
    }

    // Helper para generar un número de boleta (simple, para producción usar algo más robusto)
    private String generateUniqueReceiptNumber() {
      return "RCPT-" + LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
      + ThreadLocalRandom.current().nextInt(10000, 99999);
    }

}

