package com.example.policebureauapp;

import java.util.ArrayList;

public class PoliceStation {
    private String Pid, Password, SpecialPassword, nameOfSo, PhoneNumber;

    public PoliceStation() {

    }

    public PoliceStation(String pid, String password, String special_password, String name_of_SO, String phoneNumber) {
        this.Pid = pid;
        this.Password = password;
        this.SpecialPassword = special_password;
        this.nameOfSo = name_of_SO;
        this.PhoneNumber = phoneNumber;
    }

    public String getSpecialPassword() {
        return SpecialPassword;
    }
    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        this.Pid = pid;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getNameOfSo() {
        return nameOfSo;
    }

    public void setName_of_SO(String name_of_SO) {
        this.nameOfSo = name_of_SO;
    }
}
