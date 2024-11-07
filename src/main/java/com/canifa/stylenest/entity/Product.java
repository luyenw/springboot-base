package com.canifa.stylenest.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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
