package com.canifa.stylenest.entity.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProductResponseDTO {
    private String id;
    private String name;
    private int stock;
    private int price;
    private String description;
    private String instruction;
    private String materials;
    private List<String> images;
    private List<ModelResponseDTO> models;
}
