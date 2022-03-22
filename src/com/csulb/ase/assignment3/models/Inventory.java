package com.csulb.ase.assignment3.models;

import java.util.List;

public interface Inventory {
    public void CreateWarehouse(String name, String address,  RegionEnum region, List<Product> list);

    public void ReadWarehouse();

    public void UpdateWarehouse();

    public void DeleteWarehouse();
}
