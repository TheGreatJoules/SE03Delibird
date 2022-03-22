package com.csulb.ase.assignment3.models;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public abstract class Person {
    @NonNull
    private String id;
    @NonNull
    private String first_name;
    @Nullable
    private String middle_name;
    @NonNull
    private String last_name;
    @Nullable
    private String phone_number;
    @NonNull
    private String address;
    @NotNull
    private String email;
    @NonNull
    private Date start;
    @Nullable
    private Date end;
}
