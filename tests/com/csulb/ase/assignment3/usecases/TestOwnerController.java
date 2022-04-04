package com.csulb.ase.assignment3.usecases;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.components.InvoiceManager;
import com.csulb.ase.assignment3.controller.OwnerController;
import com.csulb.ase.assignment3.models.BusinessStatus;
import com.csulb.ase.assignment3.models.DeliveryEnum;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.PaymentEnum;
import com.csulb.ase.assignment3.models.SupplierType;
import com.csulb.ase.assignment3.utils.LoadUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

public class TestOwnerController {
    private static final String OWNER_PATH = "tests/com/csulb/ase/assignment3/data/owner.json";
    private static final String PERSONS_PATH = "tests/com/csulb/ase/assignment3/data/persons.json";
    private static final String ORDERS_PATH = "tests/com/csulb/ase/assignment3/data/orders.json";
    private static final String PRODUCTS_PATH = "tests/com/csulb/ase/assignment3/data/products.json";
    private Owner owner;
    private InventoryManager inventoryManager;
    private InvoiceManager invoiceManager;

    @BeforeSuite
    public void setup() throws IOException {
        owner = LoadUtils.loadOwnerFromJson(OWNER_PATH, PERSONS_PATH, ORDERS_PATH);
        invoiceManager = LoadUtils.getInvoiceFromJson(ORDERS_PATH);
    }

    @Test
    public void test_CreateData_Successful() {
        OwnerController ownerController = new OwnerController("Steve", "Jobs", "sJobs", "password", null);
        ownerController.createCustomer("bob", "", "ford", "(562) 985-4222", "1250 Bellmotor Blvd, Long Beach, CA 90840","sally_iris@csulb.edu", 1647721600, DeliveryEnum.PICKUP, PaymentEnum.CREDIT);
        ownerController.createSalesPerson("sally", "", "iris", "(562) 985-4333", "1450 Bellflower Blvd, Long Beach, CA 90840","bob_ford@csulb.edu", 1647721600, 0, 0, 0);
        ownerController.createSupplier("ricky", "", "rock", "(562) 985-4444", "1650 Bellstone Blvd, Long Beach, CA 90840", "ricky_rock@csulb.edu" ,1647721600, 0, SupplierType.TELEVISION, BusinessStatus.OBOARDING);
    }

    @Test
    public void test_LoadedData_CreateInvoice_Successful(){
        System.out.println();
    }

    @Test
    public void test_UpdateInvoice_Successful(){
        System.out.println();
    }

}
