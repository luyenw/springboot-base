package com.canifa.stylenest.entity.dto.request;

import com.canifa.stylenest.annotation.ValidMapValuePositive;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class CheckoutRequest {
    private Long id;
    private String name;
    private String address;
    private String tel;
    @ValidMapValuePositive
    Map<String, Long> items;
}
