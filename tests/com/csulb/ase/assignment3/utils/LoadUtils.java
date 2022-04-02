package com.csulb.ase.assignment3.utils;

import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Invoice;
import com.csulb.ase.assignment3.models.Order;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.PersonEnum;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.models.Stereo;
import com.csulb.ase.assignment3.models.Supplier;
import com.csulb.ase.assignment3.models.Television;
import com.csulb.ase.assignment3.models.Warehouse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Load the owner graph with corresponding data from people and invoices
     * @param owner_path
     * @param people_path
     * @param order_path
     * @return owner
     * @throws IOException
     */
    public static Owner loadOwnerFromJson(String owner_path, String people_path, String order_path) throws IOException {
        Owner owner = objectMapper.readValue(IOUtils.toString(new FileInputStream(owner_path), StandardCharsets.UTF_8), Owner.class);
        String[] persons = IOUtils.toString(new FileInputStream(people_path), StandardCharsets.UTF_8).split("\\r?\\n");

        for (String person : persons) {
            JSONObject jsonItem = new JSONObject(person);
            switch (PersonEnum.valueOf(jsonItem.get("person_type").toString())) {
                case OWNER:
                    break;
                case CUSTOMER:
                    if (owner.getCustomers() == null) {
                        owner.setCustomers(new HashMap<>());
                    }
                    Customer customer = objectMapper.readValue(person, Customer.class);
                    owner.getCustomers().put(customer.getId(), customer);
                    break;
                case SALESPERSON:
                    if (owner.getSalesPersons() == null) {
                        owner.setSalesPersons(new HashMap<>());
                    }
                    SalesPerson salesPerson = objectMapper.readValue(person, SalesPerson.class);
                    owner.getSalesPersons().put(salesPerson.getId(), salesPerson);
                    break;
                case SUPPLIER:
                    if (owner.getSuppliers() == null) {
                        owner.setSuppliers(new HashMap<>());
                    }
                    Supplier supplier = objectMapper.readValue(person, Supplier.class);
                    owner.getSuppliers().put(supplier.getId(), supplier);
                    break;
                default:
            }
        }

        owner.setInvoices(loadInvoicesFromJson(order_path));
        return owner;
    }

    /**
     * Generate Invoices from orders
     * @param order_path
     * @return invoices - a collection of invoices hashed by their unique identifier
     * @throws IOException
     */
    private static Map<String, Invoice> loadInvoicesFromJson(String order_path) throws IOException {
        String[] items = IOUtils.toString(new FileInputStream(order_path), StandardCharsets.UTF_8).split("\\r?\\n");
        Map<String, Invoice> invoices = new HashMap<>();
        for (String item : items) {
            Order order = objectMapper.readValue(item, Order.class);
            if (invoices.get(order.getInvoice_id()) == null) {
                invoices.put(order.getInvoice_id(), Invoice.builder()
                                .id(order.getInvoice_id())
                                .timestamp(order.getTimestamp())
                                .orders(new ArrayList<>())
                        .build());
            }
            double current_cost = invoices.get(order.getInvoice_id()).getTotal_cost();
            invoices.get(order.getInvoice_id()).getOrders().add(order);
            invoices.get(order.getInvoice_id()).setTotal_cost(current_cost + order.getCost());
        }
        return invoices;
    }

    /**
     * Load the Inventor from list of product jsons
     * @param product_path
     * @return inventory
     * @throws IOException
     */
    public static Map<String, Warehouse> loadProductsFromJson(String product_path) throws IOException {
        String[] items = IOUtils.toString(new FileInputStream(product_path), StandardCharsets.UTF_8).split("\\r?\\n");
        Map<String, Warehouse> warehouses = new HashMap<>();
        for (String item : items) {
            JSONObject jsonItem = new JSONObject(item);
            switch (ProductEnum.valueOf(jsonItem.get("product_type").toString())) {
                case TELEVISION:
                    Television television = objectMapper.readValue(item, Television.class);
                    if (warehouses.get(television.getWarehouse_id()) == null) {
                        warehouses.put(television.getWarehouse_id(), Warehouse.builder()
                                        .id(television.getWarehouse_id())
                                        .address(television.getWarehouse_address())
                                        .products(new HashMap<>())
                                .build());
                    }
                    warehouses.get(television.getWarehouse_id()).getProducts().computeIfAbsent(ProductEnum.TELEVISION, k -> new ArrayList<>());
                    warehouses.get(television.getWarehouse_id()).getProducts().get(ProductEnum.TELEVISION).add(television);
                    break;
                case STEREO:
                    Stereo stereo = objectMapper.readValue(item, Stereo.class);
                    if (warehouses.get(stereo.getWarehouse_id()) == null) {
                        warehouses.put(stereo.getWarehouse_id(), Warehouse.builder()
                                .id(stereo.getWarehouse_id())
                                .address(stereo.getWarehouse_address())
                                .products(new HashMap<>())
                                .build());
                    }
                    warehouses.get(stereo.getWarehouse_id()).getProducts().computeIfAbsent(ProductEnum.STEREO, k -> new ArrayList<>());
                    warehouses.get(stereo.getWarehouse_id()).getProducts().get(ProductEnum.TELEVISION).add(stereo);
                    break;
            }
        }
        return warehouses;
    }

    public static Inventory loadInventoryFromWarehouses(Map<String, Warehouse> warehouses) {
        Inventory inventory = new Inventory();
        for (Map.Entry<String, Warehouse> warehouseEntry : warehouses.entrySet()) {
            if (warehouseEntry.getValue().getProducts() != null) {
                for (Map.Entry<ProductEnum, List<Product>> productEntry : warehouseEntry.getValue().getProducts().entrySet()) {
                    inventory.setTotal_items(inventory.getTotal_items() + productEntry.getValue().size());
                }
                inventory.setTotal_warehouses(inventory.getTotal_warehouses() + 1);
            }
        }
        return inventory;
    }

}
