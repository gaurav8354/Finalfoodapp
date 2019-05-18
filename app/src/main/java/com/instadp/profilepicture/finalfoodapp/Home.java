package com.instadp.profilepicture.finalfoodapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.instadp.profilepicture.finalfoodapp.Common.Common;
import com.instadp.profilepicture.finalfoodapp.Interface.ItemOnClickListner;
import com.instadp.profilepicture.finalfoodapp.Service.ListenOrder;
import com.instadp.profilepicture.finalfoodapp.ViewHolder.MenuViewHolder;
import com.instadp.profilepicture.finalfoodapp.model.Category;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView textname;
    FirebaseDatabase database;
    DatabaseReference category;
    private FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    RecyclerView recyclermenu;
    RecyclerView.LayoutManager layoutManager;

    //private FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        changeStatusBarColor("WHITE");
        status_icon_color();
        //init firebase
        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartintent=new Intent(Home.this,Cart.class);
                startActivity(cartintent);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toolbar.setTitleTextColor(Color.BLACK);
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//setuser name
        View handlerView=navigationView.getHeaderView(0);
        textname= (TextView)handlerView.findViewById(R.id.user_name_display);

        //if added by me
        if(!Common.currentuser.getName().equals(null)) {
            textname.setText(Common.currentuser.getName());
        }
//load menu
        recyclermenu= (RecyclerView) findViewById(R.id.recycle_menu);
        recyclermenu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclermenu.setLayoutManager(layoutManager);
        loadMenu();
        Intent service=new Intent(Home.this, ListenOrder.class);
        startService(service);
    }
    private void loadMenu() {
        adapter =new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
                viewHolder.texMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final Category clickitem=model;
                viewHolder.setItemClickListner(new ItemOnClickListner() {


                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent i=new Intent(Home.this,FoodList.class);
                        i.putExtra("CategoryId",adapter.getRef(position).getKey());
                        Toast.makeText(Home.this, adapter.getRef(position).getKey(), Toast.LENGTH_SHORT).show();
                        startActivity(i);
                        // Toast.makeText(Home.this, ""+clickitem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        recyclermenu.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cart) {

            Intent cartIntent=new Intent(Home.this,Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.menu) {

        } else if (id == R.id.order) {
            Intent orderIntent=new Intent(Home.this,OrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.logout) {

            Intent signin=new Intent(Home.this,MainActivity.class);
            signin.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signin);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
