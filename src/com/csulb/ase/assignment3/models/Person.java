package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public abstract class Person {
    @NonNull
    private String id;
    @NonNull
    private PersonEnum person_type;
    @NonNull
    private String first_name;
    private String middle_name;
    @NonNull
    private String last_name;
    private String phone_number;
    @NonNull
    private String address;
    private String email;
    @NonNull
    private Date start;
    private Date end;

    public Person() {}
}
