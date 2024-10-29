package com.canifa.stylenest.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.List;

@Document(indexName = "vue_storefront_catalog_2_product_1729676856")
@Data
public class Product {
    @Id
    private String id;

    @Field(name = "short_description", type = FieldType.Text)
    private String shortDescription;

    @Field(name = "writeoff", type = FieldType.Integer)
    private int writeoff;

    @Field(name = "color", type = FieldType.Integer)
    private int color;

    @Field(name = "tsk", type = FieldType.Long)
    private long tsk;

    @Field(name = "is_pre_order", type = FieldType.Boolean)
    private boolean isPreOrder;

    @Field(name = "regular_price", type = FieldType.Double)
    private double regularPrice;

    @Field(name = "final_price", type = FieldType.Double)
    private double finalPrice;

    @Field(name = "price", type = FieldType.Double)
    private double price;

    @Field(name = "logistic_qty", type = FieldType.Integer)
    private int logisticQty;

    @Field(name = "only_online", type = FieldType.Integer)
    private int onlyOnline;

    @Field(name = "sku", type = FieldType.Text)
    private String sku;

    @Field(name = "product_links", type = FieldType.Object)
    private List<Object> productLinks;

    @Field(name = "stock", type = FieldType.Object)
    private Stock stock;

    @Field(name = "barcode", type = FieldType.Text)
    private String barcode;

    @Field(name = "slug", type = FieldType.Text)
    private String slug;

    @Field(name = "meta_title", type = FieldType.Text)
    private String metaTitle;

    @Field(name = "visibility", type = FieldType.Integer)
    private int visibility;

    @Field(name = "type_id", type = FieldType.Text)
    private String typeId;

    @Field(name = "media_gallery", type = FieldType.Object)
    private List<Object> mediaGallery;

    @Field(name = "tax_class_id", type = FieldType.Integer)
    private int taxClassId;

    @Field(name = "url_key", type = FieldType.Text)
    private String urlKey;

    @Field(name = "size", type = FieldType.Integer)
    private int size;

    @Field(name = "name", type = FieldType.Text)
    private String name;

//    @Field(name = "configurable_children", type = FieldType.Object)
//    private List<Object> configurableChildren;

    @Field(name = "promotion_image_list", type = FieldType.Integer)
    private int promotionImageList;

    @Field(name = "mastercolor", type = FieldType.Integer)
    private int mastercolor;

    @Field(name = "status", type = FieldType.Integer)
    private int status;

    @Field(name = "original_price", type = FieldType.Double)
    private double originalPrice;

    @Field(name = "original_price_tax", type = FieldType.Double)
    private double originalPriceTax;

    @Field(name = "original_price_incl_tax", type = FieldType.Double)
    private double originalPriceInclTax;

    @Field(name = "original_final_price", type = FieldType.Double)
    private double originalFinalPrice;

    @Field(name = "price_tax", type = FieldType.Double)
    private double priceTax;

    @Field(name = "price_incl_tax", type = FieldType.Double)
    private double priceInclTax;

    @Field(name = "final_price_tax", type = FieldType.Double)
    private double finalPriceTax;

    @Field(name = "final_price_incl_tax", type = FieldType.Double)
    private double finalPriceInclTax;

    @Field(name = "special_price", type = FieldType.Double)
    private double specialPrice;

    @Field(name = "special_price_tax", type = FieldType.Double)
    private double specialPriceTax;

    @Field(name = "special_price_incl_tax", type = FieldType.Double)
    private double specialPriceInclTax;

    @Field(name = "category_ids", type = FieldType.Object)
    private List<Long> categoryIds;
}
