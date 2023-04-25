package com.cuong.haui.computershop.adpter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cuong.haui.computershop.Interface.ItemClickListener;
import com.cuong.haui.computershop.R;
import com.cuong.haui.computershop.model.EventBus.TinhTongEvent;
import com.cuong.haui.computershop.model.GioHang;
import com.cuong.haui.computershop.model.SaleOrder;
import com.cuong.haui.computershop.model.SanPhamMoi;
import com.cuong.haui.computershop.utils.DefaultFirst1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ComfirmAdminAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SaleOrder> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public ComfirmAdminAdapter(Context context, List<SaleOrder> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_order_admin,parent,false);
            return new ComfirmAdminAdapter.MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new ComfirmAdminAdapter.LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ComfirmAdminAdapter.MyViewHolder) {
            ComfirmAdminAdapter.MyViewHolder myViewHolder = (ComfirmAdminAdapter.MyViewHolder) holder;
            SaleOrder saleOrder = array.get(position);
            myViewHolder.tensp.setText(saleOrder.getTensp());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.giasp.setText(decimalFormat.format(saleOrder.getGiasp()) + "VNĐ");
            myViewHolder.mota.setText(String.valueOf(saleOrder.getSoluong()));
            myViewHolder.nguoinhan.setText(saleOrder.receiver);
            myViewHolder.sodienthoai.setText(saleOrder.phone_number);

            myViewHolder.tongtien.setText(decimalFormat.format(saleOrder.soluong * saleOrder.giasp) + "VNĐ");
            String status_payment = "",status_pay = "";
            if(saleOrder.payment_method.equals("1"))
            {
                status_payment = "thanh toán trực tiếp";
            }
            else
            {
                status_payment = "thanh toán online";
            }
            if(saleOrder.getStatus_pay() == 1){
                status_pay = "chưa thanh toán";
            }
            else{
                status_pay = "đã thanh toán";
            }
            myViewHolder.phuongthucthanhtoan.setText(status_payment);
            myViewHolder.trangthaithanhtoan.setText(status_pay);
            myViewHolder.ghichu.setText(saleOrder.note);
            Glide.with(context).load(saleOrder.getHinhsp()).into(myViewHolder.hinhanh);
            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if(!isLongClick){
                        // phai sua lai cho nay

                    }
                }
            });
            myViewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc muốn hủy đơn hàng không?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("SaleOrders/"+String.valueOf(saleOrder.sale_order_id)+ "/status");
                            myRef.setValue("0", new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Toast.makeText(view.getContext(), "Hủy thành công", Toast.LENGTH_LONG).show();
                                }
                            });
                            DatabaseReference myRefAdmin = database.getReference("SaleOrders/"+String.valueOf(saleOrder.sale_order_id)+ "/admin_id");
                            myRefAdmin.setValue(DefaultFirst1.userCurrent.user_id, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            });
            myViewHolder.btn_confirmation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc muốn xác nhận hàng không?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(saleOrder.status.equals("1")){
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("SaleOrders/"+String.valueOf(saleOrder.sale_order_id)+ "/status");
                                myRef.setValue("2", new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(view.getContext(), "Xác nhận đang giao hàng thành công", Toast.LENGTH_LONG).show();
                                    }
                                });
                                DatabaseReference myRefAdmin = database.getReference("SaleOrders/"+String.valueOf(saleOrder.sale_order_id)+ "/admin_id");
                                myRefAdmin.setValue(DefaultFirst1.userCurrent.user_id, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                    }
                                });
                            }
                            else if(saleOrder.status.equals("2")){
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("SaleOrders/"+String.valueOf(saleOrder.sale_order_id)+ "/status");
                                myRef.setValue("3", new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(view.getContext(), "Xác nhận đã giao hàng thành công", Toast.LENGTH_LONG).show();
                                    }
                                });
                                DatabaseReference myRefAdmin = database.getReference("SaleOrders/"+String.valueOf(saleOrder.sale_order_id)+ "/admin_id");
                                myRefAdmin.setValue(DefaultFirst1.userCurrent.user_id, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                    }
                                });
                                DatabaseReference myRefAdminStatusPay = database.getReference("SaleOrders/"+String.valueOf(saleOrder.sale_order_id)+ "/status_pay");
                                myRefAdminStatusPay.setValue(2, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                    }
                                });
                                DatabaseReference myRefAdminTime = database.getReference("Products");
                                List<SanPhamMoi> mangsp = new ArrayList<>();

                                Query myTopPostsQuery = myRefAdminTime.orderByChild("product_id").equalTo(saleOrder.idsp);
                                // lay thang bao hanh san pham
                                myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                                            SanPhamMoi sanPhamMoi = postSnapshot.getValue(SanPhamMoi.class);
                                            if(sanPhamMoi!= null){
                                                mangsp.add(sanPhamMoi);
                                                //Toast.makeText(view.getContext(), sanPhamMoi.toString(), Toast.LENGTH_LONG).show();
                                                DatabaseReference myRefAdminTimeSet = database.getReference("SaleOrders/"+String.valueOf(saleOrder.sale_order_id)+ "/warranty_period");
                                                myRefAdminTimeSet.setValue(sanPhamMoi.warranty_period, new DatabaseReference.CompletionListener() {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                                    }
                                                });
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Getting Post failed, log a message

                                        // ...
                                    }
                                });

                            }
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            });
        }
        else {
            ComfirmAdminAdapter.LoadingViewHolder loadingViewHolder = (ComfirmAdminAdapter.LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensp,giasp,mota,nguoinhan,sodienthoai,tongtien,phuongthucthanhtoan,trangthaithanhtoan,ghichu;
        ImageView hinhanh;
        Button btn_cancel,btn_confirmation;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemdt_ten);
            giasp = itemView.findViewById(R.id.itemdt_gia);
            mota = itemView.findViewById(R.id.itemdt_soluong);
            hinhanh= itemView.findViewById(R.id.itemdt_image);
            nguoinhan= itemView.findViewById(R.id.itemdt_receiver);
            sodienthoai= itemView.findViewById(R.id.itemdt_phone_number);
            tongtien= itemView.findViewById(R.id.itemdt_total_money);
            phuongthucthanhtoan= itemView.findViewById(R.id.itemdt_payment);
            trangthaithanhtoan= itemView.findViewById(R.id.itemdt_status_payment);
            ghichu =itemView.findViewById(R.id.itemdt_note);
            btn_cancel= itemView.findViewById(R.id.btn_cancel);
            btn_confirmation= itemView.findViewById(R.id.btn_confirmation);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }

}

