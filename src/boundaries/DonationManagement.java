/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundaries;

import charity.*;
import controls.Common;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import utils.LinkedList;
import java.util.Scanner;
import models.Donation;

/**
 *
 * @author quinton
 */
public class DonationManagement {
    public static final String divider = "=======================================================";
    private static final char[] yesOrNoTypeCode = {'Y', 'N'};
    private static final LinkedList<Donation> donations = new LinkedList<>();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    public static void display(){
         Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Donation Management");
            System.out.println(divider + "\n");
            System.out.println("1. Add a new donation");
            System.out.println("2. Remove a donation");
            System.out.println("3. Amend donation details");
            System.out.println("4. Search donation details");
            System.out.println("5. List all donations");
            System.out.println("0. Exit");

            System.out.print("Please enter yout choice:");
            String input = scanner.next();
            System.out.println(divider);
            switch (input) {
                case "1":
                    addDonation();
                    break;
                    
                case "2":
                    removeDonation();
                    break;
                    
                case "3":
                    amendDonation();
                    break;
                    
                case "4":
                    searchDonation();
                    break;
                    
                case "5":
                    listDonation();
                    break;
                    
                case "0":
                    break;
                    
                default:
                    System.out.println("Invalid input!");
            }
        }
    }
    
    public static void addDonation(){
        String tempInput;
        Scanner scanner = new Scanner(System.in);

        System.out.print("donorID: ");
        String donorId = scanner.next();
        
        System.out.print("eventID: ");
        String eventId = scanner.next();
        
        boolean validation = false; 
        do {
            System.out.print("donate type(foods/cash/supplies): ");
            tempInput = scanner.next();
            validation = Common.DonationTypeValidator(tempInput);
        } while (!validation);
        String donateType = tempInput;
        
        do {
            System.out.print("Enter donation date (yyyy-MM-dd): ");
            tempInput = scanner.next();
            validation = Common.dateValidator(tempInput, 'M');
        } while (!validation);
        Date donationDate = new Date(Integer.parseInt(tempInput.split("-")[0]), Integer.parseInt(tempInput.split("-")[1]), Integer.parseInt(tempInput.split("-")[2]));
 
        System.out.print("remark: ");
        String remark = scanner.next();
        
        Donation donation = new Donation(donorId, eventId, donateType, donationDate, remark);
        donations.add(donation);
        System.out.println("Donation Recorded.");
    }
    
    public static void removeDonation(){
        Scanner scanner = new Scanner(System.in);
        boolean validation = false;
        do {
            System.out.print("Enter donation ID to be removed (0 for cancel): ");
            String idInput = scanner.next();
            if (!"0".equals(idInput)){
                Donation donationToBeRemoved = (Donation)donations.get(idInput, "getDonationId");
                if (donationToBeRemoved != null) {  
                    System.out.println("Are you sure to remove" + idInput + "?");
                    System.out.print("Enter choices ( Y - yes, N - no): ");
                    String tempInput = scanner.next();
                    validation = Common.charValidator(tempInput, yesOrNoTypeCode);

                    if (tempInput.equals("Y")) {
                        donations.remove(donationToBeRemoved);
                        System.out.println("Donation removed successfully.");
                    }
                    else {
                        break;
                    }
                }
                else {
                    System.out.println("Donation not found.");
                }
            }
            else { 
                break;
            }
        } while (!validation);
    }
    
    public static void amendDonation(){
        Scanner scanner = new Scanner(System.in);
        boolean validation = false;
        do {
            System.out.print("Enter donation ID to be amended (0 for cancel): ");
            String idInput = scanner.next();
            if (!"0".equals(idInput)){
                Donation donationToBeAmended = (Donation)donations.get(AmendedId, "getDonationId");
                if (donationToBeAmended != null) {
                    donations.remove(donationToBeAmended);

                    //Update donorId
                    System.out.print("Update donorID: ");
                    String newDonorId = scanner.next();
                    //Update eventId
                    System.out.print("Update eventID: ");
                    String newEventId = scanner.next();
                    //Update donote type
                    System.out.print("Update donate type(food/cash): ");
                    String newDonateType = scanner.next();
                    //Update donation date
                    System.out.print("Update donation date (yyyy-MM-dd): ");
                    String dateInput = scanner.next();
                    Date newDonationDate;
                    try {
                        newDonationDate = dateFormat.parse(dateInput);
                    } catch (ParseException e) {
                        System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                        return;
                    }
                    //Modify remark
                    System.out.print("Update remark: ");
                    String newRemark = scanner.next();

                    Donation updateDonation = new Donation(AmendedId,newDonorId, newEventId, newDonateType, newDonationDate, newRemark);
                    donations.add(updateDonation);
                    System.out.println("Update Successfully.");
                }
                else {
                    System.out.println("Donation not found.");
                }
            }
            else {
                break;
            }
        } while(!validation);
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
        
    
    public static void listDonation(){
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
