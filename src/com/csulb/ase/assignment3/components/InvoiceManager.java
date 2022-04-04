package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ComponentEnum;
import com.csulb.ase.assignment3.models.Invoice;
import com.csulb.ase.assignment3.models.Order;
import com.csulb.ase.assignment3.utils.GeneratorUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
    public int createInvoice(Order order) {
        if (order == null) {
            return -1;
        }
        String[] ids = order.getId().split(":");
        Invoice invoice = invoices.get(ids[0]);
        if (invoice == null) {
            invoice = Invoice.builder()
                    .id(Objects.requireNonNull(GeneratorUtils.generateEntityId(ComponentEnum.INVOICE)))
                    .timestamp(order.getTimestamp())
                    .orders(new HashMap<>())
                    .build();
            this.invoices.put(invoice.getId(), invoice);
        }
        this.total_invoices += 1;
        invoice.setTotal_cost(invoice.getTotal_cost() + order.getCost());
        invoice.getOrders().put(order.getId(), order);
        return 0;
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
        String[] ids = order_id.split(":");
        if (readInvoice(ids[0]) == null) {
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
        String[] ids = order.getId().split(":");
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
        String[] ids = order_id.split(":");
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