/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.Date;

public class Donation implements Serializable {
    
    

    private String donationId;
    private String donorId;
    private String doneeId;
    private String eventId;
    private char donateType;  //F-foods, C-cash, S-supplies
    private Date donationDate;
    private String remark;
    public String get;

    


        public Donation(String donationId, String donorId, String doneeId, String eventId, char donateType, Date donationDate, String remark) {
        this.donationId = donationId;
        this.donorId = donorId;
        this.doneeId = doneeId;
        this.eventId = eventId;
        this.donateType = donateType;
        this.donationDate = donationDate;
        this.remark = remark;
    }

    public String getDonationId() {
        return donationId;
    }


    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public String getDoneeId() {
        return doneeId;
    }

    public void setDoneeId(String doneeId) {
        this.doneeId = doneeId;
    }

    public char getDonateType() {
        return donateType;
    }

    public void setDonateType(char donateType) {
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
    
}
