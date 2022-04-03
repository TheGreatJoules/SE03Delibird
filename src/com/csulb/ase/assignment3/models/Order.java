package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @NonNull
    private String id;
    @NonNull
    private String invoice_id;
    @NonNull
    private String warehouse_id;
    @NonNull
    private String product_id;
    @NonNull
    private ProductEnum product_type;
    @NonNull
    private long timestamp;
    @NonNull
    private int quantity;
    @NonNull
    private double cost;
}
