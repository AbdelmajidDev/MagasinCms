package com.example.magasin_cms.Model;

public class UserModel {

    String Fname , Email , Department,Phone;

    public UserModel() {
    }

    public UserModel(String fname, String email, String department,String phone) {
        Fname = fname;
        Phone=phone;
        Email = email;
        Department = department;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
