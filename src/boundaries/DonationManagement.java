/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundaries;

import charity.*;
import controls.Common;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private static final char[] DonationTypeCode = {'F', 'C','S'};
    private static final LinkedList<Donation> donations = new LinkedList<>();

    
    public static void display(){
         Scanner scanner = new Scanner(System.in);
         boolean indicateFlag = true;
        while (indicateFlag) {
            System.out.println("\n" + divider);
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
                   donations.sort("getDoneeId");
                    try {

                        Common.writeObjectsToFile(donations, "donees.dat");
                    } catch (IOException ex) {
                        Logger.getLogger(DonationManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("");
                    indicateFlag = false;
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
            System.out.print("donate type(F-foods, C-cash, S-supplies): ");
            tempInput = scanner.next();
            validation = Common.charValidator(tempInput,DonationTypeCode);
        } while (!validation);
        String donateType = tempInput;
        
        do {
            System.out.print("Enter donation date (dd-MM-yyyy: ");
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
                        System.out.println("Canceled.");
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
        String tempInput;
        boolean validation = false;
        do {
            System.out.print("Enter donation ID to be amended (0 for cancel): ");
            String idInput = scanner.next();
            if (!"0".equals(idInput)){
                Donation donationToBeAmended = (Donation)donations.get(idInput, "getDonationId");
                if (donationToBeAmended != null) {
                    donations.remove(donationToBeAmended);

                    //Update donorId
                    System.out.print("Update donorID: ");
                    String newDonorId = scanner.next();
                    
                    //Update eventId
                    System.out.print("Update eventID: ");
                    String newEventId = scanner.next();
                    
                    //Update donote type
                    do {
                        System.out.print("Update donate type(F-foods, C-cash, S-supplies): ");
                        tempInput = scanner.next();
                        validation = Common.charValidator(tempInput,DonationTypeCode);
                    } while (!validation);
                    String newDonateType = tempInput;
                    
                    //Update donation date
                    do {
                        System.out.print("Update donation date (dd-MM-yyyy: ");
                        tempInput = scanner.next();
                        validation = Common.dateValidator(tempInput, 'M');
                    } while (!validation);
                    Date newDonationDate = new Date(Integer.parseInt(tempInput.split("-")[0]), Integer.parseInt(tempInput.split("-")[1]), Integer.parseInt(tempInput.split("-")[2]));
 
                    //Modify remark
                    System.out.print("Update remark: ");
                    String newRemark = scanner.next();

                    Donation updateDonation = new Donation(idInput,newDonorId, newEventId, newDonateType, newDonationDate, newRemark);
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
