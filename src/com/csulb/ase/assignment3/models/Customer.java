package com.csulb.ase.assignment3.models;

import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@SuperBuilder
public class Customer extends Person{
    private double sales_tax;
    @Nullable
    private Date last_purchase;
    @Nullable
    private int purchase_quantity;
    private DeliveryEnum preferred_delivery;
    private PaymentEnum preferred_payment;
}
