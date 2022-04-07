package com.csulb.ase.assignment3.models.persons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SalesPerson extends Person {
    private int total_sales;
    private double commission_rate;
    private double performance_score;
    private double total_earnings;
    private long last_sell;
}
