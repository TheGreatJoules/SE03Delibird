package com.csulb.ase.assignment3.utils;

import com.csulb.ase.assignment3.models.Order;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.StateTaxRateEnum;

public class ExpenseUtil {
    public static double calculateStateTax(String state, double total_cost) {
        double tax_rate = StateTaxRateEnum.valueOf(state).tax_rate;
        return total_cost + total_cost * tax_rate;
    }

    public static double calculateDiscounts(Person person) {
        return 0;
    }
}
