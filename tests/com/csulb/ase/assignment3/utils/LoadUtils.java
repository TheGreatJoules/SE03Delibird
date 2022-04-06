package com.csulb.ase.assignment3.utils;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.components.InvoiceManager;
import com.csulb.ase.assignment3.components.PersonManager;
import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.Electronics;
import com.csulb.ase.assignment3.models.Invoice;
import com.csulb.ase.assignment3.models.Order;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.PersonEnum;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.models.Supplier;
import com.csulb.ase.assignment3.models.Warehouse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoadUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final String ORDERS_PATH = "tests" +
                                                File.separator + "com" +
                                                File.separator + "csulb" +
                                                File.separator + "ase" +
                                                File.separator + "assignment3" +
                                                File.separator + "data" +
                                                File.separator + "orders.json";

    public static final String PRODUCT_PATH = "tests" +
                                                File.separator + "com" +
                                                File.separator + "csulb" +
                                                File.separator + "ase" +
                                                File.separator + "assignment3" +
                                                File.separator + "data" +
                                                File.separator + "products.json";

    public static final String OWNER_PATH = "tests" +
                                                File.separator + "com" +
                                                File.separator + "csulb" +
                                                File.separator + "ase" +
                                                File.separator + "assignment3" +
                                                File.separator + "data" +
                                                File.separator + "owner.json";

    public static final String PERSONS_PATH = "tests" +
                                                File.separator + "com" +
                                                File.separator + "csulb" +
                                                File.separator + "ase" +
                                                File.separator + "assignment3" +
                                                File.separator + "data" +
                                                File.separator + "persons.json";

    /**
     * Load the owner graph with corresponding data from people and invoices
     * @param owner_path
     * @param people_path
     * @return owner
     * @throws IOException
     */
    public static Owner loadOwnerFromJson(String owner_path, String people_path) throws IOException {
        return objectMapper.readValue(IOUtils.toString(new FileInputStream(owner_path), StandardCharsets.UTF_8), Owner.class);
    }

    /**
     * Load the PersonManager from a serialized list of json person items
     * @param people_path
     * @return PersonManager
     * @throws IOException
     */
    public static PersonManager loadPersonManagerFromJson(String people_path) throws IOException {
        Map<String, Person> persons = new HashMap<>();
        String[] people = IOUtils.toString(new FileInputStream(people_path), StandardCharsets.UTF_8).split("\\r?\\n");

        for (String person : people) {
            Person p = loadPersonFromJson(person);
            persons.put(Objects.requireNonNull(p).getId(), p);
        }

        return new PersonManager(persons);
    }

    /**
     * Deserialize a json person item to a Person object
     * @param str
     * @return Person
     */
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
     * Load the Invoice Manager from a list of serialized order items
     * @param order_path
     * @return InvoiceManager
     * @throws IOException
     */
    public static InvoiceManager getInvoiceFromJson(String order_path) throws IOException {
        return new InvoiceManager(loadInvoicesFromJson(order_path));
    }

    /**
     * Deserialize json order item to Order object
     * @param str
     * @return Order
     */
    public static Order getOrderFromJson(String str) {
        try {
            return objectMapper.readValue(str, Order.class);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Get a single product item from a serialized json item
     * @param str
     * @return Product
     */
    public static Product getProductFromJson(String str) {
        try {
            return objectMapper.readValue(str, Electronics.class);
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
                                .person_id(ids[1])
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
     * Load the Warehouses from list of json products
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
            Product product = objectMapper.readValue(item, Electronics.class);
            ids = product.getId().split(":");
            if (warehouses.get(ids[0]) == null) {
                warehouses.put(ids[0], Warehouse.builder()
                                .id(ids[0])
                                .address(product.getWarehouse_address())
                                .products(new HashMap<>())
                        .build());
            }
            warehouses.get(ids[0]).getProducts().put(product.getId(), product);
        }
        return warehouses;
    }

    /**
     * Load the Inventory manager from json product items
     * @param product_path
     * @return InventoryManager
     * @throws IOException
     */
    public static InventoryManager loadInventoryManagerFromJson(String product_path) throws IOException {
        Map<String, Warehouse> warehouses = LoadUtils.loadProductsFromJson(product_path);
        return new InventoryManager(warehouses);
    }
}
