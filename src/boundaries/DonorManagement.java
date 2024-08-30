/* Author: Quinton Pang Yi Xuan
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundaries;

import static boundaries.DoneeManagement.DIVIDER;
import charity.*;
import controls.Common;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import utils.LinkedList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Donee;
import models.Donor;
import utils.List;

/**
 *
 * @author quinton
 */
public class DonorManagement {

    public static LinkedList<Donor> donors = new LinkedList<>();
    private static final int PAGE_SIZE = 10;
    private static int currentPage = 0;
    public static final String divider = "=======================================================";
    public static final String DIVIDER = "-------------------------------------------------------------------------------------------------------";

    public static void display() {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        try {
            // DEPENDS ON HOW YOU SAVE YOUR DATA IN DAT FILE
//            ArrayList<Donor> donorList = (ArrayList<Donor>) (Common.retrieveObjectsFromFile(donors, "donors.dat"));
            Object[] objArr = (Object[]) (Common.retrieveObjectsFromFile(donors, "donors.dat"));

            if (objArr != null) {
                Donor[] donorsArr = Arrays.copyOf(objArr, objArr.length, Donor[].class);

                for (Donor donor : donorsArr) {
                    donors.add(donor);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DonorManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (flag) {
//  donors.add(new Donor("2", "Quinton", 13, new Date(), 'M'));
//       donors.add(new Donor("3", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("4", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("5", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("6", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("7", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("8", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("9", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("10", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("11", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("12", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("13", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("14", "Quinton", 13, new Date(), 'M'));
//  donors.add(new Donor("15", "Quinton", 13, new Date(), 'M'));


      
      
   
   
   
   
            
            System.out.println("0. Exit");
            System.out.println("1. Add a new donor");
            System.out.println("2. Remove a donor");
            System.out.println("3. Update donor details");
            System.out.println("4. Search donor details");
            System.out.println("5. List all donors");

            System.out.print("Hi, please enter yout choice: ");
            String input = scanner.next();
            scanner.nextLine();  // Consume newline left-over

            switch (input) {
                case "1":
                    String tempInput;
                    boolean validation = false;

                    do {
                        System.out.print("Enter name: ");
                        tempInput = scanner.nextLine();
                        validation = true;
                    } while (!validation);
                    String name = tempInput;

                    do {
                        System.out.print("Enter age: ");
                        tempInput = scanner.next();
                        validation = Common.integerValidator(tempInput);
                    } while (!validation);
                    int age = Integer.parseInt(tempInput);

                    do {
                        System.out.print("Enter gender (M - Male, F - Female): ");
                        tempInput = scanner.next();
                        validation = Common.charValidator(tempInput, Common.GENDER_TYPE_CODE
                        );
                    } while (!validation);
                    char gender = tempInput.charAt(0);

                    do {
                        System.out.print("Enter date of birth: ");
                        tempInput = scanner.next();
                        validation = Common.dateValidator(tempInput, 'M'
                        );
                    } while (!validation);
                    String dateString = tempInput;
                    Date dob = new Date(Integer.parseInt(dateString.split("-")[0]), Integer.parseInt(dateString.split("-")[1]), Integer.parseInt(dateString.split("-")[2]));

                    LinkedList <Donor> tempDonors = donors;
                    tempDonors.sort("getDonorID",true);
//                    System.out.println(tempDonors.get(tempDonors.size() - 1));
                    int currentNo = 0; //default
                    if (tempDonors.size() > 0) {
                        currentNo = Integer.parseInt(tempDonors.get(tempDonors.size() - 1).getDonorID()) + 1;

                    }
                    Donor donor = new Donor(Integer.toString(currentNo), name, age, new Date(), gender);
                    donors.add(donor);
                    System.out.println("Successfully added new donor with ID: " + currentNo + "\n");
                    break;
                case "2":
                    System.out.print("Enter ID: ");
                    String id = scanner.next();
                    Donor donorToBeRemoved = (Donor) donors.get(id, "getDonorID");
                    if (donorToBeRemoved == null) {
                        System.out.println("Donor not found!\n");
                        break;
                    }
                    donors.remove(donorToBeRemoved);
                    System.out.println("Successfully removed donor with ID: " + id + "\n");

                    break;
                case "3":
                    System.out.print("Enter ID: ");
                    String idToBeUpdated = scanner.next();
                    Donor donorToBeUpdated = (Donor) donors.get(idToBeUpdated, "getDonorID");
                    double donationAmount2 = donorToBeUpdated.getDonationAmount();
                    if (donorToBeUpdated == null) {
                        System.out.println("Donor not found!\n");
                        break;
                    }
                    donors.remove(donorToBeUpdated);

                    String tempInput2;
                    boolean validation2 = false;

                    do {
                        System.out.print("Enter name: ");
                        tempInput2 = scanner.nextLine();
                        validation2 = true;
                    } while (!validation2);
                    String name2 = tempInput2;

                    do {
                        System.out.print("Enter age: ");
                        tempInput2 = scanner.next();
                        validation2 = Common.integerValidator(tempInput2);
                    } while (!validation2);
                    int age2 = Integer.parseInt(tempInput2);

                    do {
                        System.out.print("Enter gender (M - Male, F - Female): ");
                        tempInput2 = scanner.next();
                        validation2 = Common.charValidator(tempInput2, Common.GENDER_TYPE_CODE
                        );
                    } while (!validation2);
                    char gender2 = tempInput2.charAt(0);

                    do {
                        System.out.print("Enter date of birth: ");
                        tempInput2 = scanner.next();
                        validation2 = Common.dateValidator(tempInput2, 'M'
                        );
                    } while (!validation2);
                    String dateString2 = tempInput2;
                    Date dob2 = new Date(Integer.parseInt(dateString2.split("-")[0]), Integer.parseInt(dateString2.split("-")[1]), Integer.parseInt(dateString2.split("-")[2]));
                    Donor donorObjToBeUpdated = new Donor(idToBeUpdated, name2, age2, new Date(), gender2, donationAmount2);
                    donors.add(donorObjToBeUpdated);
                    System.out.println("Successfully updated donor with ID: " + idToBeUpdated + "\n");
                    break;
                case "4":
                    System.out.print("Enter ID: ");
                    String idToBeFound = scanner.next();
                    System.out.println("");

                    Donor donorToBeFound = (Donor) donors.get(idToBeFound, "getDonorID");
                    if (donorToBeFound == null) {
                        System.out.println("Donor not found!\n");
//                        break;
                    } else {
                        System.out.println(donorToBeFound + "\n");

                    }
                    break;
                case "5":
                    String option = list("", 0, 0, 0);

                    while (option.equals("3")) {
                        String tempInput3;
                        boolean validation3 = false;
                        int listAge = 0;
                        double donationAmountStart;
                        double donationAmountEnd;
                        String listSearch;
                        do {
                            System.out.print("Search Donee Id or Name (Empty to avoid filter): ");
                            tempInput3 = scanner.nextLine();
//                            if (!tempInput3.equals("\n")) {
////                                validation3 = Common.requiredField(tempInput3);
//                            } else {
                                validation3 = true;
//                            }
                        } while (!validation3);
                        listSearch = tempInput3;
                        do {
                            System.out.print("Age (Enter 0 to avoid filter): ");
                            tempInput3 = scanner.next();
                            if (!tempInput3.equals("0")) {
                                validation3 = Common.integerValidator(tempInput3);
                            } else {
                                validation3 = true;
                            }
                        } while (!validation3);
                        listAge = Integer.parseInt(tempInput3);
                        do {
                            System.out.print("Minimum donation amount (Enter 0 to avoid filter): ");
//                            System.out.print("Enter 0 to avoid filter on age: ");
                            tempInput3 = scanner.next();
                            if (!tempInput3.equals("0")) {
                                validation3 = Common.doubleValidator(tempInput3);
                            }
                        } while (!validation3);

                        donationAmountStart = Double.parseDouble(tempInput3);
                        do {
                            System.out.print("Maximum donation amount (Enter 0 to avoid filter): ");

//                            System.out.print("Enter 0 to avoid filter on age: ");
//                            scanner.nextLine();
                            tempInput3 = scanner.next();
                            if (!tempInput3.equals("0")) {
                                validation3 = Common.doubleValidator(tempInput3);
                            }
                        } while (!validation3);
                        donationAmountEnd = Double.parseDouble(tempInput3);

                        option = list(listSearch, listAge, donationAmountStart, donationAmountEnd);
                    }
                    System.out.println("");

                    break;

                case "0": {
                    donors.sort("getDonorID",true);

                    // THERE ARE TWO WAYS TO WRITE INTO FILE                    
                    // 1. WRITE AS ARRAYLIST
//                    Object[] objArr = donors.toArray();
//                    Donor[] donorsArr = Arrays.copyOf(objArr, objArr.length, Donor[].class);
//                    ArrayList<Donor> newList = new ArrayList<>();
//                    for (Donor donorElement : donorsArr) {
//                        newList.add(donorElement);
//                    }
                    try {
//                      Common.writeObjectsToFile(newList, "donors.dat");

                        // 2. PASS IN LINKED LSIT AND WRITE AS ARRAY
                        Common.writeObjectsToFile(donors, "donors.dat");
                    } catch (IOException ex) {
                        Logger.getLogger(DonorManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("");

                }
                flag = false;
                break;

                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    public static String list(String search, int age, double donationAmountStart, double donationAmountEnd) {
        LinkedList<Donor> filteredDonors = new LinkedList<>();

        for (Donor donor : donors) {
            boolean matches = true;

            if (!search.equals("") && (!donor.getName().contains(search) && !donor.getDonorID().contains(search))) {
                matches = false;
            }

            if (age != 0 && donor.getAge() != age) {
                matches = false;
            }

            if (donationAmountStart != 0 && donationAmountEnd != 0) {
                if (donor.getDonationAmount()<donationAmountStart || donor.getDonationAmount()>donationAmountEnd) {
                    matches = false;
                }
            }else if(donationAmountStart!=0){
                if (donor.getDonationAmount()<donationAmountStart) {
                    matches = false;
                }
            }else if(donationAmountEnd!=0){
                if (donor.getDonationAmount()>donationAmountEnd) {
                    matches = false;
                }
            }

            if (matches) {
                filteredDonors.add(donor);
            }
        }
        String input = "0";
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            int start = currentPage * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, filteredDonors.size());
            System.out.println(DIVIDER);
            System.out.printf("%-25s | %-20s | %-15s | %-15s | %-15s |\n", "Donor", "Age", "Gender", "Date of Birth", "Donation Amount");
            System.out.println(DIVIDER); 
            
            Donor[] newFilteredDonors = new Donor[end-start];
            
            System.arraycopy(filteredDonors.toArray(), start           , newFilteredDonors, 0     , end-start);

            for (Donor donor : newFilteredDonors) {
                
                String donorField = donor.getDonorID() + " - " + donor.getName();

                System.out.printf("%-25s | %-20d | %-15s | %-15s | %15s |%n",
                        donorField, donor.getAge(), donor.getGenderInString(), donor.getDobInString(), donor.getDonationAmount());
            }
            System.out.println(DIVIDER);
            System.out.println("Page " + (currentPage + 1) + " of " + ((filteredDonors.size() + PAGE_SIZE - 1) / PAGE_SIZE));
            System.out.println(
                    "Enter '1' for next page, '2' for previous page, '3' for list by criteria, '4' for generate report, '0' to back:");

            input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    if ((currentPage + 1) * PAGE_SIZE < filteredDonors.size()) {
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
                    generate(filteredDonors);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input, please enter '1', '2', '3', '4' or '0'");
                    break;
            }
        }

        // scanner.close();
        return input;
    }

    public static void generate(LinkedList<Donor> filteredDonors) {
        String baseDownloadPath = System.getProperty("user.home") + "/Downloads/donor_list.txt";
        int fileCount = 1;
        String downloadPath = baseDownloadPath;

        while (new File(downloadPath).exists()) {
            downloadPath = baseDownloadPath + "(" + fileCount++ + ").txt";
        }
        try (FileWriter writer = new FileWriter(new File(downloadPath))) {
            writer.write("DONOR NAME LIST\nTotal Donors: " + filteredDonors.size() + "\n");
            writer.write(DIVIDER + "\n");
            writer.write(String.format("%-30s | %-10s | %-15s | %-15s | %-20s |%n",
                        "Donor", "Age", "Gender", "Date of Birth", "Donation Amount"));
            writer.write(DIVIDER + "\n");
            for (Donor donor : filteredDonors) {
                String tempActiveStatus = " - ";

                String donorField = donor.getDonorID() + " - " + donor.getName();

                writer.write(String.format("%-30s | %-10d | %-15s | %-15s | %20s |%n",
                        donorField, donor.getAge(), donor.getGenderInString(), donor.getDobInString(), donor.getDonationAmount()));
            }
                        writer.write(DIVIDER + "\n");

            System.out.println("File successfully saved to " + downloadPath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }

}
