/* Author: Quinton Pang Yi Xuan
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import controls.Common;
import java.io.Serializable;
import utils.LinkedList;
import java.util.Date;

/**
 *
 * @author quinton
 */
public class Donor implements Serializable {


    @Override
    public String toString() {
//        return "Donor{" + "donorID=" + donorID + ", name=" + name + ", age=" + age + ", dob=" + dob + ", gender=" + gender + '}';
        return "Donor:\nDonor ID: " + donorID + "\nName: " + name + "\nAge: " + age + "\nDate of Birth: " + getDobInString() + "\nGender: " + getGenderInString()+"\nDonation Amount: "+donationAmount;
    }

    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
    }

    public Donor(String donorID, String name, int age, Date dob, char gender) {
        this.donorID = donorID;
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.gender = gender;
        this.donationAmount = 0;
    }

     public Donor(String donorID, String name, int age, Date dob, char gender, double donationAmount) {
        this.donorID = donorID;
        this.name = name;
        this.age = age;
        this.dob = dob;
        this.gender = gender;
        this.donationAmount = donationAmount;
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
    public String getDobInString() {
        return Common.convertDateToString(dob);
    }
    public void setDob(Date dob) {
        this.dob = dob;
    }
    private String donorID;
    private String name;
    private int age;
    private Date dob;
    private char gender;
    private double donationAmount;

    public double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public char getGender() {
        return gender;
    }
    public String getGenderInString() {
        return gender=='M'?"Male":"Female";
    }
    public void setGender(char gender) {
        this.gender = gender;
    }
}
