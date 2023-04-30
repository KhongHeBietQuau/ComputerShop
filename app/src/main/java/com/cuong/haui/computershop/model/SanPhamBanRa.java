package com.cuong.haui.computershop.model;

public class SanPhamBanRa {
    public int product_id;
    public int solongbanra;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getSolongbanra() {
        return solongbanra;
    }

    public void setSolongbanra(int solongbanra) {
        this.solongbanra = solongbanra;
    }

    public SanPhamBanRa() {
    }

    public SanPhamBanRa(int product_id, int solongbanra) {
        this.product_id = product_id;
        this.solongbanra = solongbanra;
    }
}
