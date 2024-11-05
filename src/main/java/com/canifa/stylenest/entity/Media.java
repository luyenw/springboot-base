package com.canifa.stylenest.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "model_id")
    private String modelId;
    @Column(name = "file_id")
    private int fileId;
}
