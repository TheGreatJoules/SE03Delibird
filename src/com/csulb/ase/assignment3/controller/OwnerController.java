package com.csulb.ase.assignment3.controller;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.components.InvoiceManager;
import com.csulb.ase.assignment3.models.BusinessStatus;
import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.DeliveryEnum;
import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Invoice;
import com.csulb.ase.assignment3.models.Order;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.PaymentEnum;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.PersonEnum;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.models.Supplier;
import com.csulb.ase.assignment3.models.SupplierType;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.GeneratorUtils;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class OwnerController {
    private final Owner owner;
    private InventoryManager inventoryManager;
    private InvoiceManager invoiceManager;

    public OwnerController(Owner owner) {
        this.owner = owner;
        this.inventoryManager = new InventoryManager();
        this.invoiceManager = new InvoiceManager();
    }

    public OwnerController(Owner owner, InventoryManager inventoryManager) {
        this.owner = owner;
        this.inventoryManager = inventoryManager;
        this.invoiceManager = new InvoiceManager();
    }

    public OwnerController(Owner owner, InventoryManager inventoryManager, InvoiceManager invoiceManager) {
        this.owner = owner;
        this.inventoryManager = inventoryManager;
        this.invoiceManager = invoiceManager;
    }

    public OwnerController(String firstname, String lastname, String username, String password, InventoryManager inventoryManager, InvoiceManager invoiceManager) {
        this.owner = Owner.builder()
                .id(GeneratorUtils.generatePersonId(PersonEnum.OWNER))
                .person_type(PersonEnum.OWNER)
                .first_name(firstname)
                .last_name(lastname)
                .start(Instant.now().toEpochMilli())
                .username(username)
                .password(password)
                .build();
        this.inventoryManager = inventoryManager;
        this.invoiceManager = invoiceManager;
    }

    public int createPerson(Person person) {
        switch(person.getPerson_type()) {
            case CUSTOMER:
                if (this.owner.getCustomers() == null) {
                    this.owner.setCustomers(new HashMap<>());
                }
                this.owner.getCustomers().put(person.getId(), (Customer) person);
                return 0;
            case SUPPLIER:
                if (this.owner.getSuppliers() == null) {
                    this.owner.setSuppliers(new HashMap<>());
                }
                this.owner.getSuppliers().put(person.getId(), (Supplier) person);
                return 0;
            case SALESPERSON:
                if (this.owner.getSalesPersons() == null) {
                    this.owner.setSalesPersons(new HashMap<>());
                }
                this.owner.getSalesPersons().put(person.getId(), (SalesPerson) person);
                return 0;
            default:
                return -1;
        }
    }

    public Person retrievePerson(String person_id) {
        String[] person_type = person_id.split("-");
        switch(person_type[0]) {
            case "SAL":
                return this.owner.getSalesPersons().get(person_id);
            case "CUS":
                return this.owner.getCustomers().get(person_id);
            case "SUP":
                return this.owner.getSuppliers().get(person_id);
            default:
                return null;
        }
    }

    public Inventory retrieveInventory() {
        return this.inventoryManager.readInventory();
    }

    public int createProduct(Product product) {
        this.inventoryManager.createInventory(product);
        return 0;
    }

    public Product retrieveProduct(String product_id) {
        String[] ids = product_id.split(":");
        if (this.inventoryManager.readWarehouses(ids[0]) == null) {
            return null;
        }
        return this.inventoryManager.readWarehouses(ids[0]).getProducts().get(product_id);
    }

    public int updateProduct(Product product) {
        if (product == null) {
            return -1;
        }
        this.inventoryManager.updateInventory(product);
        return 0;
    }

    public int deleteProduct(String product_id) {
        this.inventoryManager.deleteInventory(product_id);
        return 0;
    }

    public int createOrder(Order order) {
        this.invoiceManager.createInvoice(order);
        return 0;
    }

    public Order retrieveOrder(String order_id) {
        return this.invoiceManager.readOrder(order_id);
    }

    public int updateOrder(Order order) {
        this.invoiceManager.updateOrder(order);
        return 0;
    }

    public int deleteOrder(String order_id) {
        this.invoiceManager.deleteOrder(order_id);
        return 0;
    }
}
