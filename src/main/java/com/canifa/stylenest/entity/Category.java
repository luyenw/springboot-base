package com.canifa.stylenest.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private String id;
    private String description;
    @Column(name = "parent_id")
    private String parentId;
}
