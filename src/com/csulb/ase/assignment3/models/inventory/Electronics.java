package com.csulb.ase.assignment3.models.inventory;

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
public class Electronics extends Product {
    private Double output_wattage;
    private Double channels;
    private Double audio_zones;
    private Boolean wifi_capable;
    private Boolean bluetooth_enabled;
    private String minimum_impedance;
    private String resolution;
    private String display_type;
    private String refresh_type;
    private Boolean smart;
}
