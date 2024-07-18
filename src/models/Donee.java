package models;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Zhe Heng
 */
public class Donee {

    private static int doneeIdCounter = 1001; // Starts from 1001
    private String doneeId;
    private String doneeIc;
    private String name;
    private int age; // Autogen referred to dob
    private Date dob;
    private String phoneNo;
    private String email;
    private char gender;
    private float receivedAmount;
    private char financialType; //B - B20, M - M40, T - T20, Z - Bankcrupt
    private char currentSituation; //
    private Date joinDate;
    private boolean activeStatus; //true = yes, false - no

    public Donee(String doneeIc, String name, Date dob, String phoneNo, String email, char gender, float receivedAmount, char financialType, char currentSituation, Date joinDate, boolean activeStatus) {
        this.doneeId = generateDonneeId();
        this.doneeIc = doneeIc;
        this.name = name;
        this.dob = dob;
        this.phoneNo = phoneNo;
        this.email = email;
        this.gender = gender;
        this.receivedAmount = receivedAmount;
        this.financialType = financialType;
        this.currentSituation = currentSituation;
        this.joinDate = joinDate;
        this.activeStatus = activeStatus;
        calAge(this.dob);

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

    public float getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(float receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public char getFinancialType() {
        return financialType;
    }

    public void setFinancialType(char financialType) {
        this.financialType = financialType;
    }

    public char getCurrentSituation() {
        return currentSituation;
    }

    public void setCurrentSituation(char currentSituation) {
        this.currentSituation = currentSituation;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    private String generateDonneeId() {
        synchronized (this) { // Thread safety for counter update
            String id = "AAA" + doneeIdCounter;
            doneeIdCounter++;
            return id;
        }
    }

    private void calAge(Date birth) {
        if (birth != null) {
            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(birth);

            Calendar todayCalendar = Calendar.getInstance();
            todayCalendar.setTimeInMillis(System.currentTimeMillis());

            int birthYear = birthCalendar.get(Calendar.YEAR);
            int currentYear = todayCalendar.get(Calendar.YEAR);
            this.age = currentYear - birthYear;

            // Optional: Handle months and days difference for more precise age
        } else {
            this.age = 0;
        }
    }
    
   

}
