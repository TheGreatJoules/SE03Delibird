package com.csulb.ase.assignment3.utils;

import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.StateTaxRateEnum;

import java.util.Objects;

public class ExpenseUtil {
    public static double calculateStateTax(String state, double total_cost) {
        double tax_rate = StateTaxRateEnum.valueOf(state).tax_rate;
        return total_cost + total_cost * tax_rate;
    }

    public static double calculateDiscounts(double discounts, String delivery, String payment ) {
        if (payment.equals("CASH")) {
            discounts += 0.1;
        }
        if (payment.equals("PICKUP")) {
            discounts += 0.15;
        }
        return discounts;
    }
}
