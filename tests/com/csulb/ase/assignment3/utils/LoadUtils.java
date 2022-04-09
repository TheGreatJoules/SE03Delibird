package com.csulb.ase.assignment3.utils;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.components.InvoiceManager;
import com.csulb.ase.assignment3.components.PersonManager;
import com.csulb.ase.assignment3.models.persons.Customer;
import com.csulb.ase.assignment3.models.inventory.Electronics;
import com.csulb.ase.assignment3.models.invoices.Invoice;
import com.csulb.ase.assignment3.models.invoices.Order;
import com.csulb.ase.assignment3.models.persons.Owner;
import com.csulb.ase.assignment3.models.persons.Person;
import com.csulb.ase.assignment3.models.persons.PersonEnum;
import com.csulb.ase.assignment3.models.inventory.Product;
import com.csulb.ase.assignment3.models.persons.SalesPerson;
import com.csulb.ase.assignment3.models.persons.Supplier;
import com.csulb.ase.assignment3.models.inventory.Warehouse;
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

/**
 * LoadUtils maps json to model objets and populates components data structures
 */
public class LoadUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static final String ORDERS_PATH = "tests" +
                                                File.separator + "com" +
                                                File.separator + "csulb" +
                                                File.separator + "ase" +
                                                File.separator + "assignment3" +
                                                File.separator + "data" +
                                                File.separator + "orders.json";

    public static final String INVOICES_PATH = "tests" +
                                                File.separator + "com" +
                                                File.separator + "csulb" +
                                                File.separator + "ase" +
                                                File.separator + "assignment3" +
                                                File.separator + "data" +
                                                File.separator + "invoices.json";

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
     * Returns a populated Owner from an owner json item
     * @param owner_path json relative owner path
     * @return A populated Owner object
     * @throws IOException
     */
    public static Owner loadOwnerFromJson(String owner_path) throws IOException {
        return objectMapper.readValue(IOUtils.toString(new FileInputStream(owner_path), StandardCharsets.UTF_8), Owner.class);
    }

    /**
     * Returns a populated PersonManager from list of json person items
     * @param people_path json relative person path
     * @return A populated PersonManager
     * @throws IOException throws any file exception when loading the json items
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
     * Return Person based on its type
     * @param str the deserialize json string
     * @return A populated Person object
     */
    public static Person loadPersonFromJson(String str) {
        try {
            switch(PersonEnum.valueOf(new JSONObject(str).get("person_type").toString())) {
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
     * Returns a populated InvoiceManager from list of json invoice and order items
     * @param invoice_path json relative product path
     * @param order_path json relative order path
     * @return A populated InvoiceManager
     * @throws IOException throws any file exception when loading the json items
     */
    public static InvoiceManager getInvoiceFromJson(String invoice_path, String order_path) throws IOException {
        return new InvoiceManager(loadInvoicesFromJson(invoice_path, order_path));
    }

    /**
     * Returns a populated map of invoices from list of json invoice and order items hashed by their order id
     * @param order_path json relative order path
     * @return invoices - a collection of invoices hashed by their unique identifier
     * @throws IOException throws any file exception when loading the json
     */
    private static Map<String, Invoice> loadOrdersFromJson(Map<String, Invoice> invoices, String order_path) throws IOException {
        String[] items = IOUtils.toString(new FileInputStream(order_path), StandardCharsets.UTF_8).split("\\r?\\n");
        for (String item : items) {
            Order order = objectMapper.readValue(item, Order.class);
            String[] ids = order.getId().split(":");
            double current_cost = invoices.get(ids[0]).getTotal_cost();
            invoices.get(ids[0]).getOrders().put(order.getId(), order);
            invoices.get(ids[0]).setTotal_cost(current_cost + order.getCost());
        }
        return invoices;
    }

    /**
     * a collection of invoices hashed by their unique identifier
     * Generate Invoices from orders
     * @param invoice_path json relative invoice path
     * @return populated map of invoices
     * @throws IOException throws any file exception when loading the json
     */
    private static Map<String, Invoice> loadInvoicesFromJson(String invoice_path, String order_path) throws IOException {
        String[] items = IOUtils.toString(new FileInputStream(invoice_path), StandardCharsets.UTF_8).split("\\r?\\n");
        Map<String, Invoice> invoices = new HashMap<>();

        for (String item : items) {
            Invoice invoice = objectMapper.readValue(item, Invoice.class);
            String[] ids = invoice.getId().split(":");
            invoices.putIfAbsent(ids[0], invoice);
            invoices.get(ids[0]).setOrders(new HashMap<>());
        }
        loadOrdersFromJson(invoices, order_path);
        return invoices;
    }


    /**
     * Returns a populated map of warehouse from list of json product items hashed by their product id
     * @param product_path json relative product path
     * @return Load the Warehouses from list of json products
     * @throws IOException throws any file exception when loading the json
     */
    public static Map<String, Warehouse> loadProductsFromJson(String product_path) throws IOException {
        String[] items = IOUtils.toString(new FileInputStream(product_path), StandardCharsets.UTF_8).split("\\r?\\n");
        Map<String, Warehouse> warehouses = new HashMap<>();
        for (String item : items) {
            Product product = objectMapper.readValue(item, Electronics.class);
            if (warehouses.get(product.getWarehouse_id()) == null) {
                warehouses.put(product.getWarehouse_id(), Warehouse.builder()
                                .id(product.getWarehouse_id())
                                .address(product.getWarehouse_address())
                                .products(new HashMap<>())
                        .build());
            }
            warehouses.get(product.getWarehouse_id()).getProducts().put(product.getId(), product);
        }
        return warehouses;
    }

    /**
     * Returns a populated InventoryManager from list of json product items
     * @param product_path json relative product path
     * @return populated InventoryManager
     * @throws IOException throws any file exception when loading the json
     */
    public static InventoryManager loadInventoryManagerFromJson(String product_path) throws IOException {
        Map<String, Warehouse> warehouses = LoadUtils.loadProductsFromJson(product_path);
        return new InventoryManager(warehouses);
    }
}
