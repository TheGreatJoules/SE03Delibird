package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.controller.OwnerController;
import com.csulb.ase.assignment3.models.ColorEnum;
import com.csulb.ase.assignment3.models.Customer;
import com.csulb.ase.assignment3.models.DeliveryEnum;
import com.csulb.ase.assignment3.models.Electronics;
import com.csulb.ase.assignment3.models.Order;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.PaymentEnum;
import com.csulb.ase.assignment3.models.Person;
import com.csulb.ase.assignment3.models.PersonEnum;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.SalesPerson;
import com.csulb.ase.assignment3.models.Supplier;
import com.csulb.ase.assignment3.utils.LoadUtils;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class TestOwnerController {
    private Owner owner;
    private PersonManager personManager;
    private InvoiceManager invoiceManager;
    private InventoryManager inventoryManager;
    private OwnerController ownerController;

    @BeforeSuite
    public void setup() throws IOException {
        invoiceManager = LoadUtils.getInvoiceFromJson(LoadUtils.INVOICES_PATH, LoadUtils.ORDERS_PATH);
        inventoryManager = LoadUtils.loadInventoryManagerFromJson(LoadUtils.PRODUCT_PATH);
        personManager = LoadUtils.loadPersonManagerFromJson(LoadUtils.PERSONS_PATH);
        owner = LoadUtils.loadOwnerFromJson(LoadUtils.OWNER_PATH, LoadUtils.PERSONS_PATH);
        ownerController = new OwnerController(owner, inventoryManager, invoiceManager, personManager);
    }

    @Test(dataProvider = "add-persons")
    public void test_CreateReadPerson_Successful(Person exact) {
        int transaction_status = this.ownerController.createPerson(Objects.requireNonNull(exact));
        assert transaction_status == 0;
        Person actual = this.ownerController.readPerson(exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "add-products")
    public void test_CreateReadProduct_Successful(Product exact) {
        int transaction_status = this.ownerController.createProduct(exact);
        assert transaction_status == 0;
        Product actual = this.ownerController.retrieveProduct(exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "update-products")
    public void test_UpdateReadProduct_Successful(Product exact) {
        int transaction_status = this.ownerController.updateProduct(exact);
        assert transaction_status == 0;
        Product actual = this.ownerController.retrieveProduct(exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "read-products")
    public void test_DeleteReadProduct_Successful(Product product) {
        int transaction_status = this.ownerController.deleteProduct(product.getId());
        assert transaction_status == 0;
        Product actual = this.ownerController.retrieveProduct(product.getId());
        assert actual == null;
    }

    @Test(dataProvider = "add-orders")
    public void test_AddReadOrder_Successful(Order exact) {
        int transaction_status = this.ownerController.createOrder(exact, DeliveryEnum.PICKUP, PaymentEnum.CASH);
        assert transaction_status == 0;
        Order actual = this.ownerController.readOrder(Objects.requireNonNull(exact).getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "read-orders")
    public void test_DeleteReadOrder_Successful(Order exact) {
        int transaction_status = this.ownerController.deleteOrder(exact.getId());
        assert transaction_status == 0;
        Order actual = this.ownerController.readOrder(Objects.requireNonNull(exact).getId());
        assert actual == null;
    }

    @Test(dataProvider = "update-orders")
    public void test_UpdateReadOrder_Successful(Order exact) {
        int transaction_status = this.ownerController.updateOrder(exact);
        assert transaction_status == 0;
        Order actual = this.ownerController.readOrder(exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @DataProvider(name="add-persons")
    public static Object[][] getAddedPersons() {
        return new Object[][] {
                {customer_person_item("CUS-2")},
                {salesperson_person_item("SAL-2")},
                {supplier_person_item("SUP-1")}
        };
    }

    @DataProvider(name="add-products")
    public static Object[][] getAddedProducts() {
        return new Object[][] {
                {electronics_television_item("WAR-1:TLV-123", "Sony", "KD55X80K", "X80K", 5, 0)},
                {electronics_stereo_item("WAR-1:STR-123", "Sony", "R-S202BL","RX-V", 5, 0)}
        };
    }

    @DataProvider(name="update-products")
    public static Object[][] getUpdatedProducts() {
        return new Object[][] {
                {electronics_television_item("WAR-1:TLV-124", "Sony", "KD55X80K", "X81K", 5, 0)}
        };
    }

    @DataProvider(name="read-products")
    public static Object[][] getSavedProducts() {
        return new Object[][] {
                {electronics_television_item("WAR-1:STR-123", "Sony", "R-S202BL", "RX-V", 5, 0)}
        };
    }

    @DataProvider(name="add-orders")
    public static Object[][] getAddedOrders() {
        return new Object[][] {
                {television_order_item("INV-1:CUS-1:ORD-4:TLV-123", 1, 299.99)},
                {stereo_order_item("INV-1:CUS-1:ORD-5:STR-234", 1, 150.99)}
        };
    }

    @DataProvider(name="update-orders")
    public static Object[][] getUpdatedOrders() {
        return new Object[][] {
                {television_order_item("INV-1:CUS-1:ORD-3:STR-234", 1, 199.99)},
                {stereo_order_item("INV-1:CUS-1:ORD-3:STR-234", 2, 299.99)}
        };
    }

    @DataProvider(name="read-orders")
    public static Object[][] getExistOrders() {
        return new Object[][] {
                {television_order_item("INV-1:CUS-1:ORD-1:TLV-123", 1, 299.99)},
                {stereo_order_item("INV-1:CUS-1:ORD-3:STR-234", 2, 199.99)}
        };
    }

    public static Electronics electronics_television_item(String id, String manufacturer, String model, String series, int stock, int sold) {
        return Electronics.builder()
                .id(id)
                .warehouse_address("Newport")
                .product_type(ProductEnum.TELEVISION)
                .manufacturer(manufacturer)
                .model_name(model)
                .series(series)
                .height(50.0)
                .width(15.0)
                .depth(30.0)
                .weight(50.0)
                .product_color(ColorEnum.BLACK)
                .year(2022)
                .resolution("4K")
                .display_type("LCD")
                .refresh_type("60 Hz")
                .stock_count(stock)
                .sold_count(sold)
                .build();
    }

    public static Electronics electronics_stereo_item(String id, String manufacturer, String model, String series, int stock, int sold) {
        return Electronics.builder()
                .id(id)
                .product_type(ProductEnum.STEREO)
                .warehouse_address("Irvine")
                .manufacturer(manufacturer)
                .model_name(model)
                .series(series)
                .year(2022)
                .height(5.0)
                .width(17.0)
                .depth(12.0)
                .weight(14.0)
                .product_color(ColorEnum.BLACK)
                .output_wattage(200.0)
                .channels(2.0)
                .audio_zones(1.0)
                .minimum_impedance("4 ohms")
                .wifi_capable(true)
                .bluetooth_enabled(true)
                .stock_count(stock)
                .sold_count(sold)
                .build();
    }

    public static Order television_order_item(String id, int quantity, double cost) {
        return Order.builder()
                .id(id)
                .customer_id("CUS-1")
                .salesperson_id("SAL-1")
                .product_id("TLV-123")
                .product_type(ProductEnum.TELEVISION)
                .timestamp(1648722131)
                .quantity(quantity)
                .cost(cost)
                .build();
    }

    public static Order stereo_order_item(String id, int quantity, double cost) {
        return Order.builder()
                .id(id)
                .customer_id("CUS-1")
                .salesperson_id("SAL-1")
                .product_id("STR-234")
                .product_type(ProductEnum.STEREO)
                .timestamp(1648722131)
                .quantity(quantity)
                .cost(cost)
                .build();
    }

    public static Person salesperson_person_item(String id) {
        return SalesPerson.builder()
                .id(id)
                .person_type(PersonEnum.SALESPERSON)
                .first_name("Sally")
                .last_name("Iris")
                .phone_number("(562) 986-4333")
                .address("1450 Bellflower Blvd:Long Beach:CA:90840")
                .email("sally_iris@csulb.edu")
                .start(1647721605)
                .build();
    }

    public static Person customer_person_item(String id) {
        return Customer.builder()
                .id(id)
                .person_type(PersonEnum.CUSTOMER)
                .first_name("bob")
                .last_name("ford")
                .address("1250 Bellmotor Blvd:Long Beach:CA:90840")
                .phone_number("(562) 985-4222")
                .email("bob_ford@csulb.edu")
                .start(1647721600)
                .build();
    }

    public static Person supplier_person_item(String id) {
        return Supplier.builder()
                .id(id)
                .person_type(PersonEnum.SALESPERSON)
                .first_name("ricky")
                .last_name("rock")
                .address("1650 Bellstone Blvd:Long Beach:CA:90840")
                .phone_number("(562) 985-4444")
                .email("ricky_rock@csulb.edu")
                .start(1647721600)
                .build();
    }
}