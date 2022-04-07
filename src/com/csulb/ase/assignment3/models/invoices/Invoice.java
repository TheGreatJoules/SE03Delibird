package com.csulb.ase.assignment3.models.invoices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

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
    private String customer_id;
    private String salesperson_id;
    private long timestamp;
    @NonNull
    private String street;
    @NonNull
    private String state;
    @NonNull
    private String city;
    @NonNull
    private String zipcode;
    private double total_cost;
    private double discounts;
    private double tax_rate;
    private double total_adjusted_cost;
    @NonNull
    private PaymentEnum paymentEnum;
    @NonNull
    private DeliveryEnum deliveryEnum;
    private Map<String, Order> orders;
}
