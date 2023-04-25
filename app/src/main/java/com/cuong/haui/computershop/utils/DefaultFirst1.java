package com.cuong.haui.computershop.utils;



import com.cuong.haui.computershop.model.GioHang;
import com.cuong.haui.computershop.model.SaleOrder;
import com.cuong.haui.computershop.model.User;

import java.util.ArrayList;
import java.util.List;

public class DefaultFirst1 {
    public static final String BASE_URL = "http://localhost/banhang/";
    public static List<GioHang> manggiohang = new ArrayList<>();
    public static User userCurrent = new User(0,0,"0","0","0","0","0","0","0");
    public static SaleOrder saleOrderCurrent = new SaleOrder();
    public static int status_pay_zalopay = 0;
    public static int saleOrder_current_id = 0;
    public static long total_pay_oline = 0;

}
