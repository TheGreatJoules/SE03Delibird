package com.csulb.ase.assignment3.controller;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.models.BusinessStatus;
import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.DeliveryEnum;
import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.PaymentEnum;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.models.Supplier;
import com.csulb.ase.assignment3.models.SupplierType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OwnerController {
    private Owner owner;
    private Inventory inventoryManager;
    private List<Person> customerList;
    private List<Person> supplierList;
    private List<Person> salespersonList;

    public OwnerController(Owner owner) {
        this.owner = owner;
        this.customerList = new ArrayList<>();
        this.supplierList = new ArrayList<>();
        this.salespersonList = new ArrayList<>();
        this.inventoryManager = new InventoryManager();
    }

    public Person createCustomer(String firstname, String middleName, String lastname, String phone_number, String address, Date start,
                                 DeliveryEnum preferred_delivery, PaymentEnum preferred_payment) {
        Person customer = Customer.builder()
                .first_name(firstname)
                .middle_name(middleName)
                .last_name(lastname)
                .phone_number(phone_number)
                .address(address)
                .start(start)
                .preferred_delivery(preferred_delivery)
                .preferred_payment(preferred_payment)
                .build();
        customerList.add(customer);
        return customer;
    }

    public Person createSupplier(String firstname, String middleName, String lastname, String phone_number, String address, Date start,
                                 double quote, SupplierType supplierType, BusinessStatus businessStatus) {
        Person supplier = Supplier.builder()
                .first_name(firstname)
                .middle_name(middleName)
                .last_name(lastname)
                .phone_number(phone_number)
                .address(address)
                .start(start)
                .quote(quote)
                .supplies(supplierType)
                .status(businessStatus)
                .build();
        supplierList.add(supplier);
        return supplier;
    }

    public Person createSalesPerson(String firstname, String middleName, String lastname, String phone_number, String address, Date start,
                                    int totalSales, double commissionRate, double performanceScore) {
        Person salesperson = SalesPerson.builder()
                .first_name(firstname)
                .middle_name(middleName)
                .last_name(lastname)
                .phone_number(phone_number)
                .address(address)
                .start(start)
                .total_sales(totalSales)
                .commission_rate(commissionRate)
                .performance_score(performanceScore)
                .build();
        salespersonList.add(salesperson);
        return salesperson;
    }

}
