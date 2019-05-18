package com.instadp.profilepicture.finalfoodapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.instadp.profilepicture.finalfoodapp.Common.Common;
import com.instadp.profilepicture.finalfoodapp.ViewHolder.OrderViewHolder;
import com.instadp.profilepicture.finalfoodapp.model.Request;

public class OrderStatus extends AppCompatActivity {
    public RecyclerView recyclerView;
    public  RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    FirebaseDatabase database;
    DatabaseReference request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        database=FirebaseDatabase.getInstance();
        request=database.getReference("Requests");
        recyclerView= (RecyclerView) findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(getIntent().getStringExtra("userPhone")==null) {
            loadOrders(Common.currentuser.getPhone());
        }
        else
        {
            loadOrders(getIntent().getStringExtra("userPhone"));
            Toast.makeText(this, getIntent().getStringExtra("userPhone"), Toast.LENGTH_SHORT).show();
        }
        }
    private void loadOrders(String phone) {
    adapter= new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
            Request.class,
            R.layout.orderlayout,
            OrderViewHolder.class,
            request.orderByChild("phone")
            .equalTo(phone)) {
        @Override
        protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
            viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
            viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
            viewHolder.txtOrderAddress.setText(model.getAddress());
            viewHolder.txtOrderPhone.setText(model.getPhone());
        }
    };
    adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

}
