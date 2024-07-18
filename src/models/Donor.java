/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import utils.LinkedList;
import java.util.Date;

/**
 *
 * @author quinton
 */
public class Donor implements Serializable{

    @Override
    public String toString() {
        return "Donor{" + "donorID=" + donorID + ", name=" + name + ", age=" + age + ", dob=" + dob + ", gender=" + gender + '}';
    }

    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
    }

    
    public Donor(String donorID,String name, int age, Date dob, char gender) {
        this.donorID = donorID;
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }
    private String donorID;
    private String name;
    private int age;
    private Date dob;
    private char gender;

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}
