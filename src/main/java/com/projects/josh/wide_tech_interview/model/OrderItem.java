/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.projects.josh.wide_tech_interview.model;

import java.math.BigDecimal;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
/**
 *
 * @author USER
 */
@Data
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private Integer quantity;
    private BigDecimal price;

    @Column(name = "item_total")
    private BigDecimal total;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
    
    @JsonBackReference
    @ManyToOne
    private OrderCart orderCart;

    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    private void calculateTotal() {
        this.total = this.getSubtotal();
    }

    public OrderItem() {
    }

    public OrderItem(Product product, Integer quantity) {
        this.name = product.getName();
        this.type = product.getType();
        this.quantity = quantity;
        this.price = product.getPrice();
        this.product = product;
        this.calculateTotal();
    }
}
