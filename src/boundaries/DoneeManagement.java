/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundaries;

import java.util.ArrayList;
import java.util.Arrays;
import charity.*;
import controls.Common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Donee;
import utils.LinkedList;

/**
 *
 * @author zheheng
 */
public class DoneeManagement {
    private static final int PAGE_SIZE = 20;
    private static int currentPage = 0;
    public static final String divider = "=======================================================";
    private static final LinkedList<Donee> donees = new LinkedList<>();
    private static final char[] financialTypeCode = { 'B', 'M', 'T' };
    private static final char[] genderTypeCode = { 'M', 'F' };
    private static final char[] yesOrNoTypeCode = { 'Y', 'N' };
    private static final char[] selectionTypeCode1 = { '0', '1', '2' };

    public static void display() {
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
            System.out.println(divider);

            System.out.println("Donee Management");
            System.out.println(divider + "\n");
            System.out.println("1. Add a new donee");
            System.out.println("2. Search donee details");
            System.out.println("3. List all donees");
            System.out.println("0. Exit");

            System.out.println("Please enter your choice:");
            String input = scanner.next();

            switch (input) {
                case "1":
                    create();
                    break;
                case "2":
                    System.out.println("Enter donee ID to proceed (0 for cancel): ");
                    String doneeIdInput = scanner.next();
                    if (!"0".equals(doneeIdInput)) {
                        detail(doneeIdInput);
                    }
                    break;
                case "3":
                    String option = list(0, ' ', ' ');
                    while (!option.equals("3")) {
                        String tempInput;
                        boolean validation = false;
                        int listAge = 0;
                        char listAS;
                        char listFT;
                        do {
                            System.out.print("Age (Enter 0 to avoid filter): ");
                            tempInput = scanner.next();
                            if (!tempInput.equals("0")) {
                                validation = Common.integerValidator(tempInput);
                            } 
                        } while (!validation);
                        listAge = Integer.parseInt(tempInput);
                        do {
                            System.out.print("Active Status (Enter Y - Yes, N - No or 0 to avoid filter): ");
                            System.out.print("Enter 0 to avoid filter on age: ");
                            tempInput = scanner.next();
                            if (!tempInput.equals("0")) {
                                validation = Common.charValidator(tempInput, yesOrNoTypeCode);
                            }
                        } while (!validation);
                        if (!tempInput.equals("0")) {
                            listAS = Character.toUpperCase(tempInput.charAt(0));
                        } else {
                            listAS = ' ';
                        }

                        do {
                            System.out.print("Financial Type (Enter B - B40, M - M40, T - T20 or 0 to avoid filter): ");
                            System.out.print("Enter 0 to avoid filter on age: ");
                            tempInput = scanner.next();
                            if (!tempInput.equals("0")) {
                                validation = Common.charValidator(tempInput, financialTypeCode);
                            }
                        } while (!validation);
                        if (!tempInput.equals("0")) {
                            listFT = Character.toUpperCase(tempInput.charAt(0));
                        } else {
                            listFT = ' ';
                        }
                        option = list(listAge, listAS,listFT);
                    }

                    break;
                case "0":
                    donees.sort("getDoneeId");
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

    public static void create() {
        String tempInput;
        Scanner scanner = new Scanner(System.in);
        System.out.println(divider);
        System.out.println("Donee (Add)");
        System.out.println(divider + "\n");

        System.out.print("Enter Donee name: ");
        String name = scanner.nextLine();
        boolean validation = false;
        do {
            System.out.print("Enter IC No. (DDMMYY-XX-XXXX): ");
            tempInput = scanner.next();
            validation = Common.ICNoValidator(tempInput);
        } while (!validation);
        String doneeIc = tempInput;
        do {
            System.out.print("Enter date of birth (dd-MM-yyyy): ");
            tempInput = scanner.next();
            validation = Common.dateValidator(tempInput, 'M');
        } while (!validation);
        Date dob = new Date(Integer.parseInt(tempInput.split("-")[0]), Integer.parseInt(tempInput.split("-")[1]),
                Integer.parseInt(tempInput.split("-")[2]));
        do {
            System.out.print("Enter Phone No. (Up to 14Digits): ");
            tempInput = scanner.next();
            validation = Common.phoneNoValidator(tempInput);
        } while (!validation);
        String phoneNo = tempInput;

        do {
            System.out.print("Enter Email: ");
            tempInput = scanner.next();
            validation = Common.emailValidator(tempInput);
        } while (!validation);
        String email = tempInput;

        do {
            System.out.print("Enter gender (M - Male, F - Female): ");
            tempInput = scanner.next();
            validation = Common.charValidator(tempInput, genderTypeCode);
        } while (!validation);
        char gender = Character.toUpperCase(tempInput.charAt(0));

        do {
            System.out.print("Enter the receivedAmount: RM");
            tempInput = scanner.next();
            validation = Common.doubleValidator(tempInput);
        } while (!validation);
        double receivedAmount = Common.DoubleFormatter(Double.parseDouble(tempInput));

        do {
            System.out.print("Enter Financial Type (B - B40, M - M40, T - T20): ");
            tempInput = scanner.next();
            validation = Common.charValidator(tempInput, financialTypeCode);
        } while (!validation);
        char financialType = Character.toUpperCase(tempInput.charAt(0));

        System.out.print("Enter Current Situation (Remarks): ");
        scanner.nextLine();
        String currentSituation = scanner.nextLine();

        char activeStatus = 'Y';
        int currentNo = donees.size() + 1;
        Donee donee = new Donee(doneeIc, name, dob, phoneNo, email, gender, receivedAmount, financialType,
                currentSituation, activeStatus);
        donees.add(donee);
        System.out.println("Record Created: " + donee.getDoneeId());
    }

    public static void update(String doneeId) {
        Scanner scanner = new Scanner(System.in);
        boolean validation = false;
        do {
            System.out.println("Are you sure to proceed update " + doneeId + "?");
            System.out.print("Enter choices (Y - yes, N - No): ");
            String tempInput = scanner.next();
            validation = Common.charValidator(tempInput, yesOrNoTypeCode);

            if (tempInput.equals("Y")) {
                System.out.println(divider);
                System.out.println("Donee (Update)");
                System.out.print("\u001B[1m"); // Enable bold
                System.out.println("Enter 0 to avoid update");
                System.out.print("\u001B[0m"); // Reset to normal
                System.out.println(divider + "\n");
                Donee doneeToBeUpdated = (Donee) donees.get(doneeId, "getDoneeID");
                boolean onChange = false;
                System.out.println("Name: " + doneeToBeUpdated.getName());
                System.out.print("Enter 0 to avoid update");
                tempInput = scanner.nextLine();
                String name;
                if (tempInput.equals("0")) {
                    name = doneeToBeUpdated.getName();
                } else {
                    name = tempInput;
                }
                String doneeIc = doneeToBeUpdated.getDoneeIc();
                do {
                    System.out.println("IC No. (YYMMDD-XX-XXXX): " + doneeToBeUpdated.getDoneeIc());
                    System.out.print("Enter 0 to avoid update");
                    tempInput = scanner.next();
                    if (!tempInput.equals("0")) {
                        validation = Common.ICNoValidator(tempInput);
                        onChange = true;
                    }
                } while (!validation);
                if (onChange) {
                    doneeIc = tempInput;
                    onChange = false;
                }

                Date dob = doneeToBeUpdated.getDob();
                do {
                    System.out.println("Date of birth (dd-MM-yyyy): " + doneeToBeUpdated.getDob());
                    System.out.print("Enter 0 to avoid update");
                    tempInput = scanner.next();
                    if (!tempInput.equals("0")) {
                        validation = Common.dateValidator(tempInput, 'M');
                        onChange = true;
                    }
                } while (!validation);
                if (onChange) {
                    dob = new Date(Integer.parseInt(tempInput.split("-")[0]), Integer.parseInt(tempInput.split("-")[1]),
                            Integer.parseInt(tempInput.split("-")[2]));
                    onChange = false;
                }

                String phoneNo = doneeToBeUpdated.getPhoneNo();
                do {
                    System.out.println("Phone No. (Up to 14Digits): " + doneeToBeUpdated.getPhoneNo());
                    System.out.print("Enter 0 to avoid update");
                    tempInput = scanner.next();
                    if (!tempInput.equals("0")) {
                        validation = Common.phoneNoValidator(tempInput);
                        onChange = true;
                    }
                } while (!validation);
                if (onChange) {
                    phoneNo = tempInput;
                    onChange = false;
                }

                String email = doneeToBeUpdated.getEmail();
                do {
                    System.out.println("Email: " + doneeToBeUpdated.getEmail());
                    System.out.print("Enter 0 to avoid update");
                    tempInput = scanner.next();
                    if (!tempInput.equals("0")) {
                        validation = Common.emailValidator(tempInput);
                        onChange = true;
                    }
                } while (!validation);
                if (onChange) {
                    email = tempInput;
                    onChange = false;
                }

                char gender = doneeToBeUpdated.getGender();
                do {
                    System.out.println("Gender (M - Male, F - Female): " + doneeToBeUpdated.getGender());
                    System.out.print("Enter 0 to avoid update");
                    tempInput = scanner.next();
                    if (!tempInput.equals("0")) {
                        validation = Common.charValidator(tempInput, genderTypeCode);
                        onChange = true;
                    }
                } while (!validation);
                if (onChange) {
                    gender = Character.toUpperCase(tempInput.charAt(0));
                    onChange = false;
                }

                double receivedAmount = doneeToBeUpdated.getReceivedAmount();
                do {
                    System.out.println("ReceivedAmount: RM" + doneeToBeUpdated.getReceivedAmount());
                    System.out.print("Enter 0 to avoid update");
                    tempInput = scanner.next();
                    if (!tempInput.equals("0")) {
                        validation = Common.doubleValidator(tempInput);
                        onChange = true;
                    }
                } while (!validation);
                if (onChange) {
                    receivedAmount = Common.DoubleFormatter(Double.parseDouble(tempInput));
                    onChange = false;
                }

                char financialType = doneeToBeUpdated.getFinancialType();
                do {
                    System.out.println(
                            "Financial Type (B - B40, M - M40, T - T20): " + doneeToBeUpdated.getFinancialType());
                    System.out.print("Enter 0 to avoid update");
                    tempInput = scanner.next();
                    if (tempInput.equals("0")) {
                        validation = Common.charValidator(tempInput, financialTypeCode);
                        onChange = true;
                    }
                } while (!validation);
                if (onChange) {
                    financialType = Character.toUpperCase(tempInput.charAt(0));
                    onChange = false;
                }
                System.out.println("Current Situation (Remarks): " + doneeToBeUpdated.getCurrentSituation());
                System.out.print("Enter 0 to avoid update");
                tempInput = scanner.nextLine();
                String currentSituation;
                if (tempInput.equals("0")) {
                    currentSituation = doneeToBeUpdated.getName();
                } else {
                    currentSituation = tempInput;
                }
                Date joinDate = doneeToBeUpdated.getJoinDate();

                char activeStatus = doneeToBeUpdated.getActiveStatus();
                do {
                    System.out.println("Active Status (Y - Yes, N - No): " + doneeToBeUpdated.getActiveStatus());
                    System.out.print("Enter 0 to avoid update");
                    tempInput = scanner.next();
                    if (tempInput.equals("0")) {
                        validation = Common.charValidator(tempInput, yesOrNoTypeCode);
                        onChange = true;
                    }
                } while (!validation);
                if (onChange) {
                    activeStatus = Character.toUpperCase(tempInput.charAt(0));
                    onChange = false;
                }

                donees.remove(doneeToBeUpdated);
                Donee doneeObjToBeUpdated = new Donee(doneeId, doneeIc, name, dob, phoneNo, email, gender,
                        receivedAmount, financialType, currentSituation, joinDate, activeStatus);
                donees.add(doneeObjToBeUpdated);
                System.out.println("Record Updated");
            } else {
                break;
            }

        } while (!validation);

    }

    public static void delete(String doneeId) {
        Scanner scanner = new Scanner(System.in);
        boolean validation;
        do {
            System.out.println("Are you sure to remove " + doneeId + "?");
            System.out.print("Enter choices (Y - Yes, N - No): ");
            String tempInput = scanner.next();
            validation = Common.charValidator(tempInput, yesOrNoTypeCode);

            if (tempInput.equals("Y") || tempInput.equals("y")) {
                Donee doneeToBeRemoved = (Donee) donees.get(doneeId, "getDoneeId");
                donees.remove(doneeToBeRemoved);
                System.out.println("Record Deleted");
            } else {
                break;
            }

        } while (!validation);

    }

    public static void detail(String doneeId) {
        String tempInput;
        boolean validation;
        Scanner scanner = new Scanner(System.in);
        System.out.println(divider);
        System.out.println("Donee (Detail)");
        System.out.println(divider + "\n");
        Donee doneeDetail = (Donee) donees.get(doneeId, "getDoneeId");

        if (doneeDetail == null) {
            System.out.println("Donee Id - " + doneeId + " Not found");
        } else {
            System.out.println("Donee Id: " + doneeDetail.getDoneeId());
            System.out.println("Name: " + doneeDetail.getName());
            System.out.println("IC No. (DDMMYY-XX-XXXX): " + doneeDetail.getDoneeIc());
            System.out.println("Date of birth (dd-MM-yyyy): " + Common.convertDateToString(doneeDetail.getDob()));
            System.out.println("Age : " + doneeDetail.getAge());
            System.out.println("Phone No. (Up to 14Digits): " + doneeDetail.getPhoneNo());
            System.out.println("Email: " + doneeDetail.getEmail());
            System.out.println("Gender (M - Male, F - Female): " + doneeDetail.getGender());
            System.out.println("ReceivedAmount: RM" + doneeDetail.getReceivedAmount());
            System.out.println("Financial Type (B - B40, M - M40, T - T20): " + doneeDetail.getFinancialType());
            System.out.println("Current Situation (Remarks): " + doneeDetail.getCurrentSituation());
            System.out.println("Joined Date (dd-MM-yyyy): " + Common.convertDateToString(doneeDetail.getJoinDate()));
            System.out.println("Active Status (Y - Yes, N - No): " + doneeDetail.getActiveStatus());
            do {
                System.out.println("1. Update donee - " + doneeId);
                System.out.println("2. Delete donee - " + doneeId);
                System.out.println("0. Back");
                System.out.print("Enter your choices: ");
                tempInput = scanner.next();
                validation = Common.charValidator(tempInput, selectionTypeCode1);
                switch (tempInput) {
                    case "1":
                        update(doneeId);
                        break;
                    case "2":
                        delete(doneeId);
                        break;
                }
            } while (!validation && tempInput == "1");

        }
    }

    // public static void list() {
    // System.out.println("-----------------------------------------------------------");

    // System.out.printf("%-10s | %-5s | %-15s | %-15s%n", "Donee", "Age", "Active
    // Status", "Financial Type");
    // System.out.println("-----------------------------------------------------------");
    // for (Donee donee : donees) {
    // String tempActiveStatus = " - ";
    // switch (donee.getActiveStatus()) {
    // case 'Y':
    // tempActiveStatus = "Yes";
    // break;
    // case 'N':
    // tempActiveStatus = "No";
    // break;

    // }

    // String tempFinancial = " - ";
    // switch (donee.getFinancialType()) {
    // case 'B':
    // tempFinancial = "B40";
    // break;
    // case 'M':
    // tempFinancial = "M40";
    // case 'T':
    // tempFinancial = "T20";
    // break;

    // }

    // String doneeField = donee.getDoneeId() + "-" + donee.getName();

    // System.out.printf("%-10s | %-5d | %-15s | %-15s%n",
    // doneeField, donee.getAge(), tempActiveStatus, tempFinancial);
    // }
    // System.out.println("-----------------------------------------------------------");

    // }
    // public static void list() {
    // Scanner scanner = new Scanner(System.in);
    // boolean running = true;

    // while (running) {
    // int start = currentPage * PAGE_SIZE;
    // int end = Math.min(start + PAGE_SIZE, donees.size());

    // System.out.println("-----------------------------------------------------------");
    // System.out.printf("%-10s | %-5s | %-15s | %-15s%n", "Donee", "Age", "Active
    // Status", "Financial Type");
    // System.out.println("-----------------------------------------------------------");

    // for (int i = start; i < end; i++) {
    // Donee donee = donees.get(i);

    // String tempActiveStatus = " - ";
    // switch (donee.getActiveStatus()) {
    // case 'Y':
    // tempActiveStatus = "Yes";
    // break;
    // case 'N':
    // tempActiveStatus = "No";
    // break;
    // }

    // String tempFinancial = " - ";
    // switch (donee.getFinancialType()) {
    // case 'B':
    // tempFinancial = "B40";
    // break;
    // case 'M':
    // tempFinancial = "M40";
    // break;
    // case 'T':
    // tempFinancial = "T20";
    // break;
    // }

    // String doneeField = donee.getDoneeId() + "-" + donee.getName();

    // System.out.printf("%-10s | %-5d | %-15s | %-15s%n",
    // doneeField, donee.getAge(), tempActiveStatus, tempFinancial);
    // }

    // System.out.println("-----------------------------------------------------------");

    // System.out.println("Page " + (currentPage + 1) + " of " + ((donees.size() +
    // PAGE_SIZE - 1) / PAGE_SIZE));
    // System.out.println("Enter '1' for next page, '2' for previous page, '0' to
    // quit:");

    // String input = scanner.nextLine().trim();

    // switch (input) {
    // case "1":
    // if ((currentPage + 1) * PAGE_SIZE < donees.size()) {
    // currentPage++;
    // } else {
    // System.out.println("You are already on the last page.");
    // }
    // break;
    // case "2":
    // if (currentPage > 0) {
    // currentPage--;
    // } else {
    // System.out.println("You are already on the first page.");
    // }
    // break;
    // case "0":
    // running = false;
    // break;
    // default:
    // System.out.println("Invalid input, please enter '1', '2' or '0'");
    // break;
    // }
    // }

    // scanner.close();
    // }

    public static String list(int age, char activeStatus, char financialType) {
        LinkedList<Donee> filteredDonees = new LinkedList<>();

        for (Donee donee : donees) {
            boolean matches = true;

            if (age != 0 && donee.getAge() != age) {
                matches = false;
            }
            if (activeStatus != ' ' && donee.getActiveStatus() != activeStatus) {
                matches = false;
            }
            if (financialType != ' ' && donee.getFinancialType() != financialType) {
                matches = false;
            }

            if (matches) {
                filteredDonees.add(donee);
            }
        }
        String input = "0";
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            int start = currentPage * PAGE_SIZE;
            int end = Math.min(start + PAGE_SIZE, donees.size());
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%-10s | %-5s | %-15s | %-15s%n", "Donee", "Age", "Active Status", "Financial Type");
            System.out.println("-----------------------------------------------------------");
            for (Donee donee : filteredDonees) {
                String tempActiveStatus = " - ";
                switch (donee.getActiveStatus()) {
                    case 'Y':
                        tempActiveStatus = "Yes";
                        break;
                    case 'N':
                        tempActiveStatus = "No";
                        break;

                }

                String tempFinancial = " - ";
                switch (donee.getFinancialType()) {
                    case 'B':
                        tempFinancial = "B40";
                        break;
                    case 'M':
                        tempFinancial = "M40";
                    case 'T':
                        tempFinancial = "T20";
                        break;

                }

                String doneeField = donee.getDoneeId() + "-" + donee.getName();

                System.out.printf("%-10s | %-5d | %-15s | %-15s%n",
                        doneeField, donee.getAge(), tempActiveStatus, tempFinancial);
            }
            System.out.println("-----------------------------------------------------------");
            System.out.println("Page " + (currentPage + 1) + " of " + ((donees.size() + PAGE_SIZE - 1) / PAGE_SIZE));
            System.out.println(
                    "Enter '1' for next page, '2' for previous page, '3' for list by criteria, '4' for generate report, '0' to quit:");

            input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    if ((currentPage + 1) * PAGE_SIZE < donees.size()) {
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
                    generate(filteredDonees);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid input, please enter '1', '2', '3', '4' or '0'");
                    break;
            }
        }

        scanner.close();
        return input;
    }

    public static void generate(LinkedList<Donee> filteredDonees) {
        String downloadPath = System.getProperty("user.home") + "/Downloads/donee_list.txt";
        try (FileWriter writer = new FileWriter(new File(downloadPath))) {
            writer.write("Donee       | Age  | Active Status   | Financial Type\n");
            writer.write("-----------------------------------------------------------\n");
            for (Donee donee : filteredDonees) {
                String tempActiveStatus = " - ";
                switch (donee.getActiveStatus()) {
                    case 'Y':
                        tempActiveStatus = "Yes";
                        break;
                    case 'N':
                        tempActiveStatus = "No";
                        break;
                }

                String tempFinancial = " - ";
                switch (donee.getFinancialType()) {
                    case 'B':
                        tempFinancial = "B40";
                        break;
                    case 'M':
                        tempFinancial = "M40";
                        break;
                    case 'T':
                        tempFinancial = "T20";
                        break;
                }

                String doneeField = donee.getDoneeId() + "-" + donee.getName();

                writer.write(String.format("%-10s | %-5d | %-15s | %-15s%n",
                        doneeField, donee.getAge(), tempActiveStatus, tempFinancial));
            }
            System.out.println("File successfully saved to " + downloadPath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }

}
