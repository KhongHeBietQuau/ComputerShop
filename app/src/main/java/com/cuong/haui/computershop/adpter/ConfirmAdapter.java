package com.cuong.haui.computershop.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cuong.haui.computershop.Interface.ItemClickListener;
import com.cuong.haui.computershop.R;
import com.cuong.haui.computershop.model.SaleOrder;

import java.text.DecimalFormat;
import java.util.List;

public class ConfirmAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SaleOrder> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public ConfirmAdapter(Context context, List<SaleOrder> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_order,parent,false);
            return new ConfirmAdapter.MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new ConfirmAdapter.LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ConfirmAdapter.MyViewHolder) {
            ConfirmAdapter.MyViewHolder myViewHolder = (ConfirmAdapter.MyViewHolder) holder;
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
            myViewHolder.itemdt_address.setText(saleOrder.delivery_address);
            myViewHolder.phuongthucthanhtoan.setText(status_payment);
            myViewHolder.trangthaithanhtoan.setText(status_pay);
            Glide.with(context).load(saleOrder.getHinhsp()).into(myViewHolder.hinhanh);
            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if(!isLongClick){
                        // phai sua lai cho nay

                    }
                }
            });
        }
        else {
            ConfirmAdapter.LoadingViewHolder loadingViewHolder = (ConfirmAdapter.LoadingViewHolder) holder;
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
        TextView tensp,giasp,mota,nguoinhan,sodienthoai,tongtien,phuongthucthanhtoan,trangthaithanhtoan,itemdt_address;
        ImageView hinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemdt_ten);
            giasp = itemView.findViewById(R.id.itemdt_gia);
            mota = itemView.findViewById(R.id.itemdt_soluong);
            itemdt_address= itemView.findViewById(R.id.itemdt_address);
            hinhanh= itemView.findViewById(R.id.itemdt_image);
            nguoinhan= itemView.findViewById(R.id.itemdt_receiver);
            sodienthoai= itemView.findViewById(R.id.itemdt_phone_number);
            tongtien= itemView.findViewById(R.id.itemdt_total_money);
            phuongthucthanhtoan= itemView.findViewById(R.id.itemdt_payment);
            trangthaithanhtoan= itemView.findViewById(R.id.itemdt_status_payment);
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
