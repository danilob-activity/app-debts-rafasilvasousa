package com.example.danilo.appdebts.classes;

import java.util.Date;

/**
 * Created by Rafael Sousa on 27/06/19.
 */

public class Debts {
    private long mId;
    private Category mCategory;
    private Double mValor;
    private String mDescription;
    private Date mExpireDate;
    private Date mPaymentDate;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        this.mCategory = category;
    }

    public Double getValor() {
        return mValor;
    }

    public void setValor(Double valor) {
        this.mValor = valor;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getExpire_date() {
        return mExpireDate;
    }

    public void setExpire_date(Date expire_date) {
        this.mExpireDate = expire_date;
    }

    public Date getPayment_date() {
        return mPaymentDate;
    }

    public void setPayment_date(Date payment_date) {
        this.mPaymentDate = payment_date;
    }
}