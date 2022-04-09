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
 * The OwnerController maintains and orchestras all transaction of the store
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
     * Upload person the person directory map
     * @param person
     * @return transaction status
     */
    public int createPerson(Person person) {
        this.personManager.createPerson(person);
        return 0;
    }

    /**
     * Return the person based on the provided unique identifier
     * @param person_id the hashed id correlated with the person
     * @return the person if they exists
     */
    public Person readPerson(String person_id) {
        return this.personManager.retrievePerson(person_id);
    }

    /**
     * Return the Inventory to be queried and indexed
     * @return the respected inventory of the owner
     */
    public Inventory readInventory() {
        return this.inventoryManager.readInventory();
    }

    /**
     * Update the product stock and adjust owner expenses with suppliers quote
     * @param supplier_id the hashed id correlated with the supplier
     * @param product_id the hashed id correlated with the product
     * @param quantity the amount of products
     * @return transaction status
     */
    public int updateInventory(String supplier_id, String product_id, int quantity) {
        Supplier supplier = (Supplier) this.personManager.retrievePerson(supplier_id);
        this.owner.setExpense(ExpenseUtil.calculateExpenses(this.owner.getExpense(), supplier.getQuote(), quantity));
        this.inventoryManager.updateInventory(product_id, quantity);
        return 0;
    }

    /**
     * Upload new product and register it in the inventory based on its unique identifier
     * @param product The product to be uploaded
     * @return transaction status
     */
    public int createProduct(Product product) {
        this.inventoryManager.createInventory(product);
        return 0;
    }

    /**
     * Return the product corresponding to the provided unique identifier
     * @param product_id the hashed id correlated with the product
     * @return the product if it exists
     */
    public Product retrieveProduct(String product_id) {
        return this.inventoryManager.findProduct(product_id);
    }

    /**
     * Update the product by replacing the existing one with the provided one
     * @param product Product to replace old
     * @return transaction status
     */
    public int updateProduct(Product product) {
        if (product == null) {
            return -1;
        }
        this.inventoryManager.updateInventory(product);
        return 0;
    }

    /**
     * Delete the product corresponding to the provided unique identifier
     * @param product_id the hashed id correlated with the product
     * @return transaction status
     */
    public int deleteProduct(String product_id) {
        this.inventoryManager.deleteInventory(product_id);
        return 0;
    }

    /**
     * Create new order by providing the corresponding values
     * @param order
     * @param deliveryEnum
     * @param paymentEnum
     * @return transaction status
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
     * Return the order corresponding to the provided unique identifier
     * @param order_id the hashed id correlated with the order
     * @return the order if it exists
     */
    public Order readOrder(String order_id) {
        return this.invoiceManager.readOrder(order_id);
    }

    /**
     * Update the order by replacing the existing one
     * @param order the order to replace previous
     * @return transaction status
     */
    public int updateOrder(Order order) {
        this.invoiceManager.updateOrder(order);
        return 0;
    }

    /**
     * Delete the order corresponding to the provided unique identifier
     * @param order_id the hashed id correlated with the order
     * @return transaction status
     */
    public int deleteOrder(String order_id) {
        this.invoiceManager.deleteOrder(order_id);
        return 0;
    }
}