package com.csulb.ase.assignment3.models;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Supplier extends Person{
    private double quote;
    private SupplierType type;
    private BusinessStatus status;

}
