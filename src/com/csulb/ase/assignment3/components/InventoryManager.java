package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.inventory.Inventory;
import com.csulb.ase.assignment3.models.inventory.Product;
import com.csulb.ase.assignment3.models.inventory.ColorEnum;
import com.csulb.ase.assignment3.models.inventory.Electronics;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.inventory.Warehouse;
import com.csulb.ase.assignment3.utils.IdentifierUtil;

import java.util.Map;

/**
 * Inventory Manager manages the inventory and any transaction relating to warehouses
 */
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
     * Upload new product to be added to its corresponding warehouse and update inventory
     * @param product the product to be added
     * @return transaction status
     */
    public int createInventory(Product product) {
        this.warehouseManager.createProduct(product);
        this.inventory.setTotal_warehouses(this.warehouseManager.totalWarehouses() + 1);
        this.inventory.setTotal_items(this.inventory.getTotal_items() + 1);
        return 0;
    }

    /**
     * Return the inventory to be queried and read from
     * @return the existing Inventory
     */
    public Inventory readInventory() {
        return this.inventory;
    }

    /**
     * Return the Warehouse to be queried and indexed from
     * @param warehouse_id the hashed id correlated with the warehouse
     * @return return the Warehouse if found
     */
    public Warehouse readWarehouses(String warehouse_id) {
        return this.warehouseManager.readWarehouse(warehouse_id);
    }

    /**
     * Update the provided product by providing the product object to be overwritten with
     * @param product the product to be replaced with the existing product
     * @return transaction status
     */
    public int updateInventory(Product product) {
        if (product == null) {
            return -1;
        }
        this.warehouseManager.updateProduct(product);
        return 0;
    }

    /**
     * Update the provided product stock by searching the product id
     * @param product_id the hashed id correlated with the product
     * @param quantity the amount to add
     * @return transaction status
     */
    public int updateInventory(String product_id, int quantity) {
        this.warehouseManager.updateStock(product_id, quantity);
        return 0;
    }

    /**
     * Delete the provided product by searching its id and updating the inventory
     * @param product_id the hashed id correlated with the product
     * @return transaction status
     */
    public int deleteInventory(String product_id) {
        int current_items = this.inventory.getTotal_items();
        if (this.warehouseManager.deleteProduct(product_id) == -1) {
            return -1;
        }
        this.inventory.setTotal_items(current_items - 1);
        return 0;
    }

    /**
     * Return the product based on the provided unique identifier
     * @param product_id the hashed id correlated with the product
     * @return the product if it exists
     */
    public Product findProduct(String product_id) {
        return this.warehouseManager.findProduct(product_id);
    }

    /**
     * Create a product based on the provided inputs
     * @param id
     * @param warehouse_id
     * @param type
     * @param manufacturer
     * @param model
     * @param series
     * @param address
     * @param height
     * @param width
     * @param depth
     * @param weight
     * @param display_type
     * @param year
     * @param stock
     * @param sold
     * @param colorEnum
     * @param resolution
     * @param refresh
     * @param output_wattage
     * @param channels
     * @param audio_zone
     * @param wifi_capable
     * @param bluetooth_enabled
     * @param minimum_impedance
     * @return a populated Product
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
}
