/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.Date;

public class Donation {
    
    
    private static int counter = 0;
    private String donationId;
    private String donorId;
    private String eventId;
    private String donateType;
    private Date donationDate;
    private String remark;
    // accumulateValue
    // itemCount
    // private String picId;
    

    public Donation() {
    }
    
    public Donation(String donorId, String donateType, Date donationDate, String remark, String eventId) {
        this.donationId = generateDonationId();
        this.donorId = donorId;
        this.donateType = donateType;
        this.donationDate = new Date();
        this.remark = remark;
        this.eventId = eventId;
    }

    public String getDonationId() {
        return donationId;
    }

    public void setDonationId(String donationId) {
        this.donationId = donationId;
    }

    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getDonateType() {
        return donateType;
    }

    public void setDonateType(String donateType) {
        this.donateType = donateType;
    }

    public Date getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Date donationDate) {
        this.donationDate = donationDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    
    
    public static String generateDonationId() {
        String id = "DDD" + ++counter;
        return id;
    }
    
    @Override
    public String toString(){
        return "Donation{" +
                "donationID=" + donationId +
                "donorID=" + donorId + 
                "eventId" + eventId +
                "donateType=" + donateType +
                "donationDate=" + donationDate + 
                "remark" + remark +
                "}";
    }
}
