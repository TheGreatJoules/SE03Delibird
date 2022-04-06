package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.controller.OwnerController;
import com.csulb.ase.assignment3.models.Order;
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
    private PersonManager personManager;
    private InvoiceManager invoiceManager;
    private InventoryManager inventoryManager;
    private OwnerController ownerController;

    @BeforeSuite
    public void setup() throws IOException {
        invoiceManager = LoadUtils.getInvoiceFromJson(ORDERS_PATH);
        inventoryManager = LoadUtils.loadInventoryManagerFromJson(PRODUCTS_PATH);
        personManager = LoadUtils.loadPersonManagerFromJson(PERSONS_PATH);
        owner = LoadUtils.loadOwnerFromJson(OWNER_PATH, PERSONS_PATH);
        ownerController = new OwnerController(owner, inventoryManager, invoiceManager, personManager);
    }

    @Test(dataProvider = "add-persons")
    public void test_CreateReadPerson_Successful(String str) {
        Person exact = LoadUtils.loadPersonFromJson(str);
        int transaction_status = this.ownerController.createPerson(Objects.requireNonNull(exact));
        assert transaction_status == 0;
        Person actual = this.ownerController.readPerson(exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "add-products")
    public void test_CreateReadProduct_Successful(String str) {
        Product exact = LoadUtils.getProductFromJson(str);
        int transaction_status = this.ownerController.createProduct(exact);
        assert transaction_status == 0;
        Product actual = this.ownerController.retrieveProduct(Objects.requireNonNull(exact).getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "update-products")
    public void test_UpdateReadProduct_Successful(String str) {
        Product exact = LoadUtils.getProductFromJson(str);
        int transaction_status = this.ownerController.updateProduct(exact);
        assert transaction_status == 0;
        Product actual = this.ownerController.retrieveProduct(Objects.requireNonNull(exact).getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "delete-products")
    public void test_DeleteReadProduct_Successful(String product_id) {
        int transaction_status = this.ownerController.deleteProduct(product_id);
        assert transaction_status == 0;
        Product actual = this.ownerController.retrieveProduct(product_id);
        assert actual == null;
    }

    @Test(dataProvider = "add-orders")
    public void test_AddReadOrder_Successful(String str) {
        Order exact = LoadUtils.getOrderFromJson(str);
        int transaction_status = this.ownerController.createOrder(exact);
        assert transaction_status == 0;
        Order actual = this.ownerController.readOrder(Objects.requireNonNull(exact).getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "delete-orders")
    public void test_DeleteReadOrder_Successful(String str) {
        Order exact = LoadUtils.getOrderFromJson(str);
        int transaction_status = this.ownerController.deleteOrder(Objects.requireNonNull(exact).getId());
        assert transaction_status == 0;
        Order actual = this.ownerController.readOrder(Objects.requireNonNull(exact).getId());
        assert actual == null;
    }

    @Test(dataProvider = "update-orders")
    public void test_UpdateReadOrder_Successful(String str) {
        Order exact = LoadUtils.getOrderFromJson(str);
        int transaction_status = this.ownerController.updateOrder(exact);
        assert transaction_status == 0;
        Order actual = this.ownerController.readOrder(Objects.requireNonNull(exact).getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
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
                {"{\"id\":\"WAR-1:TLV-124\",\"warehouse_address\":\"Long Beach\",\"product_type\":\"TELEVISION\",\"manufacturer\":\"Sony\",\"model_name\":\"KD55X80K\",\"series\":\"X80K\",\"height\":48.63,\"width\":13.38,\"depth\":30.88,\"weight\":49.9,\"product_color\":\"BLACK\",\"year\":2022,\"resolution\":\"4K\",\"display_type\":\"LCD\",\"refresh_type\":\"60 Hz\",\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="update-products")
    public static Object[][] getUpdateProducts() {
        return new Object[][] {
                {"{\"id\":\"WAR-1:STR-123\",\"warehouse_address\":\"Long Beach\",\"product_type\":\"STEREO\",\"manufacturer\":\"Sony\",\"model_name\":\"R-S202BL\",\"series\":\"RX-V\",\"height\":5.5,\"width\":17.125,\"depth\":12.625,\"weight\":14.8,\"product_color\":\"WHITE\",\"output_wattage\":200,\"channels\":2,\"audio_zones\":1,\"minimum_impedance\":\"4 ohms\",\"wifi_capable\":false,\"bluetooth_enabled\":true,\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="delete-products")
    public static Object[][] getDeletedProducts() {
        return new Object[][] {
                {"WAR-1:STR-123"}
        };
    }

    @DataProvider(name="add-orders")
    public static Object[][] getAddedOrders() {
        return new Object[][] {
                {"{\"id\":\"INV-1:CUS-1:ORD-4:STR-235\",\"person_id\":\"CUS-1\",\"product_id\":\"STR-235\",\"product_type\":\"STEREO\",\"timestamp\":1648722131,\"quantity\":5,\"cost\":199.99}"},
                {"{\"id\":\"INV-2:CUS-1:ORD-1:TLV-123\",\"person_id\":\"CUS-1\",\"product_id\":\"TLV-123\",\"product_type\":\"TELEVISION\",\"timestamp\":1648722131,\"quantity\":1,\"cost\":299.99}\n"}
        };
    }

    @DataProvider(name="update-orders")
    public static Object[][] getUpdatedOrders() {
        return new Object[][] {
                {"{\"id\":\"INV-1:CUS-1:ORD-1:TLV-123\",\"person_id\":\"CUS-1\",\"product_id\":\"TLV-123\",\"product_type\":\"TELEVISION\",\"timestamp\":1648722131,\"quantity\":3,\"cost\":299.99}\n"}
        };
    }

    @DataProvider(name="delete-orders")
    public static Object[][] getDeletedOrders() {
        return new Object[][] {
                {"{\"id\":\"INV-1:CUS-1:ORD-1:TLV-123\",\"person_id\":\"CUS-1\",\"product_id\":\"TLV-123\",\"product_type\":\"TELEVISION\",\"timestamp\":1648722131,\"quantity\":1,\"cost\":299.99}\n"}
        };
    }
}