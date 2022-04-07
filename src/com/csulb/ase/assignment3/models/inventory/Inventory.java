package com.csulb.ase.assignment3.models.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @NonNull
    private int total_items;
    private int total_warehouses;
}
