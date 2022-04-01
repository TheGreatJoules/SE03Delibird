package com.csulb.ase.assignment3.controller;

import com.csulb.ase.assignment3.models.BusinessStatus;
import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.DeliveryEnum;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.PaymentEnum;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.models.Supplier;
import com.csulb.ase.assignment3.models.SupplierType;

import java.util.Date;
import java.util.HashMap;

public class OwnerController {
    private final Owner owner;

    public OwnerController() {
        this.owner = new Owner();
    }

    public OwnerController(Owner owner) {
        this.owner = owner;
    }

    public Person createCustomer(String firstname, String middleName, String lastname, String phone_number, String address, Date start,
                                 DeliveryEnum preferred_delivery, PaymentEnum preferred_payment) {
        Customer customer = Customer.builder()
                .first_name(firstname)
                .middle_name(middleName)
                .last_name(lastname)
                .phone_number(phone_number)
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

    public Person createSupplier(String firstname, String middleName, String lastname, String phone_number, String address, Date start,
                                 double quote, SupplierType supplierType, BusinessStatus businessStatus) {
        Supplier supplier = Supplier.builder()
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

        if (owner.getSuppliers() == null) {
            owner.setSuppliers(new HashMap<>());
        }
        owner.getSuppliers().put(supplier.getId(), supplier);
        return supplier;
    }

    public Person createSalesPerson(String firstname, String middleName, String lastname, String phone_number, String address, Date start,
                                    int totalSales, double commissionRate, double performanceScore) {
        SalesPerson salesperson = SalesPerson.builder()
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

        if (owner.getSalesPersons() == null) {
            owner.setSalesPersons(new HashMap<>());
        }
        owner.getSalesPersons().put(salesperson.getId(), salesperson);
        return salesperson;
    }

}
