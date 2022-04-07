package com.csulb.ase.assignment3.utils;


public class ExpenseUtil {

    public static double calculateAdjustedTotal(double rate, double total, double discount) {
        double total_with_tax = total + rate * total;
        return total_with_tax + total * discount;
    }

    public static double calculateDiscounts(String delivery, String payment ) {
        double total_discouts = 0.0;
        if (payment.equals("CASH")) {
            total_discouts += 0.1;
        }
        if (payment.equals("PICKUP")) {
            total_discouts += 0.15;
        }
        return total_discouts;
    }

    public static double calculateExpenses(double current_expense, double quote, int quantity) {
        return current_expense + quote * quantity;
    }
}
