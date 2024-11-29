package com.canifa.stylenest.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PageRequest {
    private int page = 1;
    private int pageSize = 10;
    private String sortBy = "id";
    private String order = "asc";
}
