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
import java.util.Arrays;
import java.util.Date;
import utils.LinkedList;
import java.util.Scanner;
import models.Donation;
import models.Donee;
import models.Donor;

/**
 *
 * @author quinton
 */
public class DonationManagement {
    public static final String divider = "=======================================================";
    private static final char[] yesOrNoTypeCode = {'Y', 'N'};
    private static final char[] DonationTypeCode = {'F', 'C','S'};
    public static  LinkedList<Donation> donations = new LinkedList<>();
    public static  LinkedList<Donor> donors = new LinkedList<>();
    public static  LinkedList<Donee> donees = new LinkedList<>();
    
    public static void display(){
        try {
            Object[] objArr = (Object[]) (Common.retrieveObjectsFromFile(donations, "donations.dat"));

            if (objArr != null) {
                Donation[] donationsArr = Arrays.copyOf(objArr, objArr.length, Donation[].class);

                for (Donation donation : donationsArr) {
                    donations.add(donation);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DoneeManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Object[] objArr = (Object[]) (Common.retrieveObjectsFromFile(donors, "donors.dat"));

            if (objArr != null) {
                Donor[] donorsArr = Arrays.copyOf(objArr, objArr.length, Donor[].class);

                for (Donor donor : donorsArr) {
                    donors.add(donor);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DoneeManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Object[] objArr = (Object[]) (Common.retrieveObjectsFromFile(donees, "donees.dat"));

            if (objArr != null) {
                Donee[] doneesArr = Arrays.copyOf(objArr, objArr.length, Donee[].class);

                for (Donee donee : doneesArr) {
                    donees.add(donee);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DoneeManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                    System.out.print("Enter donation ID to be removed (0 for cancel): ");
                    String idInput = scanner.next();
                    if (!"0".equals(idInput)){
                           removeDonation(idInput);
                    }
                    scanner.nextLine();
                    break;
                    
                case "3":        
                    System.out.print("Enter donation ID to be amended (0 for cancel): ");
                    idInput = scanner.next();
                    if (!"0".equals(idInput)){
                        amendDonation(idInput);
                    }
                    scanner.nextLine();
                    break;

                case "4":
                    System.out.print("Enter donation ID to be amended (0 for cancel): ");
                    idInput = scanner.next();
                    if (!"0".equals(idInput)){
                        searchDonation(idInput);
                    }
                    scanner.nextLine();
                    break;
          
                case "5":
                    listDonation();
                    break;
                    
                case "0":
                   donations.sort("getDonationId");
                    try {
                        Common.writeObjectsToFile(donations, "donations.dat");
                    } catch (IOException ex) {
                        Logger.getLogger(DonationManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Common.writeObjectsToFile(donors, "donors.dat");
                    } catch (IOException ex) {
                        Logger.getLogger(DonorManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        Common.writeObjectsToFile(donees, "donees.dat");
                    } catch (IOException ex) {
                        Logger.getLogger(DoneeManagement.class.getName()).log(Level.SEVERE, null, ex);
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
        boolean validation = false;
        String tempInput;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("donor ID: ");
            tempInput = scanner.next();
            Donor donorDetail = (Donor) donors.get(tempInput, "getDonorID");
            if (donorDetail != null) {
                validation = true;
            } else {
                System.out.println("This donor ID does not exist.");
            }
        } while (!validation);
        String donorId = tempInput;
        
        do {
            System.out.print("donee ID: ");
            tempInput = scanner.next();
            Donee doneeDetail = (Donee) donees.get(tempInput, "getDoneeId");
            if (doneeDetail != null) {
                validation = true;
            } else {
                System.out.println("This donee ID does not exist.");
                validation = false;
            }
        } while (!validation);
        String doneeId = tempInput;
        
        
        do {
            System.out.print("eventID: ");
            tempInput = scanner.next();
            validation = Common.requiredField(tempInput);
        } while (!validation);
        String eventId = tempInput;
        
        
        do {
            System.out.print("donate type(F-foods, C-cash, S-supplies): ");
            tempInput = scanner.next();
            validation = Common.charValidator(tempInput,DonationTypeCode);
        } while (!validation);
        char donateType = Character.toUpperCase(tempInput.charAt(0));
        
        do {
            System.out.print("Enter donation date (dd-MM-yyyy): ");
            tempInput = scanner.next();
            validation = Common.dateValidator(tempInput, 'M');
        } while (!validation);
        Date donationDate = Common.structureForDateParsing(tempInput);
 
        System.out.print("remark: ");
        scanner.nextLine();
        String remark = scanner.nextLine();
        
        int tempDonationId = 1001;
        String donationId = "";
        for (int i = 1001 ; i == tempDonationId ; i++){
            Donation donationDetail = (Donation)donations.get(Integer.toString(i),"getDonationId");
            if (donationDetail != null) {
                tempDonationId++;
            } else {
                donationId = Integer.toString(i);
            }
        }
        int currentNo = donations.size() + 1;
        Donation donation = new Donation(donationId, donorId, doneeId, eventId, donateType, donationDate, remark);
        donations.add(donation);
        System.out.println("Donation " + donation.getDonationId() + " Recorded.");
    }
    
    public static void removeDonation(String idInput){
        Scanner scanner = new Scanner(System.in);
        boolean validation;
        do {
            Donation donationToBeRemoved = (Donation)donations.get(idInput, "getDonationId");
            if (donationToBeRemoved != null) {  
                System.out.println("Are you sure to remove" + idInput + "?");
                System.out.print("Enter choices ( Y - yes, N - no): ");
                String tempInput = scanner.next();
                validation = Common.charValidator(tempInput, yesOrNoTypeCode);

                if (tempInput.equalsIgnoreCase("Y")) {
                    donations.remove(donationToBeRemoved);
                    System.out.println("Donation Record Removed.");
                }
                else {
                    System.out.println("Canceled.");
                    break;
                }
            }
            else {
                System.out.println("Donation not found.");
                break;
            }
        }while (!validation);
    }
    
    public static void amendDonation(String idInput){
        Scanner scanner = new Scanner(System.in);
        String tempInput;
        boolean validation = false;
                Donation donationToBeAmended = (Donation)donations.get(idInput, "getDonationId");
                if (donationToBeAmended != null) {
                    donations.remove(donationToBeAmended);

                    //Update donorId
                    do {
                        System.out.print("Update donorID: ");
                        tempInput = scanner.next();
                        Donor donorDetail = (Donor) donors.get(tempInput, "getDonorID");
                        if (donorDetail != null) {
                            validation = true;
                        } else {
                            System.out.println("This donor ID does not exist.");
                        }
                    } while (!validation);
                    String newDonorId = tempInput;
                    
                    //Update doneeId
                    do {
                        System.out.print("Update donee ID: ");
                        tempInput = scanner.next();
                        Donee doneeDetail = (Donee) donees.get(tempInput, "getDoneeId");
                        if (doneeDetail != null) {
                            validation = true;
                        } else {
                            System.out.println("This donee ID does not exist.");
                            validation = false;
                        }
                    } while (!validation);
                    String newDoneeId = tempInput;
                    
                    //Update eventId
                    do {
                        System.out.print("Update eventID: ");
                        tempInput = scanner.next();
                        validation = Common.requiredField(tempInput);
                    } while (!validation);
                    String newEventId = tempInput;
                    
                    //Update donote type
                    do {
                        System.out.print("Update donate type(F-foods, C-cash, S-supplies): ");
                        tempInput = scanner.next();
                        validation = Common.charValidator(tempInput,DonationTypeCode);
                    } while (!validation);
                    char newDonateType = Character.toUpperCase(tempInput.charAt(0));
                    
                    //Update donation date
                    do {
                        System.out.print("Update donation date (dd-MM-yyyy): ");
                        tempInput = scanner.next();
                        validation = Common.dateValidator(tempInput, 'M');
                    } while (!validation);
                    Date newDonationDate = Common.structureForDateParsing(tempInput);
                    
                    //Modify remark
                    System.out.print("Update remark: ");
                    scanner.nextLine();
                    String newRemark = scanner.nextLine();

                    Donation updateDonation = new Donation(idInput,newDonorId, newDoneeId, newEventId, newDonateType, newDonationDate, newRemark);
                    donations.add(updateDonation);
                    System.out.println("Donation Record Updated.");
                }
                else {
                    System.out.println("Donation not found.");
                }
            }
    
    
    public static void searchDonation(String idInput){
        Donation donationToBeSearched = (Donation)donations.get(idInput,"getDonationId");
        if (donationToBeSearched != null) {
            System.out.println("Donation ID: " + donationToBeSearched.getDonationId() + "\n" +
                                "Donor: " + donationToBeSearched.getDonorId() + "\n" +
                                "Donee: " + donationToBeSearched.getDoneeId() + "\n" +
                                "Event: " + donationToBeSearched.getEventId() + "\n" +
                                "Donation Type: " + donationToBeSearched.getDonateType() + "\n" +
                                "Donation Date: " + Common.convertDateToString(donationToBeSearched.getDonationDate()) + "\n" +
                                "Remark: " + donationToBeSearched.getRemark()
                    );
        }
        else {
            System.out.println("Donation not found.");
        }
    }
    
    public static void listDonation(){
        System.out.println("Donation Records: ");
        for (Donation donation: donations) {
            String donorName = "";
            String doneeName = "";
            
            String tempType = "-";
            switch (donation.getDonateType()) {
                case 'F':
                    tempType = "Foods";
                    break;
                case 'C':
                    tempType = "Cash";
                    break;
                case 'S':
                    tempType = "Supplies";
                    break;
            }
                  
            for (Donee donee:donees){
                if (donee.getDoneeId().equals(donation.getDoneeId())){
                    doneeName = donee.getName();
                    break;
                }
            }
            
            for (Donor donor:donors){
                if (donor.getDonorID().equals(donation.getDonorId())){
                    donorName = donor.getName();
                    break;
                }
            }
            
            System.out.println(donation.getDonationId() + " - " +
                                donorName + " - " +  //should read donor name by donor id
                                doneeName + " - " +
                                donation.getEventId() + " - " +  //should read event name by event id 
                                tempType + " - " +
                                Common.convertDateToString(donation.getDonationDate())
                            );
        }
        
    }
}
