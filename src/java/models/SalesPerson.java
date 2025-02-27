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
public class SalesPerson {
    private String salesID;
    private String salesName;
    private Date birthday;
    private String sex;
    private String salesAddress;

    public SalesPerson() {
    }

    public SalesPerson(String salesID, String salesName, Date birthday, String sex, String salesAddress) {
        this.salesID = salesID;
        this.salesName = salesName;
        this.birthday = birthday;
        this.sex = sex;
        this.salesAddress = salesAddress;
    }

    public String getSalesID() {
        return salesID;
    }

    public void setSalesID(String salesID) {
        this.salesID = salesID;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSalesAddress() {
        return salesAddress;
    }

    public void setSalesAddress(String salesAddress) {
        this.salesAddress = salesAddress;
    }
    
    
}
