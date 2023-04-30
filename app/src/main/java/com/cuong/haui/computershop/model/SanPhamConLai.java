package com.cuong.haui.computershop.model;

public class SanPhamConLai {
    public int product_id;
    public int solongcon;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getSolongcon() {
        return solongcon;
    }

    public void setSolongcon(int solongcon) {
        this.solongcon = solongcon;
    }

    public SanPhamConLai(int product_id, int solongcon) {
        this.product_id = product_id;
        this.solongcon = solongcon;
    }

    public SanPhamConLai() {
    }
}
