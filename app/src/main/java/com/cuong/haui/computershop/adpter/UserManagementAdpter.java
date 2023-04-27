package com.cuong.haui.computershop.adpter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.cuong.haui.computershop.Interface.ItemClickListener;
import com.cuong.haui.computershop.R;
import com.cuong.haui.computershop.model.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserManagementAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<User> array;
    private static final int VIEW_TYPE_DATA = 0;
    private static final int VIEW_TYPE_LOADING = 1;

    public UserManagementAdpter(Context context, List<User> array) {
        this.context = context;
        this.array = array;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_management,parent,false);
            return new UserManagementAdpter.MyViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return new UserManagementAdpter.LoadingViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof UserManagementAdpter.MyViewHolder) {
            UserManagementAdpter.MyViewHolder myViewHolder = (UserManagementAdpter.MyViewHolder) holder;
            User user = array.get(position);
            myViewHolder.itemdt_user_fullname.setText(user.getFullname());

            myViewHolder.itemdt_user_email.setText(String.valueOf(user.getEmail()));
            if(user.role>0){
                myViewHolder.itemdt_user_status.setText("Bình thường");
            }
            else{
                myViewHolder.itemdt_user_status.setText("Đã khóa");
            }
            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if(!isLongClick){
                        // phai sua lai cho nay

                    }
                }
            });
            myViewHolder.btn_user_management_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc muốn muốn mở khóa tài khoản này không?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if(user.role == -1) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users/" + String.valueOf(user.user_id) + "/role");
                                myRef.setValue(1, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(view.getContext(), "Mở khóa thành công", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            if(user.role == -2) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users/" + String.valueOf(user.user_id) + "/role");
                                myRef.setValue(2, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(view.getContext(), "Mở khóa thành công", Toast.LENGTH_LONG).show();
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
            //
            myViewHolder.btn_user_management_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("Thông báo");
                    builder.setMessage("Bạn có chắc muốn muốn khóa tài khoản này không?");
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            if(user.role == 1) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users/" + String.valueOf(user.user_id) + "/role");
                                myRef.setValue(-1, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(view.getContext(), "Khóa thành công", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            if(user.role == 2) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users/" + String.valueOf(user.user_id) + "/role");
                                myRef.setValue(-2, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(view.getContext(), "Mở khóa thành công", Toast.LENGTH_LONG).show();
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
            UserManagementAdpter.LoadingViewHolder loadingViewHolder = (UserManagementAdpter.LoadingViewHolder) holder;
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
        TextView itemdt_user_fullname,itemdt_user_email,itemdt_user_status;
        Button btn_user_management_open,btn_user_management_close;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemdt_user_fullname = itemView.findViewById(R.id.itemdt_user_fullname);
            itemdt_user_email = itemView.findViewById(R.id.itemdt_user_email);
            itemdt_user_status = itemView.findViewById(R.id.itemdt_user_status);
            btn_user_management_open = itemView.findViewById(R.id.btn_user_management_open);
            btn_user_management_close = itemView.findViewById(R.id.btn_user_management_close);
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


