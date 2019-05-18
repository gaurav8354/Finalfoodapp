package com.instadp.profilepicture.finalfoodapp;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.instadp.profilepicture.finalfoodapp.model.User;

public class Registration extends AppCompatActivity {
    Button signin;
    EditText name,pass,confirm_pass,email,number;
    String username,userpass,userconfirm_pass,useremail,usernumber;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();
        changeStatusBarColor("#FFFFFF");
        status_icon_color();
        idsetter();
        listner();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
      table_user=database.getReference("User");

    }

    private void listner() {
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=name.getText().toString();
                userpass=pass.getText().toString();
                userconfirm_pass=confirm_pass.getText().toString();
                useremail=email.getText().toString();
                usernumber=number.getText().toString();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(number.getText().toString()).exists()){

                            Toast.makeText(Registration.this, "Phone number already exist", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            User user=new User(name.getText().toString(),pass.getText().toString(),email.getText().toString(),number.getText().toString(),"false");
                            table_user.child(number.getText().toString()).setValue(user);
                            Toast.makeText(Registration.this, "Signin success", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
    }
    private void idsetter() {
        signin= (Button) findViewById(R.id.button_registration_signin);
        name= (EditText) findViewById(R.id.textview_registration_name);
        pass= (EditText) findViewById(R.id.textview_registration_password);
        confirm_pass= (EditText) findViewById(R.id.textview_registration_confirm_pass);
        email= (EditText) findViewById(R.id.textview_registration_email);
        number= (EditText) findViewById(R.id.textview_registration_number);
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
