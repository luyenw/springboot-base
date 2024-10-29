package com.canifa.stylenest.entity;

import lombok.Data;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@Data
public class Stock {
    @Field(name = "is_in_stock", type = FieldType.Boolean)
    private boolean isInStock;

    @Field(name = "max_sale_qty", type = FieldType.Integer)
    private int maxSaleQty;

    @Field(name = "stock_status", type = FieldType.Integer)
    private int stockStatus;

    @Field(name = "min_sale_qty", type = FieldType.Integer)
    private int minSaleQty;

    @Field(name = "item_id", type = FieldType.Long)
    private long itemId;

    @Field(name = "backorders", type = FieldType.Boolean)
    private boolean backorders;

    @Field(name = "min_qty", type = FieldType.Integer)
    private int minQty;

    @Field(name = "product_id", type = FieldType.Long)
    private long productId;

    @Field(name = "qty", type = FieldType.Integer)
    private int qty;

    @Field(name = "is_qty_decimal", type = FieldType.Boolean)
    private boolean isQtyDecimal;

    @Field(name = "low_stock_date", type = FieldType.Date)
    private String lowStockDate;
}

