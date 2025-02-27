/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.util.Date;

/**
 *
 * @author nhutt
 */

public class Invoice{
    private String invoiceID;
    private Date invoiceDate;
    private Customer customer;
    private SalesPerson salesPerson;
    private Car car;
    
    public Invoice() {
    }

    public Invoice(String invoiceID, Date invoiceDate, Customer customer, SalesPerson salesPerson, Car car) {
        this.invoiceID = invoiceID;
        this.invoiceDate = invoiceDate;
        this.customer = customer;
        this.salesPerson = salesPerson;
        this.car = car;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SalesPerson getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    
    
}