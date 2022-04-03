package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Invoice {
    @NonNull
    private String id;
    @NonNull
    private long timestamp;
    private double total_cost;
    private Map<String, Order> orders;
}
