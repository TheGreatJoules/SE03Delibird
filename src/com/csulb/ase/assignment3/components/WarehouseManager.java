package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ComponentEnum;
import com.csulb.ase.assignment3.models.inventory.Product;
import com.csulb.ase.assignment3.models.inventory.Warehouse;
import com.csulb.ase.assignment3.utils.IdentifierUtil;

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

    /**
     *
     * @param warehouses
     */
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
     *
     * @param product_id
     * @return
     */
    public Product findProduct(String product_id) {
        return readProduct(product_id, findWarehouse(product_id));
    }

    /**
     *
     * @param product_id
     * @param warehouse_id
     * @return
     */
    public Product readProduct(String product_id, String warehouse_id) {
        if (readWarehouse(warehouse_id) == null) {
            return null;
        }

        if (readWarehouse(warehouse_id).getProducts() == null) {
            return null;
        }
        if (readWarehouse(warehouse_id).getProducts().get(product_id) == null) {
            return null;
        }
        return readWarehouse(warehouse_id).getProducts().get(product_id);
    }

    /**
     *
     * @param product_id
     * @return
     */
    public String findWarehouse(String product_id) {
        for (Map.Entry<String, Warehouse> warehouseEntry : this.warehouses.entrySet()) {
            Map<String, Product> warehouse = warehouseEntry.getValue().getProducts();
            for (Map.Entry<String, Product> product : warehouse.entrySet()) {
                if (product.getKey().equals(product_id)) {
                    return warehouseEntry.getKey();
                }
            }
        }
        return null;
    }

    /**
     * Create a product and place it in the respected warehouse
     * @param product
     * @return status code
     */
    public int createProduct(Product product) {
        if (product == null) {
            return -1;
        }
        String[] ids = product.getId().split(":");

        if (!this.warehouses.containsKey(ids[0])) {
            createWarehouse(ids[0], product.getWarehouse_address(), null);
            this.total_warehouses += 1;
        }

        Warehouse warehouse = this.warehouses.get(ids[0]);
        warehouse.getProducts().put(product.getId(), product);
        this.total_items += 1;
        return 0;
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
        Warehouse warehouse = this.warehouses.get(product.getWarehouse_id());
        warehouse.getProducts().put(product.getId(), product);
        return 0;
    }

    /**
     *
     * @param product_id
     * @param quantity
     * @return
     */
    public int updateStock(String product_id, int quantity) {
        Product product = readProduct(product_id, findWarehouse(product_id));
        if (quantity < 0) {
            product.setStock_count(product.getStock_count() + quantity);
            product.setSold_count(product.getSold_count() + quantity*-1);
        } else {
            product.setStock_count(product.getStock_count() + quantity);
        }
        Warehouse warehouse = readWarehouse(product.getWarehouse_id());
        warehouse.setTotal_items(warehouse.getTotal_items() + quantity);
        return 0;
    }

    /**
     * Delete Product by providing a product to delete
     * @param product_id
     * @return status code
     */
    public int deleteProduct(String product_id) {
        if (product_id == null) {
            return -1;
        }
        Warehouse warehouse = this.warehouses.get(findWarehouse(product_id));
        if (warehouse.getProducts().get(product_id) == null) {
            return -1;
        }
        warehouse.getProducts().remove(product_id);
        this.total_items -= 1;
        return 0;
    }

    /**
     * s
     * @param id
     * @param address
     * @param products
     * @return
     */
    public Warehouse createWarehouse(String id, String address, Map<String, Product> products) {
        Warehouse warehouse = Warehouse.builder()
                .id(id != null ? id : Objects.requireNonNull(IdentifierUtil.generateEntityId(ComponentEnum.WAREHOUSE)))
                .total_items(0)
                .address(address)
                .products(products != null ? products : new HashMap<>())
                .build();
        this.warehouses.put(warehouse.getId(), warehouse);
        this.total_warehouses += 1;
        return warehouse;
    }

    /**
     *
     * @param warehouse_id
     * @return
     */
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

    /**
     *
     * @param warehouse_id
     * @return
     */
    public int deleteWarehouse(String warehouse_id) {
        Warehouse warehouse = this.warehouses.get(warehouse_id);
        this.total_items -= warehouse.getTotal_items();
        this.warehouses.remove(warehouse_id);
        this.total_warehouses -= 1;
        return 0;
    }

    /**
     *
     * @return
     */
    public int totalWarehouses() {
        return warehouses.size();
    }
}
