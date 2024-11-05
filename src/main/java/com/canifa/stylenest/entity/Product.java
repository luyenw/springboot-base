package com.canifa.stylenest.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private int stock;
    private int price;
    private String description;
    private String instruction;
    private String materials;
}
