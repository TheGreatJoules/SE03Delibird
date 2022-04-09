package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ComponentEnum;
import com.csulb.ase.assignment3.models.invoices.DeliveryEnum;
import com.csulb.ase.assignment3.models.invoices.Invoice;
import com.csulb.ase.assignment3.models.invoices.InvoiceStatusEnum;
import com.csulb.ase.assignment3.models.invoices.Order;
import com.csulb.ase.assignment3.models.invoices.PaymentEnum;
import com.csulb.ase.assignment3.models.StateTaxRateEnum;
import com.csulb.ase.assignment3.utils.ExpenseUtil;
import com.csulb.ase.assignment3.utils.IdentifierUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Invoice Manager manages the invoices and any transaction relating to orders
 */
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
     * Upload new order to be added to its corresponding invoice if it exists
     * @param order the order to be added
     * @return transaction status
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
        invoice.setDiscounts(ExpenseUtil.calculateDiscounts(deliveryEnum));
        invoice.setTotal_adjusted_cost(ExpenseUtil.calculateAdjustedTotal(invoice.getTax_rate(), invoice.getTotal_cost(), invoice.getDiscounts(), invoice.getPaymentEnum(), invoice.getStatus(), invoice.getStatus(), invoice.getTimestamp(), -1));
        invoice.getOrders().put(order.getId(), order);
        this.total_invoices += 1;
        return 0;
    }

    /**
     * Return the invoice based on the provided unique identifier
     * @param invoice_id
     * @return
     */
    public Invoice readInvoice(String invoice_id) {
        return this.invoices.get(invoice_id);
    }

    /**
     * Return the order based on the provided unique identifier
     * @param order_id the hashed id correlated with the order
     * @return the order if found
     */
    public Order readOrder(String order_id) {
        String[] ids = IdentifierUtil.parseId(order_id);
        if (readInvoice(ids[0]) == null || readInvoice(ids[0]).getOrders() == null) {
            return null;
        }
        return readInvoice(ids[0]).getOrders().get(order_id);
    }

    /**
     * Replace the current order with the latest order
     * @param order the product to be replaced
     * @return transaction status
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
     * @param invoice_id the hashed id correlated with the invoice
     * @return transaction status
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
     * Delete the existing order from their directories.
     * If no orders exist with the respected invoice id delete the invoice entry
     * @param order_id the hashed id correlated with the order
     * @return transaction status
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

    /**
     * Create an Invoice based on the provided inputs
     * @param ids
     * @param location
     * @param delivery
     * @param paymentEnum
     * @param timestamp
     * @return a populated Invoice
     */
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
                .status(paymentEnum == PaymentEnum.FINANCE ? InvoiceStatusEnum.OPEN : InvoiceStatusEnum.CLOSE)
                .timestamp(timestamp)
                .discounts(0.0)
                .total_adjusted_cost(0.0)
                .total_cost(0.0)
                .orders(new HashMap<>())
                .build();
    }

    /**
     * Update the provided invoice by providing the invoice data to be overwritten with
     * @param invoice
     * @param location
     * @param deliveryEnum
     * @param paymentEnum
     * @param status
     * @param timestamp
     * @return transaction status
     */
    public int updateInvoice(Invoice invoice, String[] location, DeliveryEnum deliveryEnum, PaymentEnum paymentEnum, InvoiceStatusEnum status, long timestamp) {
        invoice.setStreet(location[0]);
        invoice.setCity(location[1]);
        invoice.setState(location[2]);
        invoice.setZipcode(location[3]);
        invoice.setDeliveryEnum(deliveryEnum);
        invoice.setPaymentEnum(paymentEnum);
        invoice.setTax_rate(StateTaxRateEnum.valueOf(invoice.getState()).tax_rate);
        invoice.setLast_timestamp(timestamp);
        invoice.setDiscounts(ExpenseUtil.calculateDiscounts(deliveryEnum));
        invoice.setTotal_adjusted_cost(ExpenseUtil.calculateAdjustedTotal(invoice.getTax_rate(), invoice.getTotal_cost(), invoice.getDiscounts(), invoice.getPaymentEnum(), invoice.getStatus(), status, invoice.getTimestamp(), timestamp));
        invoice.setStatus(status);
        return 0;
    }

}