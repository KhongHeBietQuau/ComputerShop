package com.cuong.haui.computershop.adpter;

import android.content.Context;
import android.content.Intent;
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
import com.cuong.haui.computershop.ui.bookWarranty.BookWarrantyActivity;
import com.cuong.haui.computershop.ui.detail.DetailActivity;

import java.text.DecimalFormat;
import java.util.List;

public class ProductWarrantyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SaleOrder> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public ProductWarrantyAdapter(Context context, List<SaleOrder> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_order,parent,false);
            return new ProductWarrantyAdapter.MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new ProductWarrantyAdapter.LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ProductWarrantyAdapter.MyViewHolder) {
            ProductWarrantyAdapter.MyViewHolder myViewHolder = (ProductWarrantyAdapter.MyViewHolder) holder;
            SaleOrder saleOrder = array.get(position);
            myViewHolder.tensp.setText("Tên:      " +saleOrder.getTensp());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.giasp.setText("Giá:      " + decimalFormat.format(saleOrder.getGiasp()) + "VNĐ");
            myViewHolder.mota.setText("Số lương: " +String.valueOf(saleOrder.getSoluong()));
            Glide.with(context).load(saleOrder.getHinhsp()).into(myViewHolder.hinhanh);
            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if(!isLongClick){
                        // phai sua lai cho nay
                        Intent intent = new Intent(context, BookWarrantyActivity.class);
                        intent.putExtra("saleOrderWarranty",saleOrder);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
        else {
            ProductWarrantyAdapter.LoadingViewHolder loadingViewHolder = (ProductWarrantyAdapter.LoadingViewHolder) holder;
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
        TextView tensp,giasp,mota;
        ImageView hinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemdt_ten);
            giasp = itemView.findViewById(R.id.itemdt_gia);
            mota = itemView.findViewById(R.id.itemdt_soluong);
            hinhanh= itemView.findViewById(R.id.itemdt_image);
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

