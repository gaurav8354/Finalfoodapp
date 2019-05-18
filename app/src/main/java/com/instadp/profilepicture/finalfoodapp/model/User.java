package com.instadp.profilepicture.finalfoodapp.model;

/**
 * Created by gaurav on 7/12/2018.
 */

    public class User {
    private String Name;
    private String  Password;
    private String  Phone;
    private String  Email;
    private String  Otp;
    private String IsStaff;
    public User() {
    }

    public User(String name, String password, String email, String number,String otp) {
        Name = name;
        Password = password;
        Phone = number;
        Email = email;
        Otp=otp;
        IsStaff="false";

    }
    public String getIsStaff() {
        return IsStaff;
    }
    public void setIsStaff(String isStaff) {
        IsStaff = isStaff;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }
    public void setPhone(String phone) {
        Phone = phone;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
       Email= email;
    }
    public String getOtp() {
        return Otp;
    }
    public void setOtp(String otp) {
        Otp = otp;
    }
}










