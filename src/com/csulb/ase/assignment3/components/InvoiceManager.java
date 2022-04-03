package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.Invoice;
import com.csulb.ase.assignment3.models.Order;

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

    public void createInvoice(Order order) {
        Invoice invoice = invoices.get(order.getInvoice_id());
        if (invoice == null) {
            invoice = Invoice.builder()
                    .timestamp(order.getTimestamp())
                    .orders(new HashMap<>())
                    .build();
            this.invoices.put(invoice.getId(), invoice);
        }
        this.total_invoices += 1;
        invoice.setTotal_cost(invoice.getTotal_cost() + order.getCost());
        invoice.getOrders().put(order.getId(), order);
    }

    public Invoice readInvoice(String invoice_id) {
        return this.invoices.get(invoice_id);
    }

    public Order readOrder(String invoice_id, String order_id) {
        return readInvoice(invoice_id).getOrders().get(order_id);
    }

    public int updateOrder(Order order) {
        Invoice invoice = invoices.get(order.getInvoice_id());
        invoice.getOrders().put(order.getId(), order);
        return 0;
    }

    private int deleteInvoice(String invoice_id) {
        Invoice invoice = this.invoices.get(invoice_id);
        if (invoice == null) {
            return -1;
        }
        this.invoices.remove(invoice_id);
        return 0;
    }

    public int deleteOrder(Order order) {
        Invoice invoice = this.invoices.get(order.getInvoice_id());
        if (invoice == null) {
            return -1;
        }
        this.invoices.get(order.getInvoice_id()).getOrders().remove(order.getId());
        this.total_orders -=1;
        if (invoice.getOrders().size() == 0) {
            deleteInvoice(order.getInvoice_id());
            this.total_invoices -= 1;
        }
        return 0;
    }
}