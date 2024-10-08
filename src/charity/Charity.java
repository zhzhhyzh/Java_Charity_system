/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package charity;

import java.util.Scanner;

import boundaries.DonationManagement;
import boundaries.DoneeManagement;
import boundaries.DonorManagement;
import utils.LinkedList;

/**
 *
 * @author quinton
 */
public class Charity {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        System.out.println("=============================================");
        System.out.println("          Cha Org. Management System");
        
        System.out.println("=============================================");

        while (flag) {
            System.out.println("\nMenu:");
            System.out.println("1. Donor Management Subsystem");
            System.out.println("2. Donee Management Subsystem");
            System.out.println("3. Donation Management Subsystem");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            String input = scanner.next();

            switch (input) {
                case "1":
                    DonorManagement.display();
                    DonorManagement.donors = new LinkedList<>();
                    break;
                case "2":
                    DoneeManagement.display();
                    DoneeManagement.donees = new LinkedList<>();
                    DoneeManagement.donors = new LinkedList<>();
                    DoneeManagement.donations = new LinkedList<>();
                    break;
                case "3":
                    DonationManagement.display();
                    DonationManagement.donees = new LinkedList<>();
                    DonationManagement.donors = new LinkedList<>();
                    DonationManagement.donations = new LinkedList<>();
                    break;
                case "0":
                    flag = false;
                    System.out.println("Exiting Cha Org. Management System.");
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
        scanner.close(); // Close the scanner resource
    }

}
