package com.canifa.stylenest.entity.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class CategoryRequest {
    @Column(name = "description")
    private String name;
    private String parentId;
}
