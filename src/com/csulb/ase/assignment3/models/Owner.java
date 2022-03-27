package com.csulb.ase.assignment3.models;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Getter
@SuperBuilder
public class Owner extends Person {
    @NonNull
    private String username;
    @NonNull
    private String password;
    private List<Customer> customerList;
    private List<SalesPerson> salesPersonList;
}
