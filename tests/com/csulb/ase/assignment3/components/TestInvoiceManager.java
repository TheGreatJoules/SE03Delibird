package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.DeliveryEnum;
import com.csulb.ase.assignment3.models.Invoice;
import com.csulb.ase.assignment3.models.Order;
import com.csulb.ase.assignment3.models.PaymentEnum;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.utils.LoadUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestInvoiceManager {
    private InvoiceManager invoiceManager;

    @BeforeMethod
    public void setup() throws IOException {
        invoiceManager = LoadUtils.getInvoiceFromJson(LoadUtils.ORDERS_PATH);
    }

    @Test(dataProvider = "exist-orders")
    public void test_Find_Invoice(Order order) {
        Order actual = this.invoiceManager.readOrder(order.getId());
        assertThat(actual).isEqualToComparingFieldByField(order);
    }

    @Test(dataProvider = "add-orders")
    public void test_Create_Invoice(Order order) {
        int transaction_status = this.invoiceManager.createInvoice(order, "1250 Bellmotor Blvd:Long Beach:CA:90840",
                DeliveryEnum.PICKUP, PaymentEnum.CASH);
        assert transaction_status == 0;
        Order actual = this.invoiceManager.readOrder(order.getId());
        assertThat(actual).isEqualToComparingFieldByField(order);
    }

    @Test(dataProvider = "update-orders")
    public void test_Update_Invoice(Order order) {
        int transaction_status = this.invoiceManager.updateOrder(order);
        assert transaction_status == 0;
        Order actual = this.invoiceManager.readOrder(order.getId());
        assertThat(actual).isEqualToComparingFieldByField(order);
    }

    @Test(dataProvider = "exist-orders")
    public void test_Delete_Order(Order order) {
        String order_id = order.getId();
        int transaction_status = this.invoiceManager.deleteOrder(order_id);
        assert transaction_status == 0;
        Order actual = this.invoiceManager.readOrder(order_id);
        assert actual == null;
    }

    @Test(dataProvider = "delete-invoices")
    public void test_Delete_Invoice(String invoice_id) {
        int transaction_status = this.invoiceManager.deleteInvoice(invoice_id);
        assert transaction_status == 0;
        Invoice actual = this.invoiceManager.readInvoice(invoice_id);
        assert actual == null;
    }

    @DataProvider(name="exist-orders")
    public static Object[][] getExistProducts() {
        return new Object[][] {
                {television_order_item("INV-1:CUS-1:ORD-1:TLV-123", 1, 299.99)},
                {stereo_order_item("INV-1:CUS-1:ORD-3:STR-234", 2, 199.99)}
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

    @DataProvider(name="delete-invoices")
    public static Object[][] getDeleteInvoices() {
        return new Object[][] {
                {"INV-1"}
        };
    }

    public static Order television_order_item(String id, int quantity, double cost) {
        return Order.builder()
                .id(id)
                .person_id("CUS-1")
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
                .person_id("CUS-1")
                .salesperson_id("SAL-1")
                .product_id("STR-234")
                .product_type(ProductEnum.STEREO)
                .timestamp(1648722131)
                .quantity(quantity)
                .cost(cost)
                .build();
    }
}
