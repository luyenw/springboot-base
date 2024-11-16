package com.canifa.stylenest.entity.dto.request;

import com.canifa.stylenest.annotation.ValidMapValuePositive;
import lombok.Data;

import java.util.Map;

@Data
public class CartRequest {
    @ValidMapValuePositive
    Map<String, Long> items;
}
