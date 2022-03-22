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

public class InventoryManager implements Inventory {
    private int totalItems;
    private Map<String, Warehouse> warehouseList;

    public InventoryManager() {
        this.totalItems = 0;
        this.warehouseList = new HashMap<>();
    }


    @Override
    public void CreateWarehouse(String name, String address, RegionEnum regionEnum, List<Product> productList) {
        String id = ProjectUtils.generateEntityId(ComponentEnum.WAREHOUSE);
        Warehouse warehouse = new StoreManager(id, name, address, regionEnum, productList);
        warehouseList.put(id, warehouse);
    }

    @Override
    public void ReadWarehouse() {

    }

    @Override
    public void UpdateWarehouse() {

    }

    @Override
    public void DeleteWarehouse() {

    }
}
