package com.csulb.ase.assignment3.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
public class Stereo extends Product{
    private String stereoType;
    private double outputWattage;
    private double channels;
}
