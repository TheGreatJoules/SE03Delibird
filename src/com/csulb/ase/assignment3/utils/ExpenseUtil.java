package com.csulb.ase.assignment3.utils;

import com.csulb.ase.assignment3.models.invoices.DeliveryEnum;
import com.csulb.ase.assignment3.models.invoices.InvoiceStatusEnum;
import com.csulb.ase.assignment3.models.invoices.PaymentEnum;

public class ExpenseUtil {
    private static final long ONE_DAY = 24 * 60 * 60;
    private static final long ONE_WEEK = 7 * 24 * 60 * 60;

    public static double calculateAdjustedTotal(double rate, double total, double discount, PaymentEnum payment, InvoiceStatusEnum previous_status, InvoiceStatusEnum current_status, long start_timestamp, long end_timestamp) {
        double penalty_tax = 0.0;
        if (payment == PaymentEnum.FINANCE) {
            penalty_tax += 0.15;
        }

        if (end_timestamp != -1 && current_status == InvoiceStatusEnum.CLOSE && previous_status == InvoiceStatusEnum.OPEN) {
            long time_delay = end_timestamp * ONE_DAY - start_timestamp*ONE_DAY;
            if (time_delay <= 2 * ONE_WEEK) {
                penalty_tax = 0;
            }
        }

        double total_with_tax = total + rate * total;
        return total_with_tax - total * discount + penalty_tax * total;
    }

    public static double calculateDiscounts(DeliveryEnum delivery) {
        double total_discounts = 0.0;

        if (delivery == DeliveryEnum.PICKUP) {
            total_discounts += 0.15;
        }
        return total_discounts;
    }

    public static double calculateExpenses(double current_expense, double quote, int quantity) {
        return current_expense + quote * quantity;
    }
}
