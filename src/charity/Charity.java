/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package charity;

import charity.boundaries.DonationManagement;
import charity.boundaries.DoneeManagement;
import charity.boundaries.DonorManagement;
import java.util.Scanner;

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
        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Donor Management Subsystem");
            System.out.println("2. Donee Management Subsystem");
            System.out.println("3. Donation Management Subsystem");

            System.out.print("Hi, please enter yout choice:");
            String input = scanner.next();
            switch (input) {
                case "1":
                    DonorManagement.display();
                    break;
                case "2":
                    DoneeManagement.display();
                    break;
                case "3":
                    DonationManagement.display();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

}
