package com.csulb.ase.assignment3.models.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    @NonNull
    private String id;
    private String address;
    private int total_items;
    private Map<String, Product> products;
}
