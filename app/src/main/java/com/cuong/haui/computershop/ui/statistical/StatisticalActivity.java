package com.cuong.haui.computershop.ui.statistical;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cuong.haui.computershop.R;
import com.cuong.haui.computershop.model.SaleOrder;
import com.cuong.haui.computershop.model.SanPhamBanRa;
import com.cuong.haui.computershop.model.SanPhamMoi;
import com.cuong.haui.computershop.utils.DefaultFirst1;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

public class StatisticalActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
        toolbar = findViewById(R.id.toobar);
        ActionToolBar();
        //
        DatabaseReference myRefAdminTime = database.getReference("Products");
        // lay thang bao hanh san pham
        myRefAdminTime.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DefaultFirst1.mangSpConLai.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    SanPhamMoi sanPhamMoi = postSnapshot.getValue(SanPhamMoi.class);
                    if(sanPhamMoi!= null){
                        DefaultFirst1.mangSpConLai.add(sanPhamMoi);
                        //Toast.makeText(view.getContext(), sanPhamMoi.toString(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message

                // ...
            }
        });
        //
        DatabaseReference myRefAdminSale = database.getReference("SaleOrders");
        // lay thang bao hanh san pham
        myRefAdminSale.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DefaultFirst1.allSaleOrder.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    SaleOrder sanPhamMoi = postSnapshot.getValue(SaleOrder.class);
                    if(sanPhamMoi!= null){
                        DefaultFirst1.allSaleOrder.add(sanPhamMoi);
                        //Toast.makeText(view.getContext(), sanPhamMoi.toString(), Toast.LENGTH_LONG).show();
                    }
                }
                Ve();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Getting Post failed, log a message

                // ...
            }
        });
        //

    }

    private void Ve(){
        for(int i=0;i<DefaultFirst1.mangSpConLai.size();i++){
            DefaultFirst1.saleOrderbyProduct.add(new SanPhamBanRa(DefaultFirst1.mangSpConLai.get(i).product_id,0));
        }
        for (int i=0;i<DefaultFirst1.allSaleOrder.size();i++){
            DefaultFirst1.saleOrderbyProduct.get(DefaultFirst1.allSaleOrder.get(i).getIdsp()-1).setSolongbanra(DefaultFirst1.saleOrderbyProduct.get(DefaultFirst1.allSaleOrder.get(i).getIdsp()-1).solongbanra+DefaultFirst1.allSaleOrder.get(i).getSoluong());
        }
        GraphView graph = (GraphView) findViewById(R.id.graph);


        int size = DefaultFirst1.mangSpConLai.size();
        DataPoint[] values = new DataPoint[size];
        for (int i=0; i<size; i++) {
            Integer xi = Integer.parseInt(String.valueOf(DefaultFirst1.mangSpConLai.get(i).product_id));
            //Toast.makeText(getApplicationContext(), String.valueOf(xi), Toast.LENGTH_LONG).show();
            Integer yi = Integer.parseInt(String.valueOf(DefaultFirst1.mangSpConLai.get(i).current_quantity));
            DataPoint v = new DataPoint(xi, yi);
            values[i] = v;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(values);
        graph.addSeries(series);
//ban ra

        GraphView graphbanra = (GraphView) findViewById(R.id.graphBanRa);
        int size2 = DefaultFirst1.mangSpConLai.size();
        DataPoint[] values2 = new DataPoint[size2];
        for (int i=0; i<size2; i++) {
            Integer xi = Integer.parseInt(String.valueOf(DefaultFirst1.saleOrderbyProduct.get(i).product_id));
            Integer yi = Integer.parseInt(String.valueOf(DefaultFirst1.saleOrderbyProduct.get(i).solongbanra));
            DataPoint v = new DataPoint(xi, yi);
            values2[i] = v;
        }
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(values2);
        graphbanra.addSeries(series2);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}