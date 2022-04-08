package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.invoices.DeliveryEnum;
import com.csulb.ase.assignment3.models.invoices.Invoice;
import com.csulb.ase.assignment3.models.invoices.InvoiceStatusEnum;
import com.csulb.ase.assignment3.models.invoices.Order;
import com.csulb.ase.assignment3.models.invoices.PaymentEnum;
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
        invoiceManager = LoadUtils.getInvoiceFromJson(LoadUtils.INVOICES_PATH, LoadUtils.ORDERS_PATH);
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
    public void test_Delete_Invoice_Successful(String invoice_id) {
        int transaction_status = this.invoiceManager.deleteInvoice(invoice_id);
        assert transaction_status == 0;
        Invoice actual = this.invoiceManager.readInvoice(invoice_id);
        assert actual == null;
    }

    @Test(dataProvider = "add-order-with-finance_late")
    public void test_Update_Invoice_Close_Late(Order order, String address, DeliveryEnum delivery, PaymentEnum payment, long timestamp) {
        int transaction_status1 = this.invoiceManager.createInvoice(order, address, delivery, payment);
        assert transaction_status1 == 0;
        Invoice actual = this.invoiceManager.readInvoice(order.getId().split(":")[0]);
        int transaction_status2 = this.invoiceManager.updateInvoice(actual, address.split(":"), delivery, payment, InvoiceStatusEnum.CLOSE, timestamp);
        assert transaction_status2 == 0;
        double exact_cost = actual.getTotal_cost() + actual.getTax_rate() * actual.getTotal_cost() - actual.getDiscounts() * actual.getTotal_cost() + 0.15 * actual.getTotal_cost();
        double actual_cost = actual.getTotal_adjusted_cost();
        assert exact_cost == actual_cost;
    }

    @Test(dataProvider = "add-order-with-finance_ontime")
    public void test_Update_Invoice_Close_OneTime(Order order, String address, DeliveryEnum delivery, PaymentEnum payment, long timestamp) {
        int transaction_status1 = this.invoiceManager.createInvoice(order, address, delivery, payment);
        assert transaction_status1 == 0;
        Invoice actual = this.invoiceManager.readInvoice(order.getId().split(":")[0]);
        int transaction_status2 = this.invoiceManager.updateInvoice(actual, address.split(":"), delivery, payment, InvoiceStatusEnum.CLOSE, timestamp);
        assert transaction_status2 == 0;
        double exact_cost = actual.getTotal_cost() + actual.getTax_rate() * actual.getTotal_cost() - actual.getDiscounts() * actual.getTotal_cost() + 0 * actual.getTotal_cost();
        double actual_cost = actual.getTotal_adjusted_cost();
        assert exact_cost == actual_cost;
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

    @DataProvider(name="add-order-with-finance_late")
    public static Object[][] getAddedOrdersFinanceLate() {
        return new Object[][] {
                {television_order_item("INV-2:CUS-1:ORD-4:TLV-123", 1, 299.99), "1450 Bellflower Blvd:Long Beach:CA:90840", DeliveryEnum.PICKUP, PaymentEnum.FINANCE, 1620440185},
                {stereo_order_item("INV-2:CUS-1:ORD-5:STR-234", 1, 150.99), "1450 Bellflower Blvd:Long Beach:CA:90840", DeliveryEnum.SHIPPING, PaymentEnum.FINANCE, 1620440185}
        };
    }

    @DataProvider(name="add-order-with-finance_ontime")
    public static Object[][] getAddedOrdersFinanceOnTime() {
        return new Object[][] {
                {television_order_item("INV-2:CUS-1:ORD-4:TLV-123", 1, 299.99), "1450 Bellflower Blvd:Long Beach:CA:90840", DeliveryEnum.PICKUP, PaymentEnum.FINANCE, 1617848185},
                {stereo_order_item("INV-2:CUS-1:ORD-5:STR-234", 1, 150.99), "1450 Bellflower Blvd:Long Beach:CA:90840", DeliveryEnum.SHIPPING, PaymentEnum.FINANCE, 1617848185}
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
                .customer_id("CUS-1")
                .salesperson_id("SAL-1")
                .product_id("TLV-123")
                .product_type(ProductEnum.TELEVISION)
                .timestamp(1617848185)
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
                .timestamp(1617848185)
                .quantity(quantity)
                .cost(cost)
                .build();
    }
}
