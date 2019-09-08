package com.example.secondassignment2;

import java.io.Serializable;

public class User implements Serializable {

    String gender;
    String fullName;
    String exactLocation;

    public User(String fullName, String gender, String exactLocation) {
        this.gender = gender;
        this.fullName=fullName;
        this.exactLocation = exactLocation;
    }
    //public void Update(String fullName, String gender, String exactLocation){
    //    this.gender = gender;
    //    this.fullName=fullName;
    //    this.exactLocation = exactLocation;
    //}

    @Override
    public String toString() {
        return "User{" + gender + '\'' + fullName + exactLocation + '\'' +
                '}';
    }


}
