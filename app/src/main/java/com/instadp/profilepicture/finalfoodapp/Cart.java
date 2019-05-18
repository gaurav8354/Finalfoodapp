package com.instadp.profilepicture.finalfoodapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.instadp.profilepicture.finalfoodapp.Common.Common;
import com.instadp.profilepicture.finalfoodapp.Database.Database;
import com.instadp.profilepicture.finalfoodapp.ViewHolder.CartAdapter;
import com.instadp.profilepicture.finalfoodapp.model.Order;
import com.instadp.profilepicture.finalfoodapp.model.Request;
import java.util.ArrayList;
import java.util.List;
import info.hoang8f.widget.FButton;
public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtTotalPrice;
    FButton btnPlace;
  String orderId;
    List<Order> cart=new ArrayList<>();
    CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        changeStatusBarColor("#FFFFFF");
        status_icon_color();
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        recyclerView= (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        txtTotalPrice= (TextView) findViewById(R.id.total);
        btnPlace= (FButton) findViewById(R.id.btn_place_order);
        loadListFood();
        listner();
    }
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step");
        alertDialog.setMessage("enter Alternate number");
        final EditText edttext = new EditText(Cart.this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(  LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edttext.setLayoutParams(lp);
        alertDialog.setView(edttext);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request=new Request(
                        Common.currentuser.getPhone(),
                        Common.currentuser.getName(),
                        edttext.getText().toString(),
                        txtTotalPrice.getText().toString(),cart
                );
                orderId=String.valueOf(System.currentTimeMillis());
                requests.child(orderId).setValue(request);
                new Database(getBaseContext()).cleanCart();
                Intent i=new Intent(Cart.this,Paytm_progress_transiction.class);
                i.putExtra("paytm", txtTotalPrice.getText().toString());
                i.putExtra("orderid",orderId);
                Toast.makeText(Cart.this, txtTotalPrice.getText().toString(), Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
    private void loadListFood() {
        cart=new Database(this).getCarts();
        adapter=new CartAdapter(cart,this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        int total=0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        txtTotalPrice.setText(total+"");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals(Common.DELETE))
{
    deleteCart(item.getOrder());
}
        return true;
    }
    private void deleteCart(int order) {
         cart.remove(order);
        new Database(this).cleanCart();
        for(Order item:cart)
        {
            new Database(this).addToCart(item);
            loadListFood();
        }
    }
    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
    void status_icon_color(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            if (true) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decor.setSystemUiVisibility(0);
            }
        }
    }
    private void listner() {
        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //request
                if(cart.size()>0)
                {
                    showAlertDialog();}
                else
                {
                    Toast.makeText(Cart.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
