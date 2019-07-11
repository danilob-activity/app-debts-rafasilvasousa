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
    private String mExpireDate;
    private String mPaymentDate;

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

    public String getExpire_date() {
        return mExpireDate;
    }

    public void setExpire_date(String expire_date) {
        this.mExpireDate = expire_date;
    }

    public String getPayment_date() {
        return mPaymentDate;
    }

    public void setPayment_date(String payment_date) {
        this.mPaymentDate = payment_date;
    }

    public Debts(long id, Category category, Double valor, String description, String expireDate, String paymentDate) {
        mId = id;
        mCategory = category;
        mValor = valor;
        mDescription = description;
        mExpireDate = expireDate;
        mPaymentDate = paymentDate;
    }

    public Debts(){

    }
}
