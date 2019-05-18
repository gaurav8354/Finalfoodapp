package com.instadp.profilepicture.finalfoodapp;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.instadp.profilepicture.finalfoodapp.model.User;
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;
import com.stfalcon.smsverifycatcher.OnSmsCatchListener;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

import java.util.concurrent.TimeUnit;
public class Otp_verification extends AppCompatActivity implements VerificationListener {
    Button skip, verify_otp;
    EditText text_otp;
    TextView resendotp;
    String number, email, password, name, otp_code;
    Verification mVerification;
    DonutProgress dprogress;
    SmsVerifyCatcher smsVerifyCatcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        getSupportActionBar().hide();
        changeStatusBarColor("#FFFFFF");
        status_icon_color();
        idsetter();
        Intent i = getIntent();

        number = i.getStringExtra("phone");
        email = i.getStringExtra("email");
        password = i.getStringExtra("password");
        name = i.getStringExtra("name");
        smsVerifyCatcher = new SmsVerifyCatcher(this, new OnSmsCatchListener<String>() {
            @Override
            public void onSmsCatch(String message) {
                String code = parseCode(message);
                text_otp.setText(code);//set code in edit text
                //then you can send verification code to server
            }
        });
        listner();
        startprogress();
        mVerification = SendOtpVerification.createSmsVerification
                (SendOtpVerification
                        .config("+91" + number).senderId("EATNOW")
                        .context(this)
                        .autoVerification(true)
                        .build(), this);
        mVerification.initiate();
    }
    private void startprogress() {
        myCountDownTimer.start();
    }
    CountDownTimer myCountDownTimer = new CountDownTimer(
            60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

            int time = (int) millisUntilFinished;
            int seconds = time / 1000 % 60;
            int minutes = (time / (1000 * 60)) % 60;
            dprogress.setProgress(Integer.parseInt(twoDigitString(seconds)));
            dprogress.setText(twoDigitString(seconds));
            dprogress.setMax(60);
            resendotp.setVisibility(View.INVISIBLE);
        }
        private String twoDigitString(long number) {
            if (number == 0) {
                return "00";
            } else if (number / 10 == 0) {
                return "0" + number;
            }
            return String.valueOf(number);
        }
        @Override
        public void onFinish() {
            Toast.makeText(Otp_verification.this, "fff", Toast.LENGTH_SHORT).show();
            dprogress.setVisibility(View.INVISIBLE);
            resendotp.setVisibility(View.VISIBLE);
        }
    };
    private String parseCode(String msg) {
        String str = msg.replaceAll("[a-zA-Z]", "");
        String str1=msg.replaceAll("\\s+","");
        String str2=msg.replaceAll("[-+.^:,]","");
        return str;
    }
    private void listner() {
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Otp_verification.this, Dashboard.class);
                startActivity(i);
            }
        });
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp_code = text_otp.getText().toString();
                mVerification.verify(otp_code);
            }
        });
        resendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dprogress.setVisibility(View.VISIBLE);
                myCountDownTimer.start();
                mVerification.resend("voice");
            }
        });
    }
    private boolean updateOtp(String number, String email, String password, String name) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User").child(number);
        User user = new User(name, password, email, number, "true");
        databaseReference.setValue(user);
        return true;
    }
    private void idsetter() {
        skip = (Button) findViewById(R.id.button_verify_later);
        verify_otp = (Button) findViewById(R.id.button_verify_otp);
        text_otp = (EditText) findViewById(R.id.edittext_enter_otp);
        dprogress = (DonutProgress) findViewById(R.id.donutprogress);
        resendotp = (TextView) findViewById(R.id.textview_resendotp);
    }
    @Override
    public void onInitiated(String response) {
        Toast.makeText(this, "otp send", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onInitiationFailed(Exception paramException) {

        Toast.makeText(this, "otp not send", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onVerified(String response) {
        updateOtp(number, email, password, name);
        Toast.makeText(this, "otp verified", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Otp_verification.this, Dashboard.class);
        startActivity(i);
    }
    @Override
    public void onVerificationFailed(Exception paramException) {
        Toast.makeText(this, "not verified", Toast.LENGTH_SHORT).show();
    }
    private void changeStatusBarColor(String color) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
    void status_icon_color() {
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
    @Override
    protected void onStart() {
        super.onStart();
        smsVerifyCatcher.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        smsVerifyCatcher.onStop();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}