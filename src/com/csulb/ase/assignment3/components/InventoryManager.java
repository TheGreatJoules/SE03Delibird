package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.inventory.Inventory;
import com.csulb.ase.assignment3.models.inventory.Product;
import com.csulb.ase.assignment3.models.inventory.ColorEnum;
import com.csulb.ase.assignment3.models.inventory.Electronics;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.inventory.Warehouse;
import com.csulb.ase.assignment3.utils.IdentifierUtil;

import java.util.Map;

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

    /**
     *
     * @return
     */
    public Product createProduct(String id, String warehouse_id, ProductEnum type, String manufacturer, String model, String series, String address,
                                 double height, double width, double depth, double weight, String display_type, int year,
                                 int stock, int sold, ColorEnum colorEnum, String resolution, String refresh, Double output_wattage,
                                 Double channels, Double audio_zone, Boolean wifi_capable, Boolean bluetooth_enabled,
                                 String minimum_impedance) {
        return Electronics.builder()
                .id(id != null ? id : IdentifierUtil.generateProductId(ProductEnum.TELEVISION))
                .warehouse_id(warehouse_id)
                .product_type(type)
                .warehouse_address(address)
                .manufacturer(manufacturer)
                .model_name(model)
                .series(series)
                .height(height)
                .width(width)
                .depth(depth)
                .weight(weight)
                .product_color(colorEnum)
                .year(year)
                .display_type(display_type)
                .resolution(resolution)
                .refresh_type(refresh)
                .output_wattage(output_wattage)
                .wifi_capable(wifi_capable)
                .bluetooth_enabled(bluetooth_enabled)
                .channels(channels)
                .audio_zones(audio_zone)
                .minimum_impedance(minimum_impedance)
                .stock_count(stock)
                .sold_count(sold)
                .build();
    }

    /**
     *
     * @param product
     * @return
     */
    public int createInventory(Product product) {
        this.warehouseManager.createProduct(product);
        this.inventory.setTotal_warehouses(this.warehouseManager.totalWarehouses() + 1);
        this.inventory.setTotal_items(this.inventory.getTotal_items() + 1);
        return 0;
    }

    /**
     *
     * @return
     */
    public Inventory readInventory() {
        return this.inventory;
    }

    /**
     *
     * @param warehouse_id
     * @return
     */
    public Warehouse readWarehouses(String warehouse_id) {
        return this.warehouseManager.readWarehouse(warehouse_id);
    }

    /**
     *
     * @param product
     * @return
     */
    public int updateInventory(Product product) {
        if (product == null) {
            return -1;
        }
        this.warehouseManager.updateProduct(product);
        return 0;
    }

    /**
     *
     * @param product_id
     * @param quantity
     * @return
     */
    public int updateInventory(String product_id, int quantity) {
        this.warehouseManager.updateStock(product_id, quantity);
        return 0;
    }

    /**
     *
     * @param product_id
     * @return
     */
    public int deleteInventory(String product_id) {
        int current_items = this.inventory.getTotal_items();
        if (this.warehouseManager.deleteProduct(product_id) == -1) {
            return -1;
        }
        this.inventory.setTotal_items(current_items - 1);
        return 0;
    }

    public Product findProduct(String product_id) {
        return this.warehouseManager.findProduct(product_id);
    }
}
