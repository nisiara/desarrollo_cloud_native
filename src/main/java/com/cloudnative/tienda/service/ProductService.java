package com.cloudnative.tienda.service;

import java.util.List;
import java.util.Optional;

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

  
    public Optional<ProductResponse> getProductById(Long id) {
      return productRepository.findById(id).map(this::mapToProductResponse);
    }


  public void addProduct(ProductRequest productRequest) {
    Product product = Product.builder()
      .sku(productRequest.getSku())
      .name(productRequest.getName())
      .price(productRequest.getPrice())
      .status(productRequest.getStatus())
      .build();

      productRepository.save(product);       
  }

  public ProductResponse createProduct(ProductRequest productRequest) {
    Product product = Product.builder()
      .name(productRequest.getName())
      .sku(productRequest.getSku())
      .price(productRequest.getPrice())
      .status(productRequest.getStatus())
      .build();
    Product savedProduct = productRepository.save(product);
      return mapToProductResponse(savedProduct);
    }

  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }

  public List<ProductResponse> getAllProducts() {
    List<Product> products = productRepository.findAll();
    return products.stream().map(this::mapToProductResponse).toList();
  }

  public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
    return productRepository.findById(id).map(product -> {
      product.setName(productRequest.getName());
      product.setSku(productRequest.getSku());
      product.setPrice(productRequest.getPrice());
      product.setStatus(productRequest.getStatus());
      Product updatedProduct = productRepository.save(product);
      return mapToProductResponse(updatedProduct);
    }).orElseThrow(() -> new RuntimeException("Product not found with id " + id));
  }


  private ProductResponse mapToProductResponse(Product product) {
    return ProductResponse.builder()
      .id(product.getId())
      .sku(product.getSku())
      .name(product.getName())
      .price(product.getPrice())
      .status(product.getStatus())
      .build();
  }
    

}
