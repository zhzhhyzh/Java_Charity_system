/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundaries;

import charity.*;
import java.util.Date;
import utils.LinkedList;
import java.util.Scanner;
import models.Donation;

/**
 *
 * @author quinton
 */
public class DonationManagement {
    
    private static final LinkedList<Donation> donations = new LinkedList<>();
    
    public static void display(){
         Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Add a new donation");
            System.out.println("2. Remove a donation");
            System.out.println("3. Amend donation details");
            System.out.println("4. Search donation details");
            System.out.println("5. List all donations");

            System.out.println("Hi, please enter yout choice:");
            String input = scanner.next();
            switch (input) {
                case "1":
                    addDonation();
                    break;
                    
                case "2":
                    
                    break;
                    
                case "3":
                    
                    break;
                    
                case "4":
                    
                    break;
                    
                case "5":
                    break;
                    
                    
                    
                case "0":
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }
    
    public static void addDonation(){
        System.out.print("donorID: ");
        Scanner scanner = new Scanner(System.in);
        String donorId = scanner.next();
        System.out.print("eventID: ");
        String eventId = scanner.next();
        System.out.print("donate type(food/cash): ");
        String donateType = scanner.next();
        System.out.print("Enter donation date: ");
        Date donationDate = new Date();//Date 
        System.out.print("remark: ");
        String remark = scanner.next();
        Donation donation = new Donation(donorId, eventId, donateType, donationDate, remark);
        donations.add(donation);
        System.out.println("Donation added successfully.");
    }
    
    public static void removeDonation(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter donation ID to be removed: ");
        String id = scanner.next();
        Donation donationToBeRemoved = (Donation)donations.get(id, "getDonationId");
        if (donationToBeRemoved != null) {
            donations.remove(donationToBeRemoved);
            System.out.println("Donation removed successfully.");
        }
        else {
            System.out.println("Donation not found.");
        }
    }
    
    public static void amendDonation(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter donation ID to be amended: ");
        String id = scanner.next();
        Donation donationToBeAmended = (Donation)donations.get(id, "getDonationId");
        if (donationToBeAmended != null) {
            donations.remove(donationToBeAmended);

            //Update donorId
            System.out.print("Update donorID: ");
            String donorId = scanner.next();
            //Update eventId
            System.out.print("Update eventID: ");
            String eventId = scanner.next();
            //Update donote type
            System.out.print("Update donate type(food/cash): ");
            String donateType = scanner.next();
            //Update donation date
            System.out.print("Update donation date: ");
            Date donationDate = new Date();
            //Modify remark
            System.out.print("Update remark: ");
            String remark = scanner.next();

            Donation updateDonation = new Donation(donorId, eventId, donateType, donationDate, remark);
            donations.add(updateDonation);
            System.out.println("Update Successfully.");
        }
        else {
            System.out.println("Donation not found.");
        }
    }
    
    public static void searchDonation(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter donation ID to be searched: ");
        String id = scanner.next();
        Donation donationToBeSearched = (Donation)donations.get(id,"getDonationId");
        if (donationToBeSearched != null) {
            System.out.println(donationToBeSearched);
        }
        else {
            System.out.println("Donation not found.");
        }
    }
        
    
    public static void listDonation(Donation[] donations){
        System.out.println("Donation Records: ");
        for (Donation donation: donations) {
            System.out.println(donation.getDonationId() + " - " +
                                donation.getDonorId() + " - " +  //should read donor name by donor id 
                                donation.getEventId() + " - " +  //should read event name by event id 
                                donation.getDonateType() + " - " +
                                donation.getDonationDate()
                            );
        }
        
    }
}
