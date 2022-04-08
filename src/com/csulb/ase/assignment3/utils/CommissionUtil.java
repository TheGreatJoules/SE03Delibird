package com.csulb.ase.assignment3.utils;

public class CommissionUtil {
    private static final long ONE_DAY = 24 * 60 * 60;
    private static final long ONE_WEEK = 7 * 24 * 60 * 60;
    private static final long ONE_MONTH = 30 * 7 * 24 * 60 * 60;

    public static double calculatePerformanceScore(double performance_score, long last_day, long today) {
        // check recent sold item if its within a a month stay the same
        long time_delay = today * ONE_DAY - last_day * ONE_DAY;
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

    public static double calculateCommissionRate(double commission_rate, double old_performance_score, double latest_performance_score) {
        double performance_difference = latest_performance_score - old_performance_score;
        if (performance_difference <= 0) {
            return commission_rate;
        }
        return commission_rate + performance_difference * commission_rate;
    }

    public static int calculateSingleSale(int total_sales) {
        return total_sales + 1;
    }

    public static double calculateEarnings(double commission_rate, double total_sales) {
        return total_sales + commission_rate * total_sales;
    }

}