package com.csulb.ase.assignment3.usecases;

import com.csulb.ase.assignment3.components.InvoiceManager;
import com.csulb.ase.assignment3.models.Invoice;
import com.csulb.ase.assignment3.models.Order;
import com.csulb.ase.assignment3.utils.LoadUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class TestInvoiceManager {
    private static final String ORDERS_PATH = "tests/com/csulb/ase/assignment3/data/orders.json";
    private InvoiceManager invoiceManager;

    @BeforeMethod
    public void setup() throws IOException {
        invoiceManager = LoadUtils.getInvoiceFromJson(ORDERS_PATH);
    }

    @Test(dataProvider = "read-orders")
    public void test_Find_Invoice(String str) {
        Order exact = LoadUtils.getOrderFromJson(str);
        Order actual = invoiceManager.readOrder(Objects.requireNonNull(exact).getInvoice_id(), exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "add-orders")
    public void test_Create_Invoice(String str) {
        Order exact = LoadUtils.getOrderFromJson(str);
        int transaction_status = invoiceManager.createInvoice(exact);
        assert transaction_status == 0;
        Order actual = invoiceManager.readOrder(Objects.requireNonNull(exact).getInvoice_id(), exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "update-orders")
    public void test_Update_Invoice(String str) {
        Order exact = LoadUtils.getOrderFromJson(str);
        int transaction_status = invoiceManager.updateOrder(exact);
        assert transaction_status == 0;
        Order actual = invoiceManager.readOrder(Objects.requireNonNull(exact).getInvoice_id(), exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "delete-orders")
    public void test_Delete_Order(String str) {
        Order exact = LoadUtils.getOrderFromJson(str);
        int transaction_status = invoiceManager.deleteOrder(exact);
        assert transaction_status == 0;
        Order actual = invoiceManager.readOrder(Objects.requireNonNull(exact).getInvoice_id(), exact.getId());
        assert actual == null;
    }

    @Test(dataProvider = "delete-invoices")
    public void test_Delete_Invoice(String invoice_id) {
        int transaction_status = invoiceManager.deleteInvoice(invoice_id);
        assert transaction_status == 0;
        Invoice actual = invoiceManager.readInvoice(invoice_id);
        assert actual == null;
    }

    @DataProvider(name="read-orders")
    public static Object[][] getSavedProducts() {
        return new Object[][] {
                {"{\"id\":\"ORD-1\",\"invoice_id\":\"INV-1\",\"product_id\":\"TLV-123\",\"product_type\":\"TELEVISION\",\"timestamp\":1648722131,\"quantity\":1,\"cost\":299.99}\n"}
        };
    }

    @DataProvider(name="add-orders")
    public static Object[][] getAddedOrders() {
        return new Object[][] {
                {"{\"id\":\"ORD-4\",\"invoice_id\":\"INV-1\",\"product_id\":\"STR-234\",\"product_type\":\"STEREO\",\"timestamp\":1648722131,\"quantity\":2,\"cost\":199.99}"}
        };
    }

    @DataProvider(name="update-orders")
    public static Object[][] getUpdatedOrders() {
        return new Object[][] {
                {"{\"id\":\"ORD-1\",\"invoice_id\":\"INV-1\",\"product_id\":\"TLV-123\",\"product_type\":\"TELEVISION\",\"timestamp\":1648722131,\"quantity\":3,\"cost\":299.99}"}
        };
    }

    @DataProvider(name="delete-orders")
    public static Object[][] getDeleteOrders() {
        return new Object[][] {
                {"{\"id\":\"ORD-1\",\"invoice_id\":\"INV-1\",\"product_id\":\"TLV-123\",\"product_type\":\"TELEVISION\",\"timestamp\":1648722131,\"quantity\":1,\"cost\":299.99}\n"}
        };
    }

    @DataProvider(name="delete-invoices")
    public static Object[][] getDeleteInvoices() {
        return new Object[][] {
                {"INV-1"}
        };
    }
}
