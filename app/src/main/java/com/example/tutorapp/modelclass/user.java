package com.example.tutorapp.modelclass;

public class user {
    String name,type,reg_no;
    public user(String u_name,String u_type,String u_roll){
        name=u_name;
        type=u_type;
        reg_no=u_roll;
    }
    public String gettype(){
        return type;
    }
    public String getName(){
        return name;
    }
    public String getReg_no() {
        return reg_no;
    }
}
