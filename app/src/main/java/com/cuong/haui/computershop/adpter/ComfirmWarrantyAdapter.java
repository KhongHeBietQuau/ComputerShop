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
import com.cuong.haui.computershop.model.Maintenance;
import com.cuong.haui.computershop.model.SaleOrder;

import java.text.DecimalFormat;
import java.util.List;

public class ComfirmWarrantyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Maintenance> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public ComfirmWarrantyAdapter(Context context, List<Maintenance> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_warranty,parent,false);
            return new ComfirmWarrantyAdapter.MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new ComfirmWarrantyAdapter.LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ComfirmWarrantyAdapter.MyViewHolder) {
            ComfirmWarrantyAdapter.MyViewHolder myViewHolder = (ComfirmWarrantyAdapter.MyViewHolder) holder;
            Maintenance maintenance = array.get(position);
            myViewHolder.itemdt_ten_warranty.setText(maintenance.product_name);
            myViewHolder.itemdt_book_time_warranty.setText(maintenance.time_book_maintenance);
            myViewHolder.itemdt_store_warranty.setText(maintenance.store_maintenance);
            myViewHolder.itemdt_person_warranty.setText(maintenance.user_name_maintenance);
            myViewHolder.itemdt_problem_warranty.setText(maintenance.problem);


            Glide.with(context).load(maintenance.getThumbnail_url()).into(myViewHolder.hinhanh);
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
            ComfirmWarrantyAdapter.LoadingViewHolder loadingViewHolder = (ComfirmWarrantyAdapter.LoadingViewHolder) holder;
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
        TextView itemdt_ten_warranty,itemdt_book_time_warranty,itemdt_store_warranty,itemdt_person_warranty,itemdt_problem_warranty;
        ImageView hinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            hinhanh= itemView.findViewById(R.id.itemdt_image_warranty);
            itemdt_ten_warranty = itemView.findViewById(R.id.itemdt_ten_warranty);
            itemdt_book_time_warranty = itemView.findViewById(R.id.itemdt_book_time_warranty);
            itemdt_store_warranty = itemView.findViewById(R.id.itemdt_store_warranty);
            itemdt_person_warranty = itemView.findViewById(R.id.itemdt_person_warranty);
            itemdt_problem_warranty = itemView.findViewById(R.id.itemdt_problem_warranty);
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
