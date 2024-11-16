package com.canifa.stylenest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "models")
@Getter
@Setter
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String size;
    private String color;
    private int stock;
    @Column(name = "product_id")
    private String productId;
    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
}
