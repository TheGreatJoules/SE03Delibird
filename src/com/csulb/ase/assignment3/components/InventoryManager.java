package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.ColorEnum;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.Stereo;
import com.csulb.ase.assignment3.models.Television;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.ProjectUtils;

import java.util.Map;
import java.util.Objects;

public class InventoryManager {
    private Inventory inventory;
    private WarehouseManager warehouseManager;
    private InvoiceManager invoiceManager;

    public InventoryManager() {
        this.inventory = new Inventory();
        this.warehouseManager = new WarehouseManager();
        this.invoiceManager = new InvoiceManager();
    }

    public InventoryManager(Inventory inventory, Map<String, Warehouse> warehouses) {
        this.inventory = inventory;
        this.warehouseManager = new WarehouseManager(warehouses);
    }

    public Product createTelevision(String name, String display_type, String manufacturer, String dimensions, ColorEnum colorEnum, Boolean smart) {
        Television product = Television.builder()
                .id(Objects.requireNonNull(ProjectUtils.generateProductId(ProductEnum.TELEVISION)))
                .product_type(ProductEnum.TELEVISION)
                .product_name(name)
                .manufacturer(manufacturer)
                .dimensions(dimensions)
                .product_color(colorEnum)
                .smart(smart)
                .display_type(display_type)
                .build();
        return product;
    }

    public Stereo createStereo(String name, String display_type, String manufacturer, String dimensions, ColorEnum colorEnum, Boolean smart) {
        Stereo stereo = Stereo.builder()
                .id(Objects.requireNonNull(ProjectUtils.generateProductId(ProductEnum.STEREO)))
                .product_color(colorEnum)
                .build();
        return stereo;
    }

    public int createInventory(Product product) {
        this.warehouseManager.createProduct(product);
        return 0;
    }

    public Product readProduct(String warehouse_id, String product_id) {
        return this.warehouseManager.readProduct(warehouse_id, product_id);
    }

    public Warehouse readInventory(String warehouse_id) {
        return this.warehouseManager.readWarehouse(warehouse_id);
    }

    public int updateInventory(Product product) {
        this.warehouseManager.updateProduct(product);
        return 0;
    }

    public int deleteInventory(Product product) {
        this.warehouseManager.deleteProduct(product);
        return 0;
    }
}
