package com.instadp.profilepicture.finalfoodapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.instadp.profilepicture.finalfoodapp.Interface.ItemOnClickListner;
import com.instadp.profilepicture.finalfoodapp.R;

/**
 * Created by gaurav on 3/18/2018.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAddress;

    private ItemOnClickListner itemOnClickListner;
    public OrderViewHolder(View itemView) {
        super(itemView);
        txtOrderAddress= (TextView) itemView.findViewById(R.id.order_address);
        txtOrderId= (TextView) itemView.findViewById(R.id.order_id);
        txtOrderStatus= (TextView) itemView.findViewById(R.id.order_status);
        txtOrderPhone= (TextView) itemView.findViewById(R.id.order_phone);
        itemView.setOnClickListener(this);
    }

    public  void setItemOnClickListner(ItemOnClickListner itemOnClickListner)
    {
        this.itemOnClickListner=itemOnClickListner;
    }

    @Override
    public void onClick(View v) {

    }
}
