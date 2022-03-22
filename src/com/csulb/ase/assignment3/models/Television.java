package com.csulb.ase.assignment3.models;

import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class Television extends Product {
    @Nullable
    private String resolution;
    @Nullable
    private String displayType;
    @Nullable
    private String refreshType;
    @Nullable
    private String dimension;
    @Nullable
    private boolean smart;
}
