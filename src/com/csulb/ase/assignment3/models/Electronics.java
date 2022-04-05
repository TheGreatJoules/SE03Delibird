package com.csulb.ase.assignment3.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Electronics extends Product{
    private double output_wattage;
    private double channels;
    private double audio_zones;
    private boolean wifi_capable;
    private boolean bluetooth_enabled;
    private String minimum_impedance;
    private String resolution;
    private String display_type;
    private String refresh_type;
    private boolean smart;
}
