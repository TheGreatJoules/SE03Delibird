package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends Person{
    private double sales_tax;
    private Date last_purchase;
    private int purchase_quantity;
    private DeliveryEnum preferred_delivery;
    private PaymentEnum preferred_payment;
}
