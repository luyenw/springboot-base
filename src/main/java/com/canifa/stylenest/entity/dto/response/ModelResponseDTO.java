package com.canifa.stylenest.entity.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ModelResponseDTO {
    private String id;
    private int stock;
    private String size;
    private String color;
    private List<String> images;
}
