package com.cloudnative.tienda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cloudnative.tienda.model.dtos.ProductRequest;
import com.cloudnative.tienda.model.dtos.ProductResponse;
import com.cloudnative.tienda.service.ProductService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("api/product")
public class ProductController {
  private ProductService productService;

  @Autowired
  public ProductController(ProductService productService){
    this.productService = productService;
  }

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> getAllProducts() {
      return productService.getAllProducts();
  }

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public void createProduct(@RequestBody ProductRequest productRequest) {
    productService.addProduct(productRequest);
  }
  
}
