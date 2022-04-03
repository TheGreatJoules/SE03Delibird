package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ComponentEnum;
import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.ColorEnum;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.Stereo;
import com.csulb.ase.assignment3.models.Television;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.ProjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    public int createTelevision(String name, String display_type, String manufacturer, String dimensions, ColorEnum colorEnum, Boolean smart) {
        Product product = Television.builder()
                .id(Objects.requireNonNull(ProjectUtils.generateProductId(ProductEnum.TELEVISION)))
                .product_type(ProductEnum.TELEVISION)
                .product_name(name)
                .manufacturer(manufacturer)
                .dimensions(dimensions)
                .product_color(colorEnum)
                .smart(smart)
                .display_type(display_type)
                .build();
        return 0;
    }

    public int createStereo(String name, String display_type, String manufacturer, String dimensions, ColorEnum colorEnum, Boolean smart) {
        Product stereo = Stereo.builder()
                .id(Objects.requireNonNull(ProjectUtils.generateProductId(ProductEnum.STEREO)))
                .product_color(colorEnum)
                .build();
        return 0;
    }

    public int deleteProduct() {
        return 0;
    }

    public int createWarehouse(String address, Map<ProductEnum, List<Product>> products) {
        Warehouse warehouse = Warehouse.builder()
                .id(Objects.requireNonNull(ProjectUtils.generateEntityId(ComponentEnum.WAREHOUSE)))
                .total_items(0)
                .address(address)
                .products(products != null ? products : new HashMap<>())
                .build();
        this.warehouses.put(warehouse.getId(), warehouse);
        return 0;
    }

    public void readWarehouse() {
    }

    public int updateWarehouse(String id, String address, Map<ProductEnum, List<Product>> products) {
        Warehouse warehouse = this.warehouses.get(id);
        if (address != null) {
            warehouse.setAddress(address);
        }
        if (products != null) {
            warehouse.setProducts(products);
        }
        this.warehouses.put(warehouse.getId(), warehouse);
        total_warehouses += 1;
        return 0;
    }

    public int deleteWarehouse(String id) {
        Warehouse warehouse = this.warehouses.get(id);
        this.total_items -= warehouse.getTotal_items();
        this.warehouses.remove(id);
        this.total_warehouses -= 1;
        return 0;
    }
}
