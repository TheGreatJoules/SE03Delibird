package com.csulb.ase.assignment3.models;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@SuperBuilder
public class Supplier extends Person{
    private double quote;
    private SupplierType type;
    private BusinessStatus status;

}
