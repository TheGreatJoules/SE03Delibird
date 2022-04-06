package com.csulb.ase.assignment3.controller;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.components.InvoiceManager;
import com.csulb.ase.assignment3.components.PersonManager;
import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.DeliveryEnum;
import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Order;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.PaymentEnum;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.PersonEnum;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.utils.CommissionUtil;
import com.csulb.ase.assignment3.utils.IdentifierUtil;

import java.time.Instant;

/**
 * The Controller for the Owner.
 * A set of commands given to the Owner.
 */
public class OwnerController {
    private final Owner owner;
    private InventoryManager inventoryManager;
    private InvoiceManager invoiceManager;
    private PersonManager personManager;

    public OwnerController(Owner owner) {
        this.owner = owner;
        this.inventoryManager = new InventoryManager();
        this.invoiceManager = new InvoiceManager();
        this.personManager = new PersonManager();
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

    public OwnerController(Owner owner, InventoryManager inventoryManager, InvoiceManager invoiceManager, PersonManager personManager) {
        this.owner = owner;
        this.inventoryManager = inventoryManager;
        this.invoiceManager = invoiceManager;
        this.personManager = personManager;
    }

    public OwnerController(String firstname, String lastname, String username, String password, InventoryManager inventoryManager, InvoiceManager invoiceManager) {
        this.owner = Owner.builder()
                .id(IdentifierUtil.generatePersonId(PersonEnum.OWNER))
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
        this.personManager.createPerson(person);
        return 0;
    }

    public Person readPerson(String person_id) {
        return this.personManager.retrievePerson(person_id);
    }

    public Inventory readInventory() {
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

    public int createOrder(Order order, DeliveryEnum deliveryEnum, PaymentEnum paymentEnum) {
        Customer customer = (Customer) personManager.retrievePerson(order.getPerson_id());
        if (this.invoiceManager.createInvoice(order, customer.getAddress(), deliveryEnum, paymentEnum) == 0) {
            this.personManager.updateEmployee(order.getSalesperson_id(), order.getTimestamp());
        }
        return 0;
    }

    public Order readOrder(String order_id) {
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
