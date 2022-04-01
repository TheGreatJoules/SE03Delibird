package com.csulb.ase.assignment3.controller;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.models.BusinessStatus;
import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.DeliveryEnum;
import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Invoice;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.PaymentEnum;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.PersonEnum;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.models.Supplier;
import com.csulb.ase.assignment3.models.SupplierType;
import com.csulb.ase.assignment3.utils.ProjectUtils;

import java.time.Instant;
import java.util.HashMap;

public class OwnerController {
    private final Owner owner;
    private final InventoryManager inventoryManager;

    public OwnerController(String firstname, String lastname, String username, String password, Inventory inventory) {
        this.owner = Owner.builder()
                .id(ProjectUtils.generatePersonId(PersonEnum.OWNER))
                .person_type(PersonEnum.OWNER)
                .first_name(firstname)
                .last_name(lastname)
                .start(Instant.now().toEpochMilli())
                .username(username)
                .password(password)
                .build();
        this.inventoryManager = new InventoryManager();
    }

    public OwnerController(Owner owner, Inventory inventory) {
        this.owner = owner;
        this.inventoryManager = inventory != null ? new InventoryManager(inventory) : new InventoryManager();
    }

    public Person createCustomer(String firstname, String middleName, String lastname, String phone_number, String email, String address, long start,
                                 DeliveryEnum preferred_delivery, PaymentEnum preferred_payment) {
        Customer customer = Customer.builder()
                .id(ProjectUtils.generatePersonId(PersonEnum.CUSTOMER))
                .person_type(PersonEnum.CUSTOMER)
                .first_name(firstname)
                .middle_name(middleName)
                .last_name(lastname)
                .phone_number(phone_number)
                .email(email)
                .address(address)
                .start(start)
                .preferred_delivery(preferred_delivery)
                .preferred_payment(preferred_payment)
                .build();

        if (owner.getCustomers() == null) {
            owner.setCustomers(new HashMap<>());
        }
        owner.getCustomers().put(customer.getId(), customer);
        return customer;
    }

    public Person createSupplier(String firstname, String middleName, String lastname, String phone_number, String email, String address, long start,
                                 double quote, SupplierType supplierType, BusinessStatus businessStatus) {
        Supplier supplier = Supplier.builder()
                .id(ProjectUtils.generatePersonId(PersonEnum.SUPPLIER))
                .person_type(PersonEnum.SUPPLIER)
                .first_name(firstname)
                .middle_name(middleName)
                .last_name(lastname)
                .phone_number(phone_number)
                .email(email)
                .address(address)
                .start(start)
                .quote(quote)
                .supplies(supplierType)
                .status(businessStatus)
                .build();

        if (owner.getSuppliers() == null) {
            owner.setSuppliers(new HashMap<>());
        }
        owner.getSuppliers().put(supplier.getId(), supplier);
        return supplier;
    }

    public Person createSalesPerson(String firstname, String middleName, String lastname, String phone_number, String email, String address, long start,
                                    int totalSales, double commissionRate, double performanceScore) {
        SalesPerson salesperson = SalesPerson.builder()
                .id(ProjectUtils.generatePersonId(PersonEnum.SALESPERSON))
                .person_type(PersonEnum.SALESPERSON)
                .first_name(firstname)
                .middle_name(middleName)
                .last_name(lastname)
                .phone_number(phone_number)
                .address(address)
                .email(email)
                .start(start)
                .total_sales(totalSales)
                .commission_rate(commissionRate)
                .performance_score(performanceScore)
                .build();

        if (owner.getSalesPersons() == null) {
            owner.setSalesPersons(new HashMap<>());
        }
        owner.getSalesPersons().put(salesperson.getId(), salesperson);
        return salesperson;
    }

}
