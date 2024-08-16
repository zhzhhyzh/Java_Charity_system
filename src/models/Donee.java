package models;

import controls.Common;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
/**
 *
 * @author Zhe Heng
 */
public class Donee implements Serializable{

    private String doneeId;
    private String doneeIc;
    private String name;
    private int age; // Autogen referred to dob
    private Date dob;
    private String phoneNo;
    private String email;
    private char gender;
    private double receivedAmount;
    private char doneeType; //B - B20, M - M40, T - T20
    private String currentSituation; //
    private Date joinDate;
    private char activeStatus; //Y = yes, N - no

    public Donee(String doneeId,String doneeIc, String name, Date dob, String phoneNo, String email, char gender, double receivedAmount, char doneeType, String currentSituation, char activeStatus) {
        this.doneeId = doneeId;
        this.doneeIc = doneeIc;
        this.name = name;
        this.dob = dob;
        this.phoneNo = phoneNo;
        this.email = email;
        this.gender = gender;
        this.receivedAmount = receivedAmount;
        this.doneeType = doneeType;
        this.currentSituation = currentSituation;
        LocalDate localDate = LocalDate.now();
        this.joinDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        this.activeStatus = activeStatus;
        this.age = Common.calculateAge(this.dob);

    }

    public Donee(String doneeId, String doneeIc, String name, Date dob, String phoneNo, String email, char gender, double receivedAmount, char doneeType, String currentSituation, Date joinDate, char activeStatus) {
        this.doneeId = doneeId;
        this.doneeIc = doneeIc;
        this.name = name;
        this.dob = dob;
        this.phoneNo = phoneNo;
        this.email = email;
        this.gender = gender;
        this.receivedAmount = receivedAmount;
        this.doneeType = doneeType;
        this.currentSituation = currentSituation;
        this.joinDate = joinDate;
        this.activeStatus = activeStatus;
        this.age = Common.calculateAge(this.dob);

    }

    public String getDoneeIc() {
        return doneeIc;
    }

    public void setDoneeIc(String doneeIc) {
        this.doneeIc = doneeIc;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public double getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(double receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public char getDoneeType() {
        return doneeType;
    }

    public void setdoneeType(char doneeType) {
        this.doneeType = doneeType;
    }

    public String getCurrentSituation() {
        return currentSituation;
    }

    public void setCurrentSituation(String currentSituation) {
        this.currentSituation = currentSituation;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public char getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(char activeStatus) {
        this.activeStatus = activeStatus;
    }

    

    public String getDoneeId() {
        return doneeId;
    }

}
