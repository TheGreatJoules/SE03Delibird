package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ComponentEnum;
import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.RegionEnum;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.ProjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
    private int total_items;
    private int total_warehouses;
    private Inventory inventory;
    private Map<String, Warehouse> warehouses;

    public InventoryManager() {
        this.total_items = 0;
        this.total_warehouses = 0;
        this.inventory = new Inventory();
        this.warehouses = new HashMap<>();
    }

    public InventoryManager(Inventory inventory, Map<String, Warehouse> warehouses) {
        this.inventory = inventory;
        this.warehouses = warehouses;
    }

    public void CreateWarehouse(String name, String address, RegionEnum regionEnum, Map<ProductEnum, List<Product>> products) {
        String id = ProjectUtils.generateEntityId(ComponentEnum.WAREHOUSE);
        Warehouse warehouse = Warehouse.builder()
                .id(id)
                .total_items(0)
                .address(address)
                .products(products != null ? products : new HashMap<>())
                .build();
        this.warehouses.put(id, warehouse);
    }

    public void ReadWarehouse() {
    }

    public void UpdateWarehouse() {

    }

    public void DeleteWarehouse() {

    }
}
