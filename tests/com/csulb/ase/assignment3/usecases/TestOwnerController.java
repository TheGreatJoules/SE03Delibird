package com.csulb.ase.assignment3.usecases;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.components.InvoiceManager;
import com.csulb.ase.assignment3.controller.OwnerController;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.utils.LoadUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class TestOwnerController {
    private static final String OWNER_PATH = "tests/com/csulb/ase/assignment3/data/owner.json";
    private static final String PERSONS_PATH = "tests/com/csulb/ase/assignment3/data/persons.json";
    private static final String ORDERS_PATH = "tests/com/csulb/ase/assignment3/data/orders.json";
    private static final String PRODUCTS_PATH = "tests/com/csulb/ase/assignment3/data/products.json";

    private Owner owner;
    private InvoiceManager invoiceManager;
    private InventoryManager inventoryManager;
    private OwnerController ownerController;

    @BeforeSuite
    public void setup() throws IOException {
        invoiceManager = LoadUtils.getInvoiceFromJson(ORDERS_PATH);
        inventoryManager = LoadUtils.loadInventoryManagerFromJson(PRODUCTS_PATH);
        owner = LoadUtils.loadOwnerFromJson(OWNER_PATH, PERSONS_PATH);
        ownerController = new OwnerController(owner, inventoryManager, invoiceManager);
    }

    @Test(dataProvider = "add-persons")
    public void test_CreateReadPerson_Successful(String str) {
        OwnerController ownerController = new OwnerController(owner);
        Person exact = LoadUtils.loadPersonFromJson(str);
        int transaction_status = ownerController.createPerson(Objects.requireNonNull(exact));
        assert transaction_status == 0;
        Person actual = ownerController.retrievePerson(exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "add-products")
    public void test_CreateReadProduct_Successful(String str) {
        OwnerController ownerController = new OwnerController(owner);
        Product exact = LoadUtils.getProductFromJson(str);
        int transaction_status = ownerController.createProduct(exact);
        assert transaction_status == 0;
        Product actual = ownerController.retrieveProduct(Objects.requireNonNull(exact).getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "update-products")
    public void test_UpdateReadProduct_Successful(String str) {
        OwnerController ownerController = new OwnerController(owner, inventoryManager);
        Product exact = LoadUtils.getProductFromJson(str);
        int transaction_status = ownerController.updateProduct(exact);
        assert transaction_status == 0;
        Product actual = ownerController.retrieveProduct(Objects.requireNonNull(exact).getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "delete-products")
    public void test_DeleteReadProduct_Successful(String product_id) {
        OwnerController ownerController = new OwnerController(owner, inventoryManager);
        int transaction_status = ownerController.deleteProduct(product_id);
        assert transaction_status == 0;
        Product actual = ownerController.retrieveProduct(product_id);
        assert actual == null;
    }

    @DataProvider(name="add-persons")
    public static Object[][] getAddedPersons() {
        return new Object[][] {
                {"{\"id\":\"SAL-2\",\"person_type\":\"SALESPERSON\",\"first_name\":\"sally2\",\"last_name\":\"iris2\",\"phone_number\":\"(562) 986-4333\",\"address\":\"1450 Bellflower Blvd, Long Beach, CA 90840\",\"email\":\"sally_iris@csulb.edu\",\"start\":1647721605}"}
        };
    }

    @DataProvider(name="add-products")
    public static Object[][] getAddedProducts() {
        return new Object[][] {
                {"{\"id\":\"PRO-TV-124:WAR-1\",\"warehouse_address\":\"Long Beach\",\"product_type\":\"TELEVISION\",\"manufacturer\":\"Sony\",\"model_name\":\"KD55X80K\",\"series\":\"X80K\",\"height\":48.63,\"width\":13.38,\"depth\":30.88,\"weight\":49.9,\"product_color\":\"BLACK\",\"year\":2022,\"resolution\":\"4K\",\"display_type\":\"LCD\",\"refresh_type\":\"60 Hz\",\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="update-products")
    public static Object[][] getUpdateProducts() {
        return new Object[][] {
                {"{\"id\":\"PRO-STR-123:WAR-1\",\"warehouse_address\":\"Long Beach\",\"product_type\":\"STEREO\",\"manufacturer\":\"Sony\",\"model_name\":\"R-S202BL\",\"series\":\"RX-V\",\"height\":5.5,\"width\":17.125,\"depth\":12.625,\"weight\":14.8,\"product_color\":\"WHITE\",\"output_wattage\":200,\"channels\":2,\"audio_zones\":1,\"minimum_impedance\":\"4 ohms\",\"wifi_capable\":false,\"bluetooth_enabled\":true,\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="delete-products")
    public static Object[][] getDeletedProducts() {
        return new Object[][] {
                {"PRO-STR-123:WAR-1"}
        };
    }
}