package com.csulb.ase.assignment3.models;

import com.sun.istack.internal.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class Stereo extends Product{
    @Nullable
    private String stereoType;
    @Nullable
    private double outputWattage;
    @Nullable
    private double channels;
}
