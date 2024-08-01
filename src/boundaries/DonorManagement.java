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

    private static final LinkedList<Donor> donors = new LinkedList<>();

    public static void display() {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        try {
            Donor[] donorList = (Donor[]) (Common.retrieveObjectsFromFile(donors, "donors.dat"));
            if(donorList!=null)
            for (Donor donor : donorList) {
                donors.add(donor);
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
            switch (input) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = scanner.next();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    System.out.print("Enter gender: ");
                    char gender = scanner.next().charAt(0);
                    System.out.print("Enter date of birth: ");
                    String dateString = scanner.next();
                    Date dob = new Date(Integer.parseInt(dateString.split("-")[0]), Integer.parseInt(dateString.split("-")[1]), Integer.parseInt(dateString.split("-")[2]));
                    int currentNo = donors.size() + 1;
                    Donor donor = new Donor(Integer.toString(currentNo), name, age, new Date(), gender);
                    donors.add(donor);
                    System.out.println("Successfully added new donor with ID: " + currentNo + "\n");
                    break;
                case "2":
                    System.out.print("Enter ID: ");
                    String id = scanner.next();
                    Donor donorToBeRemoved = (Donor) donors.get(id, "getDonorID");
                    donors.remove(donorToBeRemoved);
                    System.out.println("Successfully removed donor with ID: " + id + "\n");

                    break;
                case "3":
                    System.out.print("Enter ID: ");
                    String idToBeUpdated = scanner.next();
                    Donor donorToBeUpdated = (Donor) donors.get(idToBeUpdated, "getDonorID");
                    donors.remove(donorToBeUpdated);

                    System.out.print("Enter name: ");
                    String name2 = scanner.next();
                    System.out.print("Enter age: ");
                    int age2 = scanner.nextInt();
                    System.out.print("Enter gender: ");
                    char gender2 = scanner.next().charAt(0);
                    System.out.print("Enter date of birth: ");
                    String dateString2 = scanner.next();
                    Date dob2 = new Date(Integer.parseInt(dateString2.split("-")[0]), Integer.parseInt(dateString2.split("-")[1]), Integer.parseInt(dateString2.split("-")[2]));
                    Donor donorObjToBeUpdated = new Donor(idToBeUpdated, name2, age2, new Date(), gender2);
                    donors.add(donorObjToBeUpdated);
                    System.out.println("Successfully updated donor with ID: " + idToBeUpdated + "\n");
                    break;
                case "4":
                    System.out.print("Enter ID: ");
                    String idToBeFound = scanner.next();
                    System.out.println(donors.get(idToBeFound, "getDonorID") + "\n");
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
                    try {
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
