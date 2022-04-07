package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ComponentEnum;
import com.csulb.ase.assignment3.models.invoices.DeliveryEnum;
import com.csulb.ase.assignment3.models.invoices.Invoice;
import com.csulb.ase.assignment3.models.invoices.Order;
import com.csulb.ase.assignment3.models.invoices.PaymentEnum;
import com.csulb.ase.assignment3.models.StateTaxRateEnum;
import com.csulb.ase.assignment3.utils.ExpenseUtil;
import com.csulb.ase.assignment3.utils.IdentifierUtil;

import java.util.HashMap;
import java.util.Map;

public class InvoiceManager{
    private Map<String, Invoice> invoices;
    private int total_orders;
    private int total_invoices;

    public InvoiceManager() {
        this.invoices = new HashMap<>();
        this.total_orders = 0;
        this.total_invoices = 0;
    }

    public InvoiceManager(Map<String, Invoice> invoices) {
        this.invoices = invoices;
        this.total_invoices = invoices.size();
        this.total_orders = 0;
        for (Map.Entry<String, Invoice> entry : invoices.entrySet()) {
            this.total_orders += entry.getValue().getOrders().size();
        }
    }

    /**
     * Add a constructed order to its collection
     * @param order
     * @return status code
     */
    public int createInvoice(Order order, String address, DeliveryEnum deliveryEnum, PaymentEnum paymentEnum) {
        if (order == null) {
            return -1;
        }
        String[] ids = IdentifierUtil.parseId(order.getId());
        String[] location = IdentifierUtil.parseId(address);
        if (!invoices.containsKey(ids[0])) {
            Invoice invoice = createInvoice(ids, location, deliveryEnum, paymentEnum, order.getTimestamp());
            this.invoices.put(invoice.getId(), invoice);
        }
        Invoice invoice = invoices.get(ids[0]);
        invoice.setTotal_cost(invoice.getTotal_cost() + order.getCost());
        invoice.setTax_rate(StateTaxRateEnum.valueOf(invoice.getState()).tax_rate);
        invoice.setDiscounts(ExpenseUtil.calculateDiscounts(deliveryEnum.toString(), paymentEnum.toString()));
        invoice.setTotal_adjusted_cost(ExpenseUtil.calculateAdjustedTotal(invoice.getTax_rate(), invoice.getTotal_cost(), invoice.getDiscounts()));
        invoice.getOrders().put(order.getId(), order);
        this.total_invoices += 1;
        return 0;
    }

    public Invoice createInvoice(String[] ids, String[] location, DeliveryEnum delivery, PaymentEnum paymentEnum, long timestamp) {
        return Invoice.builder()
                .id(ids[0] != null ? ids[0] : IdentifierUtil.generateEntityId(ComponentEnum.INVOICE))
                .customer_id(ids[1])
                .street(location[0])
                .city(location[1])
                .state(location[2])
                .zipcode(location[3])
                .deliveryEnum(delivery)
                .paymentEnum(paymentEnum)
                .timestamp(timestamp)
                .discounts(0.0)
                .total_adjusted_cost(0.0)
                .total_cost(0.0)
                .orders(new HashMap<>())
                .build();
    }

    public void updateInvoice(Invoice invoice, String[] location, DeliveryEnum deliveryEnum, PaymentEnum paymentEnum) {
        invoice.setStreet(location[0]);
        invoice.setCity(location[1]);
        invoice.setState(location[2]);
        invoice.setZipcode(location[3]);
        invoice.setDeliveryEnum(deliveryEnum);
        invoice.setPaymentEnum(paymentEnum);
        invoice.setDiscounts(ExpenseUtil.calculateDiscounts(deliveryEnum.toString(), paymentEnum.toString()));
        invoice.setTax_rate(StateTaxRateEnum.valueOf(invoice.getState()).tax_rate);
        invoice.setDiscounts(ExpenseUtil.calculateDiscounts(deliveryEnum.toString(), paymentEnum.toString()));
        invoice.setTotal_adjusted_cost(ExpenseUtil.calculateAdjustedTotal(invoice.getTax_rate(), invoice.getTotal_cost(), invoice.getDiscounts()));
    }

    /**
     * Find the invoice by its unique identifier
     * @param invoice_id
     * @return
     */
    public Invoice readInvoice(String invoice_id) {
        return this.invoices.get(invoice_id);
    }

    /**
     * Find the order based on its unique permutation of invoice and order id
     * @param order_id
     * @return Order
     */
    public Order readOrder(String order_id) {
        String[] ids = IdentifierUtil.parseId(order_id);
        if (readInvoice(ids[0]) == null || readInvoice(ids[0]).getOrders() == null) {
            return null;
        }
        return readInvoice(ids[0]).getOrders().get(order_id);
    }

    /**
     * Replace the current order with the latest order data
     * @param order
     * @return status code
     */
    public int updateOrder(Order order) {
        if (order == null) {
            return -1;
        }
        String[] ids = IdentifierUtil.parseId(order.getId());
        Invoice invoice = invoices.get(ids[0]);
        if (invoice == null) {
            return -1;
        }
        invoice.getOrders().put(order.getId(), order);
        return 0;
    }

    /**
     * Delete the entire invoice entry along with all its orders
     * @param invoice_id
     * @return status code
     */
    public int deleteInvoice(String invoice_id) {
        Invoice invoice = this.invoices.get(invoice_id);
        if (invoice == null) {
            return -1;
        }
        this.invoices.remove(invoice_id);
        return 0;
    }

    /**
     * Delete the loaded order from the collection.
     * If no orders exist with the respected invoice id delete the invoice entry
     * @param order_id
     * @return status code
     */
    public int deleteOrder(String order_id) {
        if (order_id == null) {
            return -1;
        }
        String[] ids = IdentifierUtil.parseId(order_id);
        if (this.invoices.get(ids[0]) == null || this.invoices.get(ids[0]).getOrders() == null) {
            return -1;
        }
        this.invoices.get(ids[0]).getOrders().remove(order_id);

        Invoice invoice = this.invoices.get(ids[0]);
        if (invoice == null) {
            return -2;
        }

        this.total_orders -=1;
        if (invoice.getOrders().size() == 0) {
            deleteInvoice(order_id);
            this.total_invoices -= 1;
        }
        return 0;
    }
}