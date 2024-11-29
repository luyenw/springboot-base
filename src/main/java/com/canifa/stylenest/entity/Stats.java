package com.canifa.stylenest.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class Stats {
    private Long revenue;
    private Long order;
    private Long user;
    private Long product;
    private List<Map<String, Object>> categoryShare;
}
