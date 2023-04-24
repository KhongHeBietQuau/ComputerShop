package com.cuong.haui.computershop.model;

import java.io.Serializable;

public class Store implements Serializable {
    public int store_id;
    public String address;

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Store() {
    }

    public Store(int store_id, String address) {
        this.store_id = store_id;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Store{" +
                "store_id=" + store_id +
                ", address='" + address + '\'' +
                '}';
    }
}
