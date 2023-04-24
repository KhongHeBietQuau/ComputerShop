package com.cuong.haui.computershop.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.cuong.haui.computershop.Interface.ItemClickListener;
import com.cuong.haui.computershop.R;
import com.cuong.haui.computershop.model.Store;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Store> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public StoreAdapter(Context context, List<Store> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_store,parent,false);
            return new StoreAdapter.MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new StoreAdapter.LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof StoreAdapter.MyViewHolder) {
            StoreAdapter.MyViewHolder myViewHolder = (StoreAdapter.MyViewHolder) holder;
            Store store = array.get(position);
            myViewHolder.item_edt_address_store.setText(store.address);

            myViewHolder.btn_delete_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("StoreLists/"+String.valueOf(store.getStore_id()));
                    myRef.removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(view.getContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            myViewHolder.btn_update_store.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("StoreLists/"+String.valueOf(store.getStore_id())+ "/address");
                    myRef.setValue(myViewHolder.item_edt_address_store.getText().toString(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Toast.makeText(view.getContext(), "Sửa thành công", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            /*myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if(!isLongClick){
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("chitiet",sanPham);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });*/
        }
        else {
            StoreAdapter.LoadingViewHolder loadingViewHolder = (StoreAdapter.LoadingViewHolder) holder;
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
    public class MyViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */{
        EditText item_edt_address_store ;
        Button btn_update_store,btn_delete_store;
        //private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_edt_address_store = itemView.findViewById(R.id.item_edt_address_store);
            btn_update_store = itemView.findViewById(R.id.btn_update_store);
            btn_delete_store = itemView.findViewById(R.id.btn_delete_store);
            //itemView.setOnClickListener(this);
        }

        /*public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }*/
    }

}

