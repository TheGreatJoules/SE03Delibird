package com.csulb.ase.assignment3.usecases;

import com.csulb.ase.assignment3.controller.OwnerController;
import com.csulb.ase.assignment3.models.Inventory;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.utils.LoadUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestOwnerRoles {
    private static final String OWNER_PATH = "tests/com/csulb/ase/assignment3/data/owner.json";
    private static final String PERSONS_PATH = "tests/com/csulb/ase/assignment3/data/persons.json";
    private static final String ORDERS_PATH = "tests/com/csulb/ase/assignment3/data/orders.json";
    private static final String PRODUCTS_PATH = "tests/com/csulb/ase/assignment3/data/products.json";
    private Owner owner;
    private Inventory inventory;

    @BeforeSuite
    public void setup() throws IOException {
        owner = LoadUtils.loadOwnerFromJson(OWNER_PATH, PERSONS_PATH, ORDERS_PATH);
        inventory = LoadUtils.loadInventoryFromJson(PRODUCTS_PATH);
    }

    @Test
    public void test_CreateInvoice_Successful(){
        OwnerController ownerController = new OwnerController(owner);

    }

    @Test
    public void test_UpdateInvoice_Successful(){
    }

}
