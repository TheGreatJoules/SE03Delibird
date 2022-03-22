package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@AllArgsConstructor
public class Order {
    @NonNull
    private String id;
    @NonNull
    private String product_id;
    @NonNull
    private Date date;
    @NonNull
    private int quantity;
    @NonNull
    private int totalCost;
}
