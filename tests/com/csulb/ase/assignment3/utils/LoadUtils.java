package com.csulb.ase.assignment3.utils;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.components.InvoiceManager;
import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Invoice;
import com.csulb.ase.assignment3.models.Order;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.Person;
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
import java.util.HashMap;
import java.util.Map;

public class LoadUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Load the owner graph with corresponding data from people and invoices
     * @param owner_path
     * @param people_path
     * @return owner
     * @throws IOException
     */
    public static Owner loadOwnerFromJson(String owner_path, String people_path) throws IOException {
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
        return owner;
    }

    public static Person loadPersonFromJson(String str) {
        JSONObject jsonObject = new JSONObject(str);
        try {
            switch(PersonEnum.valueOf(jsonObject.get("person_type").toString())) {
                case CUSTOMER:
                    return objectMapper.readValue(str, Customer.class);
                case SUPPLIER:
                    return objectMapper.readValue(str, Supplier.class);
                case SALESPERSON:
                    return objectMapper.readValue(str, SalesPerson.class);
                default:
                    return null;
            }
        } catch (IOException e) {
            return null;
        }
    }
    /**
     *
     * @param order_path
     * @return
     * @throws IOException
     */
    public static InvoiceManager getInvoiceFromJson(String order_path) throws IOException {
        return new InvoiceManager(loadInvoicesFromJson(order_path));
    }

    /**
     *
     * @param str
     * @return
     */
    public static Order getOrderFromJson(String str) {
        JSONObject jsonItem = new JSONObject(str);
        try {
            return objectMapper.readValue(str, Order.class);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     *
     * @param str
     * @return
     */
    public static Product getProductFromJson(String str) {
        JSONObject jsonItem = new JSONObject(str);
        try {
            switch (ProductEnum.valueOf(jsonItem.get("product_type").toString())) {
                case STEREO:
                    return objectMapper.readValue(str, Stereo.class);
                case TELEVISION:
                    return objectMapper.readValue(str, Television.class);
                default:
                    return null;
            }
        } catch (IOException e) {
            return null;
        }
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
            String[] ids = order.getId().split(":");
            if (invoices.get(ids[0]) == null) {
                invoices.put(ids[0], Invoice.builder()
                                .id(ids[0])
                                .timestamp(order.getTimestamp())
                                .orders(new HashMap<>())
                        .build());
            }
            double current_cost = invoices.get(ids[0]).getTotal_cost();
            invoices.get(ids[0]).getOrders().put(order.getId(), order);
            invoices.get(ids[0]).setTotal_cost(current_cost + order.getCost());
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
        String[] ids;
        String[] items = IOUtils.toString(new FileInputStream(product_path), StandardCharsets.UTF_8).split("\\r?\\n");
        Map<String, Warehouse> warehouses = new HashMap<>();
        for (String item : items) {
            JSONObject jsonItem = new JSONObject(item);
            switch (ProductEnum.valueOf(jsonItem.get("product_type").toString())) {
                case TELEVISION:
                    Television television = objectMapper.readValue(item, Television.class);
                    ids = television.getId().split(":");
                    if (warehouses.get(ids[0]) == null) {
                        warehouses.put(ids[0], Warehouse.builder()
                                        .id(ids[0])
                                        .address(television.getWarehouse_address())
                                        .products(new HashMap<>())
                                .build());
                    }
                    warehouses.get(ids[0]).getProducts().put(television.getId(), television);
                    break;
                case STEREO:
                    Stereo stereo = objectMapper.readValue(item, Stereo.class);
                    ids = stereo.getId().split(":");
                    if (warehouses.get(ids[0]) == null) {
                        warehouses.put(ids[0], Warehouse.builder()
                                .id(ids[0])
                                .address(stereo.getWarehouse_address())
                                .products(new HashMap<>())
                                .build());
                    }
                    warehouses.get(ids[0]).getProducts().put(stereo.getId(), stereo);
                    break;
            }
        }
        return warehouses;
    }

    /**
     *
     * @param product_path
     * @return
     * @throws IOException
     */
    public static InventoryManager loadInventoryManagerFromJson(String product_path) throws IOException {
        Map<String, Warehouse> warehouses = LoadUtils.loadProductsFromJson(product_path);
        return new InventoryManager(warehouses);
    }

    /**
     *
     * @param warehouses
     * @return
     */
    public static Inventory loadInventoryFromWarehouses(Map<String, Warehouse> warehouses) {
        Inventory inventory = new Inventory();
        for (Map.Entry<String, Warehouse> warehouseEntry : warehouses.entrySet()) {
            if (warehouseEntry.getValue().getProducts() != null) {
                for (Map.Entry<String, Product> productEntry : warehouseEntry.getValue().getProducts().entrySet()) {
                    inventory.setTotal_items(inventory.getTotal_items() + 1);
                }
                inventory.setTotal_warehouses(inventory.getTotal_warehouses() + 1);
            }
        }
        return inventory;
    }


}
