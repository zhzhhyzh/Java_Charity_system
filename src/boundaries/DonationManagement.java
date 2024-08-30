/* Author: Loo Wai Kit
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundaries;

import controls.Common;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Donation;
import models.Donee;
import models.Donor;
import utils.LinkedList;

/**
 *
 * @author WaiKit
 */
public class DonationManagement {
    private static final int PAGE_SIZE = 20;
    private static int currentPage = 0;
    public static final String divider = "=======================================================";
    public static final String divider2 = "-------------------------------------------------------";
    private static final char[] yesOrNoTypeCode = {'Y', 'N'};
    private static final char[] DonationTypeCode = {'F', 'C','S'};
    public static LinkedList<Donation> donations = new LinkedList<>();
    public static LinkedList<Donor> donors = new LinkedList<>();
    public static LinkedList<Donee> donees = new LinkedList<>();

    
    
    public static void display(){
        
        Common.loadData("donations.dat", donations, Donation[].class, DonationManagement.class);
        Common.loadData("donors.dat", donors, Donor[].class,DonorManagement.class);
        Common.loadData("donees.dat", donees, Donee[].class,DoneeManagement.class);

//        try {
//            Object[] objArr = (Object[]) (Common.retrieveObjectsFromFile(donations, "donations.dat"));
//
//            if (objArr != null) {
//                Donation[] donationsArr = Arrays.copyOf(objArr, objArr.length, Donation[].class);
//
//                for (Donation donation : donationsArr) {
//                    donations.add(donation);
//                }
//            }
//        } catch (IOException | ClassNotFoundException ex) {
//            Logger.getLogger(DoneeManagement.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            Object[] objArr = (Object[]) (Common.retrieveObjectsFromFile(donors, "donors.dat"));
//
//            if (objArr != null) {
//                Donor[] donorsArr = Arrays.copyOf(objArr, objArr.length, Donor[].class);
//
//                for (Donor donor : donorsArr) {
//                    donors.add(donor);
//                }
//            }
//        } catch (IOException | ClassNotFoundException ex) {
//            Logger.getLogger(DoneeManagement.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        try {
//            Object[] objArr = (Object[]) (Common.retrieveObjectsFromFile(donees, "donees.dat"));
//
//            if (objArr != null) {
//                Donee[] doneesArr = Arrays.copyOf(objArr, objArr.length, Donee[].class);
//
//                for (Donee donee : doneesArr) {
//                    donees.add(donee);
//                }
//            }
//        } catch (IOException | ClassNotFoundException ex) {
//            Logger.getLogger(DoneeManagement.class.getName()).log(Level.SEVERE, null, ex);
//        }


         Scanner scanner = new Scanner(System.in);
         boolean indicateFlag = true;
         String idInput;
        while (indicateFlag) {
            System.out.println("\n" + divider);
            System.out.println("Donation Management");
            System.out.println(divider);
            System.out.println("1. Add a new donation");
            System.out.println("2. Remove a donation");
            System.out.println("3. Amend donation details");
            System.out.println("4. Search donation details");
            System.out.println("5. List all donations");
            System.out.println("6. Track donated Items");
            System.out.println("0. Exit");

            System.out.print("Please enter yout choice:");
            String input = scanner.nextLine();
            System.out.println(divider2);
            switch (input) {
                case "1":
                    addDonation();
                    break;
                    
                case "2":
                    System.out.print("Enter donation ID to be removed (0 for cancel): ");
                    idInput = scanner.next();
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
                    System.out.print("Enter donation ID to be searched (0 for cancel): ");
                    idInput = scanner.next();
                    if (!"0".equals(idInput)){
                        searchDonation(idInput);
                    }
                    scanner.nextLine();
                    break;
          
                case "5":
                    String option = listDonation("",' ');
                    
                    while (option.equals("3")) {
                        String tempInput;
                        boolean validation = false;
                        String listByDonor;
                        char listByType;
                        
                        do {
                            System.out.print("Enter donor ID to list his donation (type '0' to skip):");
                            tempInput = scanner.nextLine();
                            if (!tempInput.equals("0")) {
                                Donor donorDetail = (Donor) donors.get(tempInput, "getDonorID");
                                if (donorDetail != null) {
                                    validation = true;
                                } else {
                                    System.out.println("Donor not found.");
                                }
                            }
                            else {
                                validation = true;
                                tempInput = "";
                            }
                        } while (!validation);
                        listByDonor = tempInput;
                        
                        do {
                            System.out.print("Enter filter criteria (type 'F' for foods, 'C' for cash, 'S' for supplies, '0' to skip): ");
                            tempInput = scanner.nextLine();
                            if (!tempInput.equals("0")) {
                                validation = Common.charValidator(tempInput, DonationTypeCode);
                            } 
                        } while (!validation);
                        if (!tempInput.equals("0")){
                            listByType = Character.toUpperCase(tempInput.charAt(0));
                        } else {
                            listByType = ' ';
                        }
                        
                        option = listDonation(listByDonor, listByType);
                    }
                    break;
                    
                    
                case "6":
                    trackDonatedItems();
                    break;
                    
                case "0":
                    donations.sort("getDonationId",false);
                    try {
                        Common.writeObjectsToFile(donations, "donations.dat");
                        Common.writeObjectsToFile(donors, "donors.dat");
                        Common.writeObjectsToFile(donees, "donees.dat");
                    } catch (IOException ex) {
                        Logger.getLogger(DonationManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
            } else if (tempInput.equals("0")){
                break;
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
            } else if (tempInput.equals("0")){
                break;
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
            System.out.print("Estimate value of donation: RM");
            tempInput = scanner.next();
            validation = Common.integerValidator(tempInput);
        } while (!validation);
        double estValue = Common.DoubleFormatter(Double.parseDouble(tempInput));
        
        do {
            System.out.print("Enter donation date (dd-MM-yyyy): ");
            tempInput = scanner.next();
            validation = Common.dateValidator(tempInput, 'M');
        } while (!validation);
        Date donationDate = Common.structureForDateParsing(tempInput);
 
        System.out.print("remark: ");
        scanner.nextLine();
        String remark = scanner.nextLine();
        
        int currentId = 1000;
        for (Donation donation : donations) {
            String donationId = donation.getDonationId();

            // Skip empty or null records
            if (donationId == null || donationId.isEmpty()) {
                continue;
            }
            // Extract the numeric part of the donation ID by removing the "D" prefix
            int donationNumericId = Integer.parseInt(donation.getDonationId().substring(1));
            // Update currentId if this donation ID is greater than the currentId
            if (donationNumericId > currentId) {
                currentId = donationNumericId;
            }
        }
        currentId++;
        String donationId = "D" + currentId;
        
        
            
        
        int currentNo = donations.size() + 1;
        Donation donation = new Donation(donationId, donorId, doneeId, eventId, donateType, estValue, donationDate, remark);
        donations.add(donation);
        System.out.println("Donation " + donation.getDonationId() + " Recorded.");
        
        
        //Sum up the received amount of the donee 
        Donee donee = (Donee) donees.get(doneeId, "getDoneeId");
        double AccValue = donee.getReceivedAmount() + estValue;
        Donee doneeUpdated = new Donee(doneeId, donee.getDoneeIc(), donee.getName(), donee.getDob(), donee.getPhoneNo(), donee.getEmail(),
                                        donee.getGender(), AccValue, donee.getDoneeType(), donee.getCurrentSituation(),
                                        donee.getJoinDate(), donee.getActiveStatus(), donee.getRepName());
        donees.remove(donee);
        donees.add(doneeUpdated);
        
        Donor donor = (Donor) donors.get(donorId, "getDonorID");
        AccValue = donor.getDonationAmount() + estValue;
        Donor donorUpdated = new Donor(donorId, donor.getName(), donor.getAge(), donor.getDob(), donor.getGender(), AccValue);
        donors.remove(donor);
        donors.add(donorUpdated);
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
                    
                    //Update Estimate Value
                    do {
                        System.out.print("Update estimate value of donation: RM");
                        tempInput = scanner.next();
                        validation = Common.integerValidator(tempInput);
                    } while (!validation);
                    double newEstValue = Common.DoubleFormatter(Double.parseDouble(tempInput));
                    
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

                    Donation updateDonation = new Donation(idInput,newDonorId, newDoneeId, newEventId, newDonateType, newEstValue, newDonationDate, newRemark);
                    donations.add(updateDonation);
                    System.out.println("Donation Record Updated.");
                }
                else {
                    System.out.println("Donation not found.");
                }
            }
    
    
    public static void searchDonation(String idInput){
        Donation donationToBeSearched = (Donation)donations.get(idInput,"getDonationId");
        Donor donor = (Donor) donors.get(donationToBeSearched.getDonorId(),"getDonorID");
        Donee donee = (Donee) donees.get(donationToBeSearched.getDoneeId(),"getDoneeId");
        if (donationToBeSearched != null) {
            System.out.println("Donation ID: " + donationToBeSearched.getDonationId() + "\n" +
                                "Donor: " + donationToBeSearched.getDonorId() + " - " + donor.getName() + "\n" +
                                "Donee: " + donationToBeSearched.getDoneeId() + " - " + donee.getName() + "\n" +
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
    
    public static String listDonation(String donorId, char type){
        LinkedList<Donation> filteredDonations = new LinkedList<>();
        
        for (Donation donation : donations) {
            boolean match = true;
            
            if ((!donorId.equals("")) && (!donation.getDonorId().equals(donorId ))) {
                match = false;
            }
            if (type != ' ' && donation.getDonateType() != type) {
                match = false;
            }
            
            if (match) {
                filteredDonations.add(donation);
            }
        }
        
        System.out.println("Donation Records: ");
        
        String input = "0";
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {
            int start = currentPage * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, donations.size());
            System.out.println(divider2 + divider2);
            System.out.printf("%-5s | %-20s | %-20s | %-15s | %-10s | %-10s | %-10s%n" ,
                    "ID", "Donor", "Donee", "Event", "Type","Value(RM)", "Date");
            System.out.println(divider2 + divider2);    
            
            for (Donation donation: filteredDonations) {
                String donorName = "";
                String doneeName = "";
                //String eventName = "";

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

//                 for (Event event:events){
//                     if (event.getEventId().equals(donation.getEventId())){
//                         eventName = event.getEventName();
//                         break;
//                     }
//                }

//                System.out.println(donation.getDonationId() + " - " +
//                                    donorName + " - " +  
//                                    doneeName + " - " +
//                                    donation.getEventId() + " - " +  //should read event name by event id 
//                                    tempType + " - " +
//                                    Common.convertDateToString(donation.getDonationDate())
//                                );
                System.out.printf("%-5s | %-20s | %-20s | %-15s | %-10s | %-10s | %-10s%n" ,
                        donation.getDonationId(),
                        donorName,
                        doneeName,
                        donation.getEventId(),
                        tempType,
                        donation.getEstValue(),
                        Common.convertDateToString(donation.getDonationDate()));
            }
            

            
            System.out.println(divider2 + divider2);
            System.out.println("Page " + (currentPage + 1) + " of " + ((donations.size() + PAGE_SIZE - 1) / PAGE_SIZE));
            System.out.print("Enter '1' for next page, '2' for previous page, '3' for list by criteria, '4' for generate report, '0' to back: ");

            input = scanner.nextLine().trim();

                switch (input) {
                    case "1":
                        if ((currentPage + 1) * PAGE_SIZE < donations.size()) {
                            currentPage++;
                        } else {
                            System.out.println("You are already on the last page.");
                        }
                        break;
                        
                    case "2":
                        if (currentPage > 0) {
                            currentPage--;
                        } else {
                            System.out.println("You are already on the first page.");
                        }
                        break;
                        
                    case "3":
                        running = false;
                        break;
                        
                    case "4":
                        generateSummaryReport();
                        break;
                        
                    case "0":
                        running = false;
                        break;
                        
                    default:
                        System.out.println("Invalid input, please enter '1', '2' or '0'");
                        break;
                }
            }   
        return input;
    }
    
    
    public static void trackDonatedItems() {
        System.out.println("Tracking Donated Items by Category:");

        // Initialize counters
        int foodCount = 0;
        int cashCount = 0;
        int suppliesCount = 0;
        int foodValue = 0;
        int cashValue = 0;
        int suppliesValue = 0;

        // Count donations based on type
        for (Donation donation : donations) {
            switch (donation.getDonateType()) {
                case 'F':
                    foodCount++;
                    foodValue += donation.getEstValue();
                    break;
                case 'C':
                    cashCount++;
                    cashValue += donation.getEstValue();
                    break;
                case 'S':
                    suppliesCount++;
                    suppliesValue += donation.getEstValue();
                    break;
                default:
                    System.out.println("Unknown donation type: " + donation.getDonateType());
            }
        }

        // Display counts
        System.out.println("Foods: " + foodCount + "(estimated RM " + foodValue + ")");
        System.out.println("Cash: " + cashCount + "(estimated RM " + cashValue + ")");
        System.out.println("Supplies: " + suppliesCount + "(estimated RM " + suppliesValue + ")");
    }
    
    
    
    public static void generateSummaryReport() {
//        int totalDonations = donations.size();
//        int totalDonors = (int) donors.stream().count();
//        int totalDonees = (int) donees.stream().count();
//
//        System.out.println("Summary Report:");
//     x`   System.out.println("Total Donations: " + totalDonations);
//        System.out.println("Total Donors: " + totalDonors);
//        System.out.println("Total Donees: " + totalDonees);

        // Further summary can be added here, like donations per donor, donations per donee, etc.
    }
}
