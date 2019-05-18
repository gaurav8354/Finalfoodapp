package com.instadp.profilepicture.finalfoodapp.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.instadp.profilepicture.finalfoodapp.Common.Common;
import com.instadp.profilepicture.finalfoodapp.Interface.ItemOnClickListner;
import com.instadp.profilepicture.finalfoodapp.R;
import com.instadp.profilepicture.finalfoodapp.model.Order;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by gaurav on 3/17/2018.
 */

class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
,View.OnCreateContextMenuListener{

    public TextView txt_crt_name,txt_price;
    public ImageView img_cart_count;
    private ItemOnClickListner itemOnClickListner;


    public void setTxt_crt_name(TextView txt_crt_name) {
        this.txt_crt_name = txt_crt_name;
    }

    public CardViewHolder(View itemView) {
        super(itemView);
        txt_crt_name= (TextView) itemView.findViewById(R.id.cart_item_name);
        txt_price = (TextView) itemView.findViewById(R.id.cart_item_Price);
        img_cart_count= (ImageView) itemView.findViewById(R.id.cart_item_count);
       itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {


        menu.setHeaderTitle("select action");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);

    }
}


public  class CartAdapter extends  RecyclerView.Adapter<CardViewHolder>{


   private List<Order> listdata;
    private Context context;

    public CartAdapter(List<Order> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.cartlayout,parent,false);
        return  new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {

        TextDrawable drawable=TextDrawable.builder()
                .buildRound(""+listdata.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(drawable);

    Locale locale=new Locale("en","US");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
        int price=(Integer.parseInt(listdata.get(position).getPrice()))*(Integer.parseInt(listdata.get(position).getQuantity()));
        holder.txt_price.setText(fmt.format(price));
        holder.txt_crt_name.setText(listdata.get(position).getProduceName());

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}