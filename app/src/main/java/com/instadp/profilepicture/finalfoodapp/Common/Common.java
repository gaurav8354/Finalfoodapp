package com.instadp.profilepicture.finalfoodapp.Common;

import com.instadp.profilepicture.finalfoodapp.model.User;

/**
 * Created by gaurav on 3/11/2018.
 */

public class Common {
    public  static User currentuser;
    public  static  final  String DELETE="Delete";
    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return  "Placed";
        else if(status.equals("1"))
            return "On the way";
        else
            return "Shipped";


    }
}
