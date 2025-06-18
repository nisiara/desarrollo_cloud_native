package com.cloudnative.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudnative.tienda.model.dtos.ProductRequest;
import com.cloudnative.tienda.model.dtos.ProductResponse;
import com.cloudnative.tienda.model.entities.Product;
import com.cloudnative.tienda.repository.ProductRepository;

@Service
public class ProductService {
  private ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository){
    this.productRepository = productRepository;
  }

  public void addProduct(ProductRequest productRequest){
    Product product = Product.builder()
      .sku(productRequest.getSku())
      .name(productRequest.getName())
      .description(productRequest.getDescription())
      .price(productRequest.getPrice())
      .status(productRequest.getStatus())
      .build();
    
    productRepository.save(product);
  }

  public List<ProductResponse> getAllProducts(){
    List<Product> products = productRepository.findAll();
    return products.stream().map(this::mapToProductResponse).toList();
  }

  private ProductResponse mapToProductResponse(Product product) {
    return ProductResponse.builder()
      .id(product.getId())
      .sku(product.getSku())
      .name(product.getName())
      .description(product.getDescription())
      .price(product.getPrice())
      .status(product.getStatus())
      .build();
  }

}
