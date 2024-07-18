/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundaries;

import charity.*;
import utils.LinkedList;
import java.util.Date;
import java.util.Scanner;
import models.Donor;

/**
 *
 * @author quinton
 */
public class DonorManagement {

    private static final LinkedList<Donor> donors = new LinkedList<>();

    public static void display() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Add a new donor");
            System.out.println("2. Remove a donor");
            System.out.println("3. Update donor details");
            System.out.println("4. Search donor details");
            System.out.println("5. List all donors");

            System.out.println("Hi, please enter yout choice:");
            String input = scanner.next();
            switch (input) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = scanner.next();
                    System.out.println("Enter age: ");
                    int age = scanner.nextInt();
                    System.out.println("Enter gender: ");
                    char gender = scanner.next().charAt(0);
                    System.out.println("Enter date of birth: ");
                    Date dob = new Date(Integer.parseInt(scanner.next().split("-")[0]), Integer.parseInt(scanner.next().split("-")[1]), Integer.parseInt(scanner.next().split("-")[2]));
                    int currentNo = donors.size() + 1;
                    Donor donor = new Donor(Integer.toString(currentNo), name, age, new Date(), gender);
                    donors.add(donor);
                    break;
                case "2":
                    System.out.println("Enter ID: ");
                    String id = scanner.next();
                    Donor donorToBeRemoved = (Donor) donors.get(id, "getDonorID");
                    donors.remove(donorToBeRemoved);
                    break;
                case "3":
                    System.out.println("Enter ID: ");
                    String idToBeUpdated = scanner.next();
                    Donor donorToBeUpdated = (Donor) donors.get(idToBeUpdated, "getDonorID");
                    donors.remove(donorToBeUpdated);

                    System.out.print("Enter name: ");
                    String name2 = scanner.next();
                    System.out.println("Enter age: ");
                    int age2 = scanner.nextInt();
                    System.out.println("Enter gender: ");
                    char gender2 = scanner.next().charAt(0);
                    System.out.println("Enter date of birth: ");
                    Date dob2 = new Date(Integer.parseInt(scanner.next().split("-")[0]), Integer.parseInt(scanner.next().split("-")[1]), Integer.parseInt(scanner.next().split("-")[2]));
                    Donor donorObjToBeUpdated = new Donor(donorToBeUpdated.getDonorID(), name2, age2, new Date(), gender2);
                    donors.add(donorObjToBeUpdated);
                    break;
                case "4":
                    System.out.println("Enter ID: ");
                    String idToBeFound = scanner.next();
                    System.out.println(donors.get(idToBeFound, "getDonorID"));
                    break;
                case "5":
                    System.out.println("Donors");
                    int count = 0;
                    donors.forEach((donorElement)
                            -> System.out.print(donorElement.getDonorID() + " - " + donorElement.getName())
                    );
                case "0":
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }
}
