package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
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
    private int stock_count;
    private int sold_count;
    private String model_name;
    private String series;
    private String manufacturer;
    private double height;
    private double width;
    private double depth;
    private double weight;
    private int year;
    private ColorEnum product_color;

}
