package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public abstract class Product {
    @NonNull
    private String id;
    @NonNull
    private String warehouse_id;
    @NonNull
    private String warehouse_address;
    @NonNull
    private ProductEnum product_type;
    @NonNull
    private int stock_count;
    @NonNull
    private int sold_count;
    private String product_name;
    private String manufacturer;
    private String dimensions;
    private ProductColor product_color;
    private int year;

    public Product() {}

}
