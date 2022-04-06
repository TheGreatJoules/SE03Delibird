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
    private String person_id;
    @NonNull
    private long timestamp;
    private String street;
    private String state;
    private String city;
    private String zipcode;
    private double total_cost;
    private double total_adjusted_cost;
    private PaymentEnum paymentEnum;
    private DeliveryEnum deliveryEnum;
    private Map<String, Order> orders;
}
