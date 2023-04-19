package com.cuong.haui.computershop.model;

import java.io.Serializable;

public class SaleOrder implements Serializable {
    public int sale_order_id;
    public int idsp;
    public String tensp;
    public long giasp;
    public String hinhsp;
    public int soluong;
    //1 : cho xac nhan 2 : dang van chuyen 3: da giao 4: tra hang
    public int status;
    public int admin_id;
    public int user_id;
    public int shipper_id;
    // 1 : chua tra 2: da tra
    public int status_pay;
    public int warranty_period;
    public String note;
    // 1 : tra truc tiep 2: tra oline
    public String payment_method;
    public String receiver;
    public String create_at;
    public String update_at;
    public String delivery_address;
    public String phone_number;

    public int getSale_order_id() {
        return sale_order_id;
    }

    public void setSale_order_id(int sale_order_id) {
        this.sale_order_id = sale_order_id;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public long getGiasp() {
        return giasp;
    }

    public void setGiasp(long giasp) {
        this.giasp = giasp;
    }

    public String getHinhsp() {
        return hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        this.hinhsp = hinhsp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getShipper_id() {
        return shipper_id;
    }

    public void setShipper_id(int shipper_id) {
        this.shipper_id = shipper_id;
    }

    public int getStatus_pay() {
        return status_pay;
    }

    public void setStatus_pay(int status_pay) {
        this.status_pay = status_pay;
    }

    public int getWarranty_period() {
        return warranty_period;
    }

    public void setWarranty_period(int warranty_period) {
        this.warranty_period = warranty_period;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public SaleOrder() {
    }

    public SaleOrder(int sale_order_id, int idsp, String tensp, long giasp, String hinhsp, int soluong, int status, int admin_id, int user_id, int shipper_id, int status_pay, int warranty_period, String note, String payment_method, String receiver, String create_at, String update_at, String delivery_address, String phone_number) {
        this.sale_order_id = sale_order_id;
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinhsp = hinhsp;
        this.soluong = soluong;
        this.status = status;
        this.admin_id = admin_id;
        this.user_id = user_id;
        this.shipper_id = shipper_id;
        this.status_pay = status_pay;
        this.warranty_period = warranty_period;
        this.note = note;
        this.payment_method = payment_method;
        this.receiver = receiver;
        this.create_at = create_at;
        this.update_at = update_at;
        this.delivery_address = delivery_address;
        this.phone_number = phone_number;
    }

}
