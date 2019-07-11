package com.example.danilo.appdebts.classes;

/**
 * Created by Rafael Sousa on 27/06/19.
 */

public class Category {
    private long mId;
    private String mType;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public Category(){

    }

    public Category(String tipo){
        mType = tipo;
    }


}
