package com.csulb.ase.assignment3.utils;

import com.csulb.ase.assignment3.models.SalesPerson;

public class CommissionUtil {
    private static final long ONE_DAY = 24 * 60 * 60;
    private static final long ONE_WEEK = 7 * 24 * 60 * 60;
    private static final long ONE_MONTH = 30 * 7 * 24 * 60 * 60;

    public static double calculateCommissionRate(double commission_rate, long start, long end) {
        // if 10 sales are made increment commission rate

        return 0;
    }

    public static double calculatePerformanceScore(double performance_score, long last_day, long today) {
        // check recent sold item if its within a a month stay the same
        long time_delay = (today - last_day) - ONE_DAY;
        if (time_delay <= ONE_DAY) {
            return 1;
        } else if (time_delay <= ONE_WEEK) {
            return 0.75;
        } else if (time_delay <= ONE_MONTH) {
            return 0.50;
        } else {
            return 0;
        }
    }

    public static int calculateSingleSale(int total_sales) {
        return total_sales + 1;
    }

    public static double calculateEarnings(double commission_rate, double total_sales) {
        return total_sales + commission_rate * total_sales;
    }

}