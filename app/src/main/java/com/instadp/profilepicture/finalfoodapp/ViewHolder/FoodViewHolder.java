package com.instadp.profilepicture.finalfoodapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.instadp.profilepicture.finalfoodapp.Interface.ItemOnClickListner;
import com.instadp.profilepicture.finalfoodapp.R;

/**
 * Created by gaurav on 3/12/2018.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {


    public TextView food_name;
    public ImageView food_image;

    public void setItemClickLisner(ItemOnClickListner itemClickLisner) {
        this.itemClickLisner = itemClickLisner;
    }

    private ItemOnClickListner itemClickLisner;

     public FoodViewHolder(View itemView) {
        super(itemView);
//
         food_name= (TextView) itemView.findViewById(R.id.food_name);
         food_image= (ImageView) itemView.findViewById(R.id.food_image);
         itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickLisner.onClick(v,getAdapterPosition(),false);


    }
}
