package com.canifa.stylenest.entity.dto.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductRequestDTO {
    private String name;
    private int stock;
    private int price;
    private String description;
    private String instruction;
    private String materials;
    private List<ModelRequestDTO> models = new ArrayList<>();
}
