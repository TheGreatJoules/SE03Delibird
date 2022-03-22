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
    private String productName;
    private String Manufacturer;
    private String dimensions;
    private ProductColor productColor;
    private int year;
    private int quantityStock;
    private int quantitySold;
}
