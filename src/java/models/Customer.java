/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author nhutt
 */
public class Customer implements Serializable{

    private String custID;
    private String custName;
    private String phone;
    private String sex;
    private String custAddress;

    public Customer() {
    }

    public Customer(String custID, String custName, String phone, String sex, String custAddress) {
        this.custID = custID;
        this.custName = custName;
        this.phone = phone;
        this.sex = sex;
        this.custAddress = custAddress;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }
    
    
}
