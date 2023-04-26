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
import com.cuong.haui.computershop.model.Maintenance;
import com.cuong.haui.computershop.model.SanPhamMoi;
import com.cuong.haui.computershop.utils.DefaultFirst1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComfirmWarrantyAdminAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Maintenance> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public ComfirmWarrantyAdminAdapter(Context context, List<Maintenance> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_warranty_admin,parent,false);
            return new ComfirmWarrantyAdminAdapter.MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new ComfirmWarrantyAdminAdapter.LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ComfirmWarrantyAdminAdapter.MyViewHolder) {
            ComfirmWarrantyAdminAdapter.MyViewHolder myViewHolder = (ComfirmWarrantyAdminAdapter.MyViewHolder) holder;
            Maintenance maintenance = array.get(position);
            myViewHolder.itemdt_ten_warranty.setText(maintenance.product_name);
            myViewHolder.itemdt_book_time_warranty.setText(maintenance.time_book_maintenance);
            myViewHolder.itemdt_store_warranty.setText(maintenance.store_maintenance);
            myViewHolder.itemdt_person_warranty.setText(maintenance.user_name_maintenance);
            myViewHolder.itemdt_problem_warranty.setText(maintenance.problem);
            myViewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc muốn hủy đơn bảo hành không?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("BookWarrantys/"+String.valueOf(maintenance.maintenance_id)+ "/status_maintenance");
                            myRef.setValue("0", new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Toast.makeText(view.getContext(), "Hủy thành công", Toast.LENGTH_LONG).show();
                                }
                            });
                            DatabaseReference myRefAdmin = database.getReference("BookWarrantys/"+String.valueOf(maintenance.maintenance_id)+ "/admin_id_maintenance");
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
                    builder.setMessage("Bạn có chắc muốn xác nhận bảo hành không?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(maintenance.status_maintenance.equals("1")){
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("BookWarrantys/"+String.valueOf(maintenance.maintenance_id)+ "/status_maintenance");
                                myRef.setValue("2", new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(view.getContext(), "Xác nhận chấp nhận bảo hành thành công", Toast.LENGTH_LONG).show();
                                    }
                                });
                                DatabaseReference myRefAdmin = database.getReference("BookWarrantys/"+String.valueOf(maintenance.maintenance_id)+ "/admin_id_maintenance");
                                myRefAdmin.setValue(DefaultFirst1.userCurrent.user_id, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                    }
                                });
                            }
                            else if(maintenance.status_maintenance.equals("2")){
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("BookWarrantys/"+String.valueOf(maintenance.maintenance_id)+ "/status_maintenance");
                                myRef.setValue("3", new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(view.getContext(), "Xác nhận đã bảo hành thành công", Toast.LENGTH_LONG).show();
                                    }
                                });
                                DatabaseReference myRefAdmin = database.getReference("BookWarrantys/"+String.valueOf(maintenance.maintenance_id)+ "/admin_id_maintenance");
                                myRefAdmin.setValue(DefaultFirst1.userCurrent.user_id, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

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
            ComfirmWarrantyAdminAdapter.LoadingViewHolder loadingViewHolder = (ComfirmWarrantyAdminAdapter.LoadingViewHolder) holder;
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
        Button btn_cancel,btn_confirmation;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            hinhanh= itemView.findViewById(R.id.itemdt_image_warranty);
            itemdt_ten_warranty = itemView.findViewById(R.id.itemdt_ten_warranty);
            itemdt_book_time_warranty = itemView.findViewById(R.id.itemdt_book_time_warranty);
            itemdt_store_warranty = itemView.findViewById(R.id.itemdt_store_warranty);
            itemdt_person_warranty = itemView.findViewById(R.id.itemdt_person_warranty);
            itemdt_problem_warranty = itemView.findViewById(R.id.itemdt_problem_warranty);
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

