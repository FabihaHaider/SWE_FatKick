package com.example.fatkick.subsystem.authenticator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class User {
    private String name;
    private String DOB;
    private double weight;
    private double height;
    private String gender;
    private String email;
    private String password;

    public User(String name, String DOB, double weight, double height, String gender, String email) {
        this.name = name;
        this.DOB = DOB;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.email = email;
    }


    public User(){}
    private boolean isValid()
    {
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public long calculateAge() throws ParseException {
        Date currentDate = new Date();
        Date birthdate = (new SimpleDateFormat("MM/dd/yy")).parse(DOB);
        Long diff = currentDate.getTime()- birthdate.getTime();
        long age = (diff / (1000l * 60 * 60 * 24 * 365));

        return  age;


    }
}
