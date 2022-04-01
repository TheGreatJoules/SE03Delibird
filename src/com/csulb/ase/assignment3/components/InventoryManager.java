package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ComponentEnum;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.RegionEnum;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.ProjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
    private int totalItems;
    private Map<String, Warehouse> warehouseList;

    public InventoryManager() {
        this.totalItems = 0;
        this.warehouseList = new HashMap<>();
    }

    public void CreateWarehouse(String name, String address, RegionEnum regionEnum, List<Product> productList) {
        String id = ProjectUtils.generateEntityId(ComponentEnum.WAREHOUSE);
    }

    public void ReadWarehouse() {

    }

    public void UpdateWarehouse() {

    }

    public void DeleteWarehouse() {

    }
}
