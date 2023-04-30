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
import com.cuong.haui.computershop.model.SaleOrder;
import com.cuong.haui.computershop.utils.DefaultFirst1;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.List;

public class DeliveredAdminAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SaleOrder> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public DeliveredAdminAdapter(Context context, List<SaleOrder> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivered_admin_order,parent,false);
            return new DeliveredAdminAdapter.MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new DeliveredAdminAdapter.LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DeliveredAdminAdapter.MyViewHolder) {
            DeliveredAdminAdapter.MyViewHolder myViewHolder = (DeliveredAdminAdapter.MyViewHolder) holder;
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
            myViewHolder.itemdt_address.setText(saleOrder.delivery_address);
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
        }
        else {
            DeliveredAdminAdapter.LoadingViewHolder loadingViewHolder = (DeliveredAdminAdapter.LoadingViewHolder) holder;
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
        TextView tensp,giasp,nguoinhan,mota,sodienthoai,tongtien,phuongthucthanhtoan,trangthaithanhtoan,ghichu,itemdt_address;
        ImageView hinhanh;

        Button btn_cancel;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemdt_ten);
            giasp = itemView.findViewById(R.id.itemdt_gia);
            mota = itemView.findViewById(R.id.itemdt_soluong);
            hinhanh= itemView.findViewById(R.id.itemdt_image);
            nguoinhan= itemView.findViewById(R.id.itemdt_receiver);
            sodienthoai= itemView.findViewById(R.id.itemdt_phone_number);
            itemdt_address= itemView.findViewById(R.id.itemdt_address);
            tongtien= itemView.findViewById(R.id.itemdt_total_money);
            phuongthucthanhtoan= itemView.findViewById(R.id.itemdt_payment);
            trangthaithanhtoan= itemView.findViewById(R.id.itemdt_status_payment);
            ghichu =itemView.findViewById(R.id.itemdt_note);
            btn_cancel= itemView.findViewById(R.id.btn_cancel);

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


