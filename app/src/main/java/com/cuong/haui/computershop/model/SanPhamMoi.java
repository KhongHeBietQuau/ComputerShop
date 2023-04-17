package com.cuong.haui.computershop.model;

import java.io.Serializable;

public class SanPhamMoi implements Serializable {
    int product_id;
    String product_name;
    int price_new;
    int price_old;
    String thumbnail_url;
    String description;
    String cpu;
    String ram;
    String hard_drive;
    String graphics;
    String screen;
    int precent_discount;
    String created_at;
    String update_at;
    int deleted;
    int current_quantity;
    int warranty_period;
    int category_id;




//(val product_id: Int,val product_name:String?="",val price_new: Int,val price_old: Int,val thumbnail_url:String?=""
//               ,val description:String?="",val cpu: String?="",val ram: String?="",val hard_drive: String?="",val graphics:String?="",
//               val screen:String?="",val precent_discount: Int,val created_at:String?="",val update_at:String?="",val deleted: Int,
//               val current_quantity: Int,val warranty_period: Int,val category_id: Int)


    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getPrice_new() {
        return price_new;
    }

    public void setPrice_new(int price_new) {
        this.price_new = price_new;
    }

    public int getPrice_old() {
        return price_old;
    }

    public void setPrice_old(int price_old) {
        this.price_old = price_old;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHard_drive() {
        return hard_drive;
    }

    public void setHard_drive(String hard_drive) {
        this.hard_drive = hard_drive;
    }

    public String getGraphics() {
        return graphics;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public int getPrecent_discount() {
        return precent_discount;
    }

    public void setPrecent_discount(int precent_discount) {
        this.precent_discount = precent_discount;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public int getCurrent_quantity() {
        return current_quantity;
    }

    public void setCurrent_quantity(int current_quantity) {
        this.current_quantity = current_quantity;
    }

    public int getWarranty_period() {
        return warranty_period;
    }

    public void setWarranty_period(int warranty_period) {
        this.warranty_period = warranty_period;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public SanPhamMoi() {
    }

    public SanPhamMoi(int product_id, String product_name, int price_new, int price_old, String thumbnail_url, String description, String cpu, String ram, String hard_drive, String graphics, String screen, int precent_discount, String created_at, String update_at, int deleted, int current_quantity, int warranty_period, int category_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.price_new = price_new;
        this.price_old = price_old;
        this.thumbnail_url = thumbnail_url;
        this.description = description;
        this.cpu = cpu;
        this.ram = ram;
        this.hard_drive = hard_drive;
        this.graphics = graphics;
        this.screen = screen;
        this.precent_discount = precent_discount;
        this.created_at = created_at;
        this.update_at = update_at;
        this.deleted = deleted;
        this.current_quantity = current_quantity;
        this.warranty_period = warranty_period;
        this.category_id = category_id;
    }
}
