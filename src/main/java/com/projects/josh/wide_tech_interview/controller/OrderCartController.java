/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.projects.josh.wide_tech_interview.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projects.josh.wide_tech_interview.model.OrderCart;
import com.projects.josh.wide_tech_interview.model.OrderItem;
import com.projects.josh.wide_tech_interview.model.Product;
import com.projects.josh.wide_tech_interview.repository.OrderCartRepository;
import com.projects.josh.wide_tech_interview.repository.ProductRepository;
/**
 *
 * @author USER
 */

@RestController
@RequestMapping("/api/cart") 
public class OrderCartController {
private final OrderCartRepository orderCartRepository;
    private final ProductRepository productRepository;
    
    public OrderCartController(OrderCartRepository orderCartRepository, ProductRepository productRepository) {
        this.orderCartRepository = orderCartRepository;
        this.productRepository = productRepository;
    }

    @PostMapping
    public OrderCart createCart() {
        return orderCartRepository.save(new OrderCart());
    }
    
    @PostMapping("/{cartId}/items")
    public ResponseEntity<OrderCart> addItemToCart(
            @PathVariable Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        return orderCartRepository.findById(cartId)
                .map(cart -> {
                    Product product = productRepository.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    
                    OrderItem orderItem = new OrderItem(product, quantity);
                    orderItem.setOrderCart(cart);
                    cart.getItems().add(orderItem);
                    cart.calculateTotalAmount();  // Calculate total before saving
                    
                    return ResponseEntity.ok(orderCartRepository.save(cart));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderCart> getCart(@PathVariable Long id) {
        return orderCartRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{id}/place")
    public ResponseEntity<OrderCart> placeOrder(@PathVariable Long id) {
        return orderCartRepository.findById(id)
                .map(cart -> {
                    cart.setStatus("COMPLETE");
                    return ResponseEntity.ok(orderCartRepository.save(cart));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
