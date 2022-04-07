package com.csulb.ase.assignment3.controller;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.components.InvoiceManager;
import com.csulb.ase.assignment3.components.PersonManager;
import com.csulb.ase.assignment3.models.persons.Customer;
import com.csulb.ase.assignment3.models.invoices.DeliveryEnum;
import com.csulb.ase.assignment3.models.inventory.Inventory;
import com.csulb.ase.assignment3.models.invoices.Order;
import com.csulb.ase.assignment3.models.persons.Owner;
import com.csulb.ase.assignment3.models.invoices.PaymentEnum;
import com.csulb.ase.assignment3.models.persons.Person;
import com.csulb.ase.assignment3.models.persons.PersonEnum;
import com.csulb.ase.assignment3.models.inventory.Product;
import com.csulb.ase.assignment3.models.persons.Supplier;
import com.csulb.ase.assignment3.utils.ExpenseUtil;
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

    /**
     *
     * @param person
     * @return
     */
    public int createPerson(Person person) {
        this.personManager.createPerson(person);
        return 0;
    }

    /**
     *
     * @param person_id
     * @return
     */
    public Person readPerson(String person_id) {
        return this.personManager.retrievePerson(person_id);
    }

    /**
     *
     * @return
     */
    public Inventory readInventory() {
        return this.inventoryManager.readInventory();
    }

    /**
     *
     * @param supplier_id
     * @param product_id
     * @param quantity
     * @return
     */
    public int updateInventory(String supplier_id, String product_id, int quantity) {
        Supplier supplier = (Supplier) this.personManager.retrievePerson(supplier_id);
        this.owner.setExpense(ExpenseUtil.calculateExpenses(this.owner.getExpense(), supplier.getQuote(), quantity));
        this.inventoryManager.updateInventory(product_id, quantity);
        return 0;
    }

    /**
     *
     * @param product
     * @return
     */
    public int createProduct(Product product) {
        this.inventoryManager.createInventory(product);
        return 0;
    }

    /**
     *
     * @param product_id
     * @return
     */
    public Product retrieveProduct(String product_id) {
        return this.inventoryManager.findProduct(product_id);
    }

    /**
     *
     * @param product
     * @return
     */
    public int updateProduct(Product product) {
        if (product == null) {
            return -1;
        }
        this.inventoryManager.updateInventory(product);
        return 0;
    }

    /**
     *
     * @param product_id
     * @return
     */
    public int deleteProduct(String product_id) {
        this.inventoryManager.deleteInventory(product_id);
        return 0;
    }

    /**
     *
     * @param order
     * @param deliveryEnum
     * @param paymentEnum
     * @return
     */
    public int createOrder(Order order, DeliveryEnum deliveryEnum, PaymentEnum paymentEnum) {
        Customer customer = (Customer) personManager.retrievePerson(order.getCustomer_id());
        this.invoiceManager.createInvoice(order, customer.getAddress(), deliveryEnum, paymentEnum);
        this.personManager.updateEmployee(order.getSalesperson_id(), order.getTimestamp());
        this.inventoryManager.updateInventory(order.getProduct_id(), order.getQuantity() *-1);
        this.owner.setRevenue(owner.getRevenue() + order.getCost());
        return 0;
    }

    /**
     *
     * @param order_id
     * @return
     */
    public Order readOrder(String order_id) {
        return this.invoiceManager.readOrder(order_id);
    }

    /**
     *
     * @param order
     * @return
     */
    public int updateOrder(Order order) {
        this.invoiceManager.updateOrder(order);
        return 0;
    }

    /**
     *
     * @param order_id
     * @return
     */
    public int deleteOrder(String order_id) {
        this.invoiceManager.deleteOrder(order_id);
        return 0;
    }
}