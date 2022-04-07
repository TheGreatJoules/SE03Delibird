package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends Person{
    private Double quote;
    private SupplierType supplies;
    private BusinessStatus status;

}
