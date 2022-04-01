package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ComponentEnum;
import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.RegionEnum;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.ProjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
    private int totalItems;
    private int total_warehouses;
    private Inventory inventory;

    public InventoryManager() {
        this.totalItems = 0;
        this.total_warehouses = 0;
        this.inventory = new Inventory();
    }

    public InventoryManager(Inventory inventory) {
        this.inventory = inventory;
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
