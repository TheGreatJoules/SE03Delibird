package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ComponentEnum;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.GeneratorUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WarehouseManager {
    private Map<String, Warehouse> warehouses;
    private int total_items;
    private int total_warehouses;

    public WarehouseManager() {
        this.total_items = 0;
        this.total_warehouses = 0;
        this.warehouses = new HashMap<>();
    }

    public WarehouseManager(Map<String, Warehouse> warehouses) {
        this.total_warehouses = 0;
        this.total_items = 0;
        for (Map.Entry<String, Warehouse> warehouseEntry : warehouses.entrySet()) {
            this.total_items += warehouseEntry.getValue().getTotal_items();
            this.total_warehouses += 1;
        }
        this.warehouses = warehouses;
    }

    /**
     * Find a product by providing the required ids to query
     * @param product_id
     * @return product or null
     */
    public Product readProduct(String product_id) {
        String[] ids = product_id.split(":");

        if (readWarehouse(ids[1]).getProducts() == null) {
            return null;
        }
        if (readWarehouse(ids[1]).getProducts().get(product_id) == null) {
            return null;
        }
        return readWarehouse(ids[1]).getProducts().get(product_id);
    }

    /**
     * Create a product and place it in the respected warehouse
     * @param product
     * @return status code
     */
    public int createProduct(Product product) {
        int response = 0;
        if (product == null) {
            return -1;
        }
        String[] ids = product.getId().split(":");
        Warehouse warehouse = this.warehouses.get(ids[1]);
        if (warehouse == null) {
            warehouse = createWarehouse(product.getWarehouse_address(), null);
            this.total_warehouses += 1;
            response += 1;
        }
        if (warehouse.getProducts().get(product.getId()) == null) {
            response += 1;
        }
        warehouse.getProducts().put(product.getId(), product);
        this.total_items += 1;
        return response;
    }

    /**
     * Update Product by providing new product to replace with updated fields
     * @param product
     * @return status code
     */
    public int updateProduct(Product product) {
        if (product == null) {
            return -1;
        }
        String[] ids = product.getId().split(":");
        Warehouse warehouse = this.warehouses.get(ids[1]);
        warehouse.getProducts().put(product.getId(), product);
        return 0;
    }

    /**
     * Delete Product by providing a product to delete
     * @param product_id
     * @return status code
     */
    public int deleteProduct(String product_id) {
        int response = 0;
        if (product_id == null) {
            return -1;
        }
        String[] ids = product_id.split(":");
        Warehouse warehouse = this.warehouses.get(ids[1]);
        if (warehouse.getProducts().get(product_id) == null) {
            return -1;
        }
        warehouse.getProducts().remove(product_id);
        this.total_items -= 1;
        return 0;
    }

    public Warehouse createWarehouse(String address, Map<String, Product> products) {
        Warehouse warehouse = Warehouse.builder()
                .id(Objects.requireNonNull(GeneratorUtils.generateEntityId(ComponentEnum.WAREHOUSE)))
                .total_items(0)
                .address(address)
                .products(products != null ? products : new HashMap<>())
                .build();
        this.warehouses.put(warehouse.getId(), warehouse);
        this.total_warehouses += 1;
        return warehouse;
    }

    public Warehouse readWarehouse(String warehouse_id) {
        return warehouses.get(warehouse_id);
    }

    public int updateWarehouse(String warehouse_id, String address) {
        Warehouse warehouse = this.warehouses.get(warehouse_id);
        if (warehouse == null) {
            return -1;
        }
        warehouse.setAddress(address);
        return 0;
    }

    public int deleteWarehouse(String warehouse_id) {
        Warehouse warehouse = this.warehouses.get(warehouse_id);
        this.total_items -= warehouse.getTotal_items();
        this.warehouses.remove(warehouse_id);
        this.total_warehouses -= 1;
        return 0;
    }
}
