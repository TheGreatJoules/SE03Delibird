package com.csulb.ase.assignment3.models;

import com.sun.istack.internal.Nullable;
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
    @Nullable
    private List<Customer> customerList;
    @Nullable
    private List<SalesPerson> salesPersonList;
}
