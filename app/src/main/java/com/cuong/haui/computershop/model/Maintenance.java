package com.cuong.haui.computershop.model;

import java.io.Serializable;

public class Maintenance implements Serializable {
    public int maintenance_id;
    public int user_id_maintenance;
    public int admin_id_maintenance;
    public int sale_order_id;
    public int product_id;
    public String creat_at;
    public String update_at;
    public String problem;
    public String thumbnail_url;
    public String store_maintenance;
    public String time_book_maintenance;
    public String status_maintenance;

    public int getMaintenance_id() {
        return maintenance_id;
    }

    public void setMaintenance_id(int maintenance_id) {
        this.maintenance_id = maintenance_id;
    }

    public int getUser_id_maintenance() {
        return user_id_maintenance;
    }

    public void setUser_id_maintenance(int user_id_maintenance) {
        this.user_id_maintenance = user_id_maintenance;
    }

    public int getAdmin_id_maintenance() {
        return admin_id_maintenance;
    }

    public void setAdmin_id_maintenance(int admin_id_maintenance) {
        this.admin_id_maintenance = admin_id_maintenance;
    }

    public int getSale_order_id() {
        return sale_order_id;
    }

    public void setSale_order_id(int sale_order_id) {
        this.sale_order_id = sale_order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getCreat_at() {
        return creat_at;
    }

    public void setCreat_at(String creat_at) {
        this.creat_at = creat_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getStore_maintenance() {
        return store_maintenance;
    }

    public void setStore_maintenance(String store_maintenance) {
        this.store_maintenance = store_maintenance;
    }

    public String getTime_book_maintenance() {
        return time_book_maintenance;
    }

    public void setTime_book_maintenance(String time_book_maintenance) {
        this.time_book_maintenance = time_book_maintenance;
    }

    public String getStatus_maintenance() {
        return status_maintenance;
    }

    public void setStatus_maintenance(String status_maintenance) {
        this.status_maintenance = status_maintenance;
    }

    public Maintenance() {
    }

    public Maintenance(int maintenance_id, int user_id_maintenance, int admin_id_maintenance, int sale_order_id, int product_id, String creat_at, String update_at, String problem, String thumbnail_url, String store_maintenance, String time_book_maintenance, String status_maintenance) {
        this.maintenance_id = maintenance_id;
        this.user_id_maintenance = user_id_maintenance;
        this.admin_id_maintenance = admin_id_maintenance;
        this.sale_order_id = sale_order_id;
        this.product_id = product_id;
        this.creat_at = creat_at;
        this.update_at = update_at;
        this.problem = problem;
        this.thumbnail_url = thumbnail_url;
        this.store_maintenance = store_maintenance;
        this.time_book_maintenance = time_book_maintenance;
        this.status_maintenance = status_maintenance;
    }

    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenance_id=" + maintenance_id +
                ", user_id_maintenance=" + user_id_maintenance +
                ", admin_id_maintenance=" + admin_id_maintenance +
                ", sale_order_id=" + sale_order_id +
                ", product_id=" + product_id +
                ", creat_at='" + creat_at + '\'' +
                ", update_at='" + update_at + '\'' +
                ", problem='" + problem + '\'' +
                ", thumbnail_url='" + thumbnail_url + '\'' +
                ", store_maintenance='" + store_maintenance + '\'' +
                ", time_book_maintenance='" + time_book_maintenance + '\'' +
                ", status_maintenance='" + status_maintenance + '\'' +
                '}';
    }
}
