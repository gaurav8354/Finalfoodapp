package com.instadp.profilepicture.finalfoodapp;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.instadp.profilepicture.finalfoodapp.Common.Common;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import okhttp3.OkHttpClient;
public class FinalCheckout extends AppCompatActivity {
    OkHttpClient client;
    //Your authentication key
    int c;
    public String start="";
    private int i9 = 0;
     int length;
    String orderid="0";
    DatabaseReference ref;
    String authkey = "230648AUNNlfCklLu5b6ae822";
    //Multiple mobiles numbers separated by comma
    String mobiles = "";
    String senderId = "EATNOW";
    //Your message to send, Add URL encoding here.
    String message = "";
    StringBuilder stringBuilders=new StringBuilder();
    StringBuilder stringBuilderl=new StringBuilder();
    //define route
    String route="4";
    URLConnection myURLConnection=null;
    URL myURL=null;
    BufferedReader reader=null;
    String mainUrl="http://api.msg91.com/api/sendhttp.php?";
    TextView price,order;
   public String amt="₹ ",name,email,number,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_checkout);
        Intent i=getIntent();
        changeStatusBarColor("WHITE");
        status_icon_color();
        name= Common.currentuser.getName();
        email=Common.currentuser.getEmail();
        number=Common.currentuser.getPhone();
        Intent i1=getIntent();
        orderid=i1.getStringExtra("orderid1");
//        sendmessage(name,number,email);
       // loadstring(email);
        Toast.makeText(this, name+email+number,Toast.LENGTH_SHORT).show();
         Float x=Float.parseFloat(i.getStringExtra("paid"));
         date=i.getStringExtra("date");
        int b = (int)Math.round(x);
        c=b;
        amt=amt.concat(b+" Paid");
        ref = FirebaseDatabase.getInstance().getReference("Requests").child(orderid).child("food");
stringBuilders.append("<!doctype html>\n" +
        "<html>\n" +
        "<head>\n" +
        "    <meta charset=\"utf-8\">\n" +
        "    <title>A simple, clean, and responsive HTML invoice template</title>\n" +
        "    \n" +
        "    <style>\n" +
        "    .invoice-box {\n" +
        "        max-width: 800px;\n" +
        "        margin: auto;\n" +
        "        padding: 30px;\n" +
        "        border: 1px solid #eee;\n" +
        "        box-shadow: 0 0 10px rgba(0, 0, 0, .15);\n" +
        "        font-size: 16px;\n" +
        "        line-height: 24px;\n" +
        "        font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n" +
        "        color: #555;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table {\n" +
        "        width: 100%;\n" +
        "        line-height: inherit;\n" +
        "        text-align: left;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table td {\n" +
        "        padding: 5px;\n" +
        "        vertical-align: top;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table tr td:nth-child(2) {\n" +
        "        text-align: right;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table tr.top table td {\n" +
        "        padding-bottom: 20px;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table tr.top table td.title {\n" +
        "        font-size: 45px;\n" +
        "        line-height: 45px;\n" +
        "        color: #333;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table tr.information table td {\n" +
        "        padding-bottom: 40px;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table tr.heading td {\n" +
        "        background: #eee;\n" +
        "        border-bottom: 1px solid #ddd;\n" +
        "        font-weight: bold;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table tr.details td {\n" +
        "        padding-bottom: 20px;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table tr.item td{\n" +
        "        border-bottom: 1px solid #eee;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table tr.item.last td {\n" +
        "        border-bottom: none;\n" +
        "    }\n" +
        "    \n" +
        "    .invoice-box table tr.total td:nth-child(2) {\n" +
        "        border-top: 2px solid #eee;\n" +
        "        font-weight: bold;\n" +
        "    }\n" +
        "    \n" +
        "    @media only screen and (max-width: 600px) {\n" +
        "        .invoice-box table tr.top table td {\n" +
        "            width: 100%;\n" +
        "            display: block;\n" +
        "            text-align: center;\n" +
        "        }\n" +
        "        \n" +
        "        .invoice-box table tr.information table td {\n" +
        "            width: 100%;\n" +
        "            display: block;\n" +
        "            text-align: center;\n" +
        "        }\n" +
        "    }\n" +
        "    \n" +
        "    /** RTL **/\n" +
        "    .rtl {\n" +
        "        direction: rtl;\n" +
        "        font-family: Tahoma, 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n" +
        "    }\n" +
        "    \n" +
        "    .rtl table {\n" +
        "        text-align: right;\n" +
        "    }\n" +
        "    \n" +
        "    .rtl table tr td:nth-child(2) {\n" +
        "        text-align: left;\n" +
        "    }\n" +
        "    </style>\n" +
        "</head>\n" +
        "\n" +
        "<body>\n" +
        "    <div class=\"invoice-box\">\n" +
        "        <table cellpadding=\"0\" cellspacing=\"0\">\n" +
        "            <tr class=\"top\">\n" +
        "                <td colspan=\"2\">\n" +
        "                    <table>\n" +
        "                        <tr>\n" +
        "                            <td class=\"title\">\n" +
        "                                <img src=\"https://www.techzooper.com/paytm/psitlogo.png\" style=\"width:100%; max-width:300px;\">\n" +
        "                            </td>\n" +
        "                            \n" +
        "                            <td>\n"+orderid+"<br>"+date+"</td>\n" +
        "                        </tr>\n" +
        "                    </table>\n" +
        "                </td>\n" +
        "            </tr>\n" +
        "            \n" +
        "            <tr class=\"information\">\n" +
        "                <td colspan=\"2\">\n" +
        "                    <table>\n" +
        "                        <tr>\n" +
        "                            <td>\n" +
        "                                Psit Canteen<br>\n" +
        "                                Psit Campus<br>\n" +
        "\t\t\t\t\t\t\t\tBhauti,Kanpur,209305.\n" +
        "                            </td>\n" +
        "                            \n" +
        "                            <td>"+name+"<br>"+email+"        </td>\n" +
        "                        </tr>\n" +
        "                    </table>\n" +
        "                </td>\n" +
        "            </tr>\n" +
        "            \n" +
        "            <tr class=\"heading\">\n" +
        "                <td>\n" +
        "                    Payment Method\n" +
        "                </td>\n" +
        "                \n" +
        "                <td>\n" +
        "                   Wallet #\n" +
        "                </td>\n" +
        "            </tr>\n" +
        "            \n" +
        "            <tr class=\"details\">\n" +
        "                <td>\n" +
        "                    Paytm Wallet\n" +
        "                </td>\n" +
        "                \n" +
        "                <td>"+amt+"</td>\n" +
        "            </tr>\n" +
        "            \n" +
        "            <tr class=\"heading\">\n" +
        "                <td>\n" +
        "                    Item\n" +
        "                </td>\n" +
        "                \n" +
        "                <td>\n" +
        "                    Price\n" +
        "                </td>\n" +
        "            </tr>\n" +
        "            ");
        final String frt=stringBuilders.toString();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                length = (int) dataSnapshot.getChildrenCount();
                String[] sampleString = new String[length];
                while(i9 < length) {
                    sampleString[i9] = iterator.next().getValue().toString();
                    i9++;
                }
                String s="";
                for(int i=0;i<length;++i)
                {
                    Log.d("123456",i+"no  "+sampleString[i]);

                    s=s+"\n" +
                            "            <tr class=\"item\">\n" +
                            "                <td>"+ StringUtils.substringBetween(sampleString[i],"produceName=","}")+
                            " X "+StringUtils.substringBetween(sampleString[i],"quantity=",",")+" </td>\n" +
                            "                \n" +
                            "                <td>"+"₹ "+Integer.parseInt(StringUtils.substringBetween(sampleString[i],"quantity=",","))*
                            Integer.parseInt(StringUtils.substringBetween(sampleString[i],"price=",","))+"</td>\n" +
                            "            </tr>";
             //   stringBuilderm.append();
                }
                Log.d("main",s);

                String l="<tr class=\"total\">\n" +
                        "                <td></td>\n" +
                        "                \n" +
                        "                <td>"+amt+"\n" +
                        "                </td>\n" +
                        "            </tr>\n" +
                        "        </table>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>";
                finalmail(s,l,frt);
               // stringBuilderm.append(s);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
     //   stringBuilderl.append();

      //
        //  idsetter();



        price= (TextView) findViewById(R.id.final_amount_paid);
        order= (TextView) findViewById(R.id.order_id_textview_finalcheckout);
        price.setText(amt);
        order.setText(orderid);

           sendmessage(name,number,email);

    }

    private void finalmail(String s11, String s1, String s) {
        String min=s1;
        String mil=s;
        String str=s11;
        StrBuilder s12 =new StrBuilder();
       s12.append(s);
        s12.append(str);
        s12.append(min);
        BackgroundMail.newBuilder(this)
                .withUsername("psitfoodapp@gmail.com")
                .withPassword("gaurav123@")
                .withMailto("gaurav18.2013@gmail.com")
                .withType(BackgroundMail.TYPE_HTML)
                .withSubject("Eat it purchase recipt")
                .withBody(s12.toString())
                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                    @Override
                    public void onSuccess() {
                        //do some magic
                    }
                })
                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                    @Override
                    public void onFail() {
                        //do some magic
                    }
                })
                .send();
        Log.d("1122",s12.toString());


    }
//    private void loadstring(String email) {
// this.email=email;
//        ref = FirebaseDatabase.getInstance().getReference("Requests").child(orderid).child("food");
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
//                length = (int) dataSnapshot.getChildrenCount();
//                String[] sampleString = new String[length];
//                while(i < length) {
//                    sampleString[i] = iterator.next().getValue().toString();
//                    i++;
//                }
//            for(int i=0;i<length;++i)
//            {
//                Log.d("123456",i+"no  "+sampleString[i]);
//                middle=middle.concat("\n" +
//                        "            <tr class=\"item\">\n" +
//                        "                <td>"+ StringUtils.substringBetween(sampleString[i],"produceName=","}")+
//                        " X "+StringUtils.substringBetween(sampleString[i],"quantity=",",")+" </td>\n" +
//                        "                \n" +
//                        "                <td>"+"₹ "+Integer.parseInt(StringUtils.substringBetween(sampleString[i],"quantity=",","))*
//                        Integer.parseInt(StringUtils.substringBetween(sampleString[i],"price=",","))+"</td>\n" +
//                        "            </tr>");
//            }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        //sendmail(email,middle);
//    }

    private void sendmessage(String name, String number, String email) {
    message="Dear "+name+" Your Order number " +orderid+ " of Rs "+c+" has been placed sucessfully. "+"Check your email  "+email+" for more details. "+"We will update you when your order will be ready";
        String encoded_message= URLEncoder.encode(message);
//Send SMS API
//Prepare parameter string
        StringBuilder sbPostData= new StringBuilder(mainUrl);
        sbPostData.append("authkey="+authkey);
        sbPostData.append("&mobiles="+number);
        sbPostData.append("&message="+encoded_message);
        sbPostData.append("&route="+route);
        sbPostData.append("&sender="+senderId);
//final string
        mainUrl = sbPostData.toString();
//                 client = new OkHttpClient();
//                try {
//                    run(mainUrl);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
        try
        {
            //prepare connection
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            //reading response
            String response;
            while ((response = reader.readLine()) != null)
                //print response
                Log.d("RESPONSE", ""+response);
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void idsetter() {
    price= (TextView) findViewById(R.id.final_amount_paid);
        order= (TextView) findViewById(R.id.order_id_textview_finalcheckout);
        price.setText(amt);
        order.setText(orderid);
    }
//
    private void sendmail(String s, String middle) {

     String   start="<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>A simple, clean, and responsive HTML invoice template</title>\n" +
                "    \n" +
                "    <style>\n" +
                "    .invoice-box {\n" +
                "        max-width: 800px;\n" +
                "        margin: auto;\n" +
                "        padding: 30px;\n" +
                "        border: 1px solid #eee;\n" +
                "        box-shadow: 0 0 10px rgba(0, 0, 0, .15);\n" +
                "        font-size: 16px;\n" +
                "        line-height: 24px;\n" +
                "        font-family: 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n" +
                "        color: #555;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table {\n" +
                "        width: 100%;\n" +
                "        line-height: inherit;\n" +
                "        text-align: left;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table td {\n" +
                "        padding: 5px;\n" +
                "        vertical-align: top;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table tr td:nth-child(2) {\n" +
                "        text-align: right;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table tr.top table td {\n" +
                "        padding-bottom: 20px;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table tr.top table td.title {\n" +
                "        font-size: 45px;\n" +
                "        line-height: 45px;\n" +
                "        color: #333;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table tr.information table td {\n" +
                "        padding-bottom: 40px;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table tr.heading td {\n" +
                "        background: #eee;\n" +
                "        border-bottom: 1px solid #ddd;\n" +
                "        font-weight: bold;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table tr.details td {\n" +
                "        padding-bottom: 20px;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table tr.item td{\n" +
                "        border-bottom: 1px solid #eee;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table tr.item.last td {\n" +
                "        border-bottom: none;\n" +
                "    }\n" +
                "    \n" +
                "    .invoice-box table tr.total td:nth-child(2) {\n" +
                "        border-top: 2px solid #eee;\n" +
                "        font-weight: bold;\n" +
                "    }\n" +
                "    \n" +
                "    @media only screen and (max-width: 600px) {\n" +
                "        .invoice-box table tr.top table td {\n" +
                "            width: 100%;\n" +
                "            display: block;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        \n" +
                "        .invoice-box table tr.information table td {\n" +
                "            width: 100%;\n" +
                "            display: block;\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    /** RTL **/\n" +
                "    .rtl {\n" +
                "        direction: rtl;\n" +
                "        font-family: Tahoma, 'Helvetica Neue', 'Helvetica', Helvetica, Arial, sans-serif;\n" +
                "    }\n" +
                "    \n" +
                "    .rtl table {\n" +
                "        text-align: right;\n" +
                "    }\n" +
                "    \n" +
                "    .rtl table tr td:nth-child(2) {\n" +
                "        text-align: left;\n" +
                "    }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"invoice-box\">\n" +
                "        <table cellpadding=\"0\" cellspacing=\"0\">\n" +
                "            <tr class=\"top\">\n" +
                "                <td colspan=\"2\">\n" +
                "                    <table>\n" +
                "                        <tr>\n" +
                "                            <td class=\"title\">\n" +
                "                                <img src=\"https://www.sparksuite.com/images/logo.png\" style=\"width:100%; max-width:300px;\">\n" +
                "                            </td>\n" +
                "                            \n" +
                "                            <td>\n"+orderid+"<br>"+date+"</td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr class=\"information\">\n" +
                "                <td colspan=\"2\">\n" +
                "                    <table>\n" +
                "                        <tr>\n" +
                "                            <td>\n" +
                "                                Psit Canteen<br>\n" +
                "                                Psit Campus<br>\n" +
                "\t\t\t\t\t\t\t\tBhauti,Kanpur,209305.\n" +
                "                            </td>\n" +
                "                            \n" +
                "                            <td>"+name+"<br>"+email+"        </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr class=\"heading\">\n" +
                "                <td>\n" +
                "                    Payment Method\n" +
                "                </td>\n" +
                "                \n" +
                "                <td>\n" +
                "                   Wallet #\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr class=\"details\">\n" +
                "                <td>\n" +
                "                    Paytm Wallet\n" +
                "                </td>\n" +
                "                \n" +
                "                <td>"+amt+"</td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr class=\"heading\">\n" +
                "                <td>\n" +
                "                    Item\n" +
                "                </td>\n" +
                "                \n" +
                "                <td>\n" +
                "                    Price\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            ";
     String   last="<tr class=\"total\">\n" +
                "                <td></td>\n" +
                "                \n" +
                "                <td>"+amt+"\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
//        stringBuilder.append(start).append(middle).append(last);
//        BackgroundMail.newBuilder(this)
//                .withUsername("psitfoodapp@gmail.com")
//                .withPassword("gaurav123@")
//                .withMailto("gaurav18.2013@gmail.com")
//                .withType(BackgroundMail.TYPE_HTML)
//                .withSubject("Eat it purchase recipt")
//                .withBody(stringBuilder.toString())
//                .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
//                    @Override
//                    public void onSuccess() {
//                        //do some magic
//                    }
//                })
//                .withOnFailCallback(new BackgroundMail.OnFailCallback() {
//                    @Override
//                    public void onFail() {
//                        //do some magic
//                    }
//                })
//                .send();
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
