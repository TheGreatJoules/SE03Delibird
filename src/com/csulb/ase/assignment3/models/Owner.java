package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends Person {
    @NonNull
    private String username;
    @NonNull
    private String password;
    private Map<String, Customer> customers;
    private Map<String, SalesPerson> salesPersons;
    private Map<String, Supplier> suppliers;
    private Map<String, Invoice> invoices;
    private Inventory inventory;
}
