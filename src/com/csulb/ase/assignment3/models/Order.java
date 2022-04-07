package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @NonNull
    private String id;
    @NonNull
    private String customer_id;
    @NonNull
    private String salesperson_id;
    @NonNull
    private String product_id;
    @NonNull
    private ProductEnum product_type;
    private long timestamp;
    private int quantity;
    private double cost;
}
