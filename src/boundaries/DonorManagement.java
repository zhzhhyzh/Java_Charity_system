/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundaries;

import charity.*;
import controls.Common;
import java.io.File;
import java.io.FileOutputStream;
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
import models.Donor;
import utils.List;

/**
 *
 * @author quinton
 */
public class DonorManagement {

    public static  LinkedList<Donor> donors = new LinkedList<>();

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

            System.out.println("0. Exit");
            System.out.println("1. Add a new donor");
            System.out.println("2. Remove a donor");
            System.out.println("3. Update donor details");
            System.out.println("4. Search donor details");
            System.out.println("5. List all donors");

            System.out.print("Hi, please enter yout choice:");
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

                    int currentNo = Integer.parseInt(donors.get(donors.size()-1).getDonorID())+ 1;
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
                    Donor donorObjToBeUpdated = new Donor(idToBeUpdated, name2, age2, new Date(), gender2);
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
                    System.out.println("--------------------------------------------------------------------");

                    System.out.println("Donors: ");
                    for (Donor currentDonor : donors) {
                        System.out.println(currentDonor.getDonorID() + " - " + currentDonor.getName());
                    }
                    System.out.println("--------------------------------------------------------------------\n");

                    break;

                case "0": {
                    donors.sort("getDonorID");

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
}
