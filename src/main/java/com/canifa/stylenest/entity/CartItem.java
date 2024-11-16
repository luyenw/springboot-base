package com.canifa.stylenest.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "cart_item")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "model_id")
    private String modelId;
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "model_id", insertable = false, updatable = false)
    private Model model;
}
