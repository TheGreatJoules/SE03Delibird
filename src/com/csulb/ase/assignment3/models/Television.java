package com.csulb.ase.assignment3.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class Television extends Product {
    private String resolution;
    private String displayType;
    private String refreshType;
    private String dimension;
    private boolean smart;
}
