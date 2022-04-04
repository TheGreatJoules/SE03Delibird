package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.ColorEnum;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.Stereo;
import com.csulb.ase.assignment3.models.Television;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.GeneratorUtils;

import java.util.Map;
import java.util.Objects;

public class InventoryManager {
    private Inventory inventory;
    private WarehouseManager warehouseManager;

    public InventoryManager() {
        this.inventory = new Inventory();
        this.warehouseManager = new WarehouseManager();
    }

    public InventoryManager(Map<String, Warehouse> warehouses) {
        this.warehouseManager = new WarehouseManager(warehouses);
        this.inventory = new Inventory();
        for (Map.Entry<String, Warehouse> warehouseEntry : warehouses.entrySet()) {
            if (warehouseEntry.getValue().getProducts() != null) {
                for (Map.Entry<String, Product> productEntry : warehouseEntry.getValue().getProducts().entrySet()) {
                    inventory.setTotal_items(inventory.getTotal_items() + 1);
                }
                inventory.setTotal_warehouses(inventory.getTotal_warehouses() + 1);
            }
        }
    }

    public Product createTelevision(String name, String display_type, String manufacturer, String dimensions, ColorEnum colorEnum, Boolean smart) {
        return Television.builder()
                .id(Objects.requireNonNull(GeneratorUtils.generateProductId(ProductEnum.TELEVISION)))
                .product_type(ProductEnum.TELEVISION)
                .product_name(name)
                .manufacturer(manufacturer)
                .dimensions(dimensions)
                .product_color(colorEnum)
                .smart(smart)
                .display_type(display_type)
                .build();
    }

    public Stereo createStereo(String name, String display_type, String manufacturer, String dimensions, ColorEnum colorEnum, Boolean smart) {
        return Stereo.builder()
                .id(Objects.requireNonNull(GeneratorUtils.generateProductId(ProductEnum.STEREO)))
                .product_color(colorEnum)
                .build();
    }

    public int createInventory(Product product) {
        int current_items = this.inventory.getTotal_items();
        int current_warehouse = this.inventory.getTotal_warehouses();
        switch (this.warehouseManager.createProduct(product)) {
            case -1:
                break;
            case 0:
                this.inventory.setTotal_items(current_items + 1);
                break;
            case 1:
                this.inventory.setTotal_items(current_items + 1);
                this.inventory.setTotal_warehouses(current_warehouse + 1);
                break;
        }
        return 0;
    }

    public Inventory readInventory() {
        return this.inventory;
    }

    public Warehouse readWarehouses(String warehouse_id) {
        return this.warehouseManager.readWarehouse(warehouse_id);
    }

    public int updateInventory(Product product) {
        if (product == null) {
            return -1;
        }
        this.warehouseManager.updateProduct(product);
        return 0;
    }

    public int deleteInventory(String product_id) {
        int current_items = this.inventory.getTotal_items();
        int current_warehouse = this.inventory.getTotal_warehouses();
        switch (this.warehouseManager.deleteProduct(product_id)) {
            case -1:
                break;
            case 0:
                this.inventory.setTotal_items(current_items - 1);
                break;
        }
        return 0;
    }
}
