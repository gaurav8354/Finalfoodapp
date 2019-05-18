package com.instadp.profilepicture.finalfoodapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.instadp.profilepicture.finalfoodapp.Common.Common;
import com.instadp.profilepicture.finalfoodapp.model.User;

public class MainActivity extends AppCompatActivity {
    TextView signup,forgetid;
    EditText password,number;
    ImageView textvisible;
    Button signin;
    DatabaseReference table_user;
    String otpstatus="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        changeStatusBarColor("#FFFFFF");
        status_icon_color();
        isReadStoragePermissionGranted();
        isWriteStoragePermissionGranted();
        idsetter();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        table_user=database.getReference("User");
        listner();
    }

    private void idsetter() {
        signup= (TextView) findViewById(R.id.textview_singup);
        forgetid= (TextView) findViewById(R.id.textview_forget_id);
        textvisible= (ImageView) findViewById(R.id.textvisibleimage);
        password= (EditText) findViewById(R.id.textview_password_login);
        number= (EditText) findViewById(R.id.textview_number_login);
        signin= (Button) findViewById(R.id.button_sign_in);
    }

    private void listner() {
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Registration.class);
                startActivity(i);

            }
        });

        textvisible.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){


                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password.setSelection(password.getText().length());

                    return true;
                }
                else
                    if (event.getAction() == MotionEvent.ACTION_DOWN)
                {



                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password.setSelection(password.getText().length());
                }
                return false;
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user did not exist
                        if(dataSnapshot.child(number.getText().toString()).exists()) {


                            User user = dataSnapshot.child(number.getText().toString()).getValue(User.class);
                            user.setPhone(number.getText().toString()); //phone
                            if (user.getPassword().equals(password.getText().toString())) {
                                otpstatus=user.getOtp();
                                if (otpstatus.equals("false"))
                                {
                                    Intent i=new Intent(MainActivity.this,Otp_verification.class);
                                    i.putExtra("phone",user.getPhone());
                                    i.putExtra("email",user.getEmail());
                                    i.putExtra("name",user.getName());
                                    i.putExtra("password",user.getPassword());
                                    startActivity(i);
                                }
                                else
                                {
                                    Intent i=new Intent(MainActivity.this,Home.class);
                                    Common.currentuser=user;
                                    startActivity(i);
                                    finish();
                                }
                                Toast.makeText(MainActivity.this, otpstatus, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(MainActivity.this, "signin failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "user not exist in database", Toast.LENGTH_SHORT).show();

                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("1","Permission is granted1");
                return true;
            } else {

                Log.v("12","Permission is revoked1");

                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("123","Permission is granted1");
            return true;
        }
    }

    public  boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("1234","Permission is granted2");
                return true;
            } else {

                Log.v("12345","Permission is revoked2");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("123456","Permission is granted2");
            return true;
        }
    }


}
