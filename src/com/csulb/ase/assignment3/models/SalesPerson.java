package com.csulb.ase.assignment3.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class SalesPerson extends Person {
    private int total_sales;
    private double commission_rate;
    private double performance_score;
}
