/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundaries;

import charity.*;
import utils.LinkedList;
import java.util.Scanner;
import models.Donation;

/**
 *
 * @author quinton
 */
public class DonationManagement {
    
    private static final LinkedList<Donation> donations = new LinkedList<>();
    
    public static void display(){
         Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Add a new donation");
            System.out.println("2. Remove a donation");
            System.out.println("3. Amend donation details");
            System.out.println("3. Search donation details");
            System.out.println("3. List all donations");

            System.out.println("Hi, please enter yout choice:");
            String input = scanner.next();
            switch (input) {
                case "1":
                       
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }
    
    public static void addDonation(Donation donation){
        
        donations.add(donation);
    }
    
    public static void removeDonation(){
        
        donations.remove(donationToBeRemoved);
    }
    
    public static void amendDonation(){
        
    }
    
    public static void searchDonation(){
        
    }
    
    public static void listDonation(){
        
    }
}
