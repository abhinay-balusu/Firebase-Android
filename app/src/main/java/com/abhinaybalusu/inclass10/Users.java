package com.abhinaybalusu.inclass10;

/**
 * Created by abhinaybalusu on 11/7/16.
 */
public class Users {

    String email, fullname = "";

    public Users(String email, String password, String fullname) {
        this.email = email;
        this.fullname = fullname;
    }
    public Users()
    {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
