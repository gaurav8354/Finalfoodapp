package com.instadp.profilepicture.finalfoodapp;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.instadp.profilepicture.finalfoodapp.Database.Database;
import com.instadp.profilepicture.finalfoodapp.model.Food;
import com.instadp.profilepicture.finalfoodapp.model.Order;
import com.squareup.picasso.Picasso;
public class FoodDetails extends AppCompatActivity {
    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    String foodId="";
    FirebaseDatabase database;
    DatabaseReference foods;
    Food currentfood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
//Firebase
        database=FirebaseDatabase.getInstance();
        foods=database.getReference("Food");
        //int view
        numberButton= (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart= (FloatingActionButton) findViewById(R.id.floating_btn);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentfood.getName(),
                        numberButton.getNumber(),
                        currentfood.getPrice(),
                        currentfood.getDiscount()
                ));
                Toast.makeText(FoodDetails.this, "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });
        food_description= (TextView) findViewById(R.id.food_discription);
        food_name= (TextView) findViewById(R.id.food_name);
        food_price= (TextView) findViewById(R.id.food_price);
        food_image= (ImageView) findViewById(R.id.img_food);
        collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);
        //get food id from intent
        if(getIntent()!=null)
            foodId=getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty())
        {
            getDEtailFood(foodId);
        }
    }
    private void getDEtailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //if added by me
                    currentfood = dataSnapshot.getValue(Food.class);
                //if my
                try {
                    Picasso.with(getBaseContext()).load(currentfood.getImage()).into(food_image);

                    collapsingToolbarLayout.setTitle(currentfood.getName());
                    food_price.setText(currentfood.getPrice());
                    food_name.setText(currentfood.getName());
                    food_description.setText(currentfood.getDescription());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
                // We want to change tint color to white again.
                // You can also record the flags in advance so that you can turn UI back completely if
                // you have set other flags before, such as translucent or full screen.
                decor.setSystemUiVisibility(0);
            }
        }

    }
}
