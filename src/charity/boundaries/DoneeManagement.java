/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package charity.boundaries;

import charity.*;
import java.util.Scanner;

/**
 *
 * @author quinton
 */
public class DoneeManagement {
    public static void display(){
         Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Add a new donor");
            System.out.println("2. Remove a donor");
            System.out.println("3. Update donor details");
            System.out.println("3. Search donor details");
            System.out.println("3. List all donors");

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
}
