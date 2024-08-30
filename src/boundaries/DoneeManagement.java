/* Author: Yeoh Zhe Heng
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundaries;

import controls.Common;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
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
 * @author zheheng
 */
public class DoneeManagement {

    private static final int PAGE_SIZE = 10;
    private static int currentPage = 0;
    public static final String divider = "=======================================================";
    public static final String DIVIDER = "-----------------------------------------------------------------------------------------";
    public static LinkedList<Donation> donations = new LinkedList<>();
    public static LinkedList<Donee> donees = new LinkedList<>();
    private static final String UNXERR = "Unexpected Error. Please Contact Administrator.";
    private static final char[] doneeTypeCode = {'I', 'O', 'F'};
    private static final char[] genderTypeCode = {'M', 'F'};
    private static final char[] yesOrNoTypeCode = {'Y', 'N'};
    private static final char[] selectionTypeCode1 = {'0', '1', '2', '3'};
    public static LinkedList<Donor> donors = new LinkedList<>();

    public static void display() {
        Common.loadData("donations.dat", donations, Donation[].class, DonationManagement.class);
        Common.loadData("donors.dat", donors, Donor[].class, DonorManagement.class);
        Common.loadData("donees.dat", donees, Donee[].class, DoneeManagement.class);
        Scanner scanner = new Scanner(System.in);
        boolean indicateFlag = true;
        while (indicateFlag) {
            System.out.println("\n" + divider);

            System.out.println("Donee Management");
            System.out.println(divider);
            System.out.println("1. Add a new donee");
            System.out.println("2. Search donee details");
            System.out.println("3. List all donees");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

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
                    scanner.nextLine();
                    break;
                case "3":
                    String option = list("", 0, ' ', ' ');

                    while (option.equals("3")) {
                        String tempInput;
                        boolean validation = false;
                        int listAge = 0;
                        char listAS;
                        char listFT;
                        String listSearch;
                        do {
                            System.out.print("Search Donee Id or Name (Enter 0 to avoid filter): ");
                            tempInput = scanner.next();
                            if (!tempInput.equals("0")) {
                                validation = Common.requiredField(tempInput);
                            } else {
                                validation = true;
                            }
                        } while (!validation);
                        listSearch = tempInput;
                        do {
                            System.out.print("Age/Publish/Married Year(s) (Enter 0 to avoid filter): ");
                            tempInput = scanner.next();
                            if (!tempInput.equals("0")) {
                                validation = Common.integerValidator(tempInput);
                            } else {
                                validation = true;
                            }
                        } while (!validation);
                        listAge = Integer.parseInt(tempInput);
                        do {
                            System.out.print("Active Status (Enter Y - Yes, N - No or 0 to avoid filter)");
//                            System.out.print("Enter 0 to avoid filter on age: ");
                            scanner.nextLine();
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
                            System.out.print("Enter Donee Type ([I]-Individual, [O]-Organization, [F]-Family, [0] to avoid filter): ");

//                            System.out.print("Enter 0 to avoid filter on age: ");
                            scanner.nextLine();
                            tempInput = scanner.next();
                            if (!tempInput.equals("0")) {
                                validation = Common.charValidator(tempInput, doneeTypeCode);
                            }
                        } while (!validation);
                        if (!tempInput.equals("0")) {
                            listFT = Character.toUpperCase(tempInput.charAt(0));
                        } else {
                            listFT = ' ';
                        }
                        option = list(listSearch, listAge, listAS, listFT);
                    }

                    break;
                case "0":
                    donees.sort("getDoneeId",false);
                    try {
                        Common.writeObjectsToFile(donors, "donors.dat");
                        Common.writeObjectsToFile(donations, "donations.dat");
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
        boolean validation = false;
        String tempInput;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + divider);
        System.out.println("Donee (Add)");
        System.out.println(divider);

        do {
            System.out.print("Enter Donee Type ([I]-Individual, [O]-Organization, [F]-Family): ");
            tempInput = scanner.next();
            validation = Common.charValidator(tempInput, doneeTypeCode);
        } while (!validation);
        char doneeType = Character.toUpperCase(tempInput.charAt(0));
        String doneeIc = "";
        String name = "";
        Date dob = new Date();
        char gender = ' ';
        String repName = "";
        scanner.nextLine();
        switch (doneeType) {
            //Individual
            case 'I':
                do {
                    System.out.print("Enter Donee name: ");
                    tempInput = scanner.nextLine();
                    validation = Common.requiredField(tempInput);
                } while (!validation);
                name = tempInput;

                do {
                    System.out.print("Enter IC No. (YYMMDD-XX-XXXX): ");
                    tempInput = scanner.next();
                    validation = Common.ICNoValidator(tempInput);
                } while (!validation);
                doneeIc = tempInput;

                do {
                    System.out.print("Enter date of birth (dd-MM-yyyy): ");
                    tempInput = scanner.next();
                    validation = Common.dateValidator(tempInput, 'M');
                } while (!validation);
                dob = Common.structureForDateParsing(tempInput);

                do {
                    System.out.print("Enter gender ([M] - Male, [F] - Female): ");
                    tempInput = scanner.next();
                    validation = Common.charValidator(tempInput, genderTypeCode);
                } while (!validation);
                gender = Character.toUpperCase(tempInput.charAt(0));
                break;

            //Organization
            case 'O':
                do {
                    System.out.print("Enter Organization name: ");
                    tempInput = scanner.nextLine();
                    validation = Common.requiredField(tempInput);
                } while (!validation);
                name = tempInput;

                do {
                    System.out.print("Enter date of found (dd-MM-yyyy): ");
                    tempInput = scanner.next();
                    validation = Common.dateValidator(tempInput, 'M');
                } while (!validation);
                dob = Common.structureForDateParsing(tempInput);
                String[] res = tempInput.split("[-]", 0);
                String extractedYear = res[2];

                do {
                    System.out.print("Enter SME No. (YYYYTTXXXX): ");
                    tempInput = scanner.next();
                    validation = Common.SmeNoValidator(tempInput, extractedYear);
                } while (!validation);
                doneeIc = tempInput;
                scanner.nextLine();
                do {
                    System.out.print("Enter Organization Rep. name: ");
                    tempInput = scanner.nextLine();
                    validation = Common.requiredField(tempInput);
                } while (!validation);
                repName = tempInput;

                do {
                    System.out.print("Enter Organization Rep. Gender ([M] - Male, [F] - Female): ");
                    tempInput = scanner.next();
                    validation = Common.charValidator(tempInput, genderTypeCode);
                } while (!validation);
                gender = Character.toUpperCase(tempInput.charAt(0));
                break;

            //Family
            case 'F':
                do {
                    System.out.print("Enter Family name: ");
                    tempInput = scanner.nextLine();
                    validation = Common.requiredField(tempInput);
                } while (!validation);
                name = tempInput;

                do {
                    System.out.print("Enter date of Married (dd-MM-yyyy): ");
                    tempInput = scanner.next();
                    validation = Common.dateValidator(tempInput, 'M');
                } while (!validation);
                dob = Common.structureForDateParsing(tempInput);
                scanner.nextLine();
                do {
                    System.out.print("Enter Family Rep. name: ");
                    tempInput = scanner.nextLine();
                    validation = Common.requiredField(tempInput);
                } while (!validation);
                repName = tempInput;

                do {
                    System.out.print("Enter Family Rep. Gender ([M] - Male, [F] - Female): ");
                    tempInput = scanner.next();
                    validation = Common.charValidator(tempInput, genderTypeCode);
                } while (!validation);
                gender = Character.toUpperCase(tempInput.charAt(0));
                break;
            default:
                System.err.println("Unexpected Error. Please Contact Administrator.");
        }

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

        // do {
        //     System.out.print("Enter the receivedAmount: RM");
        //     tempInput = scanner.next();
        //     validation = Common.doubleValidator(tempInput);
        // } while (!validation);
        // double receivedAmount = Common.DoubleFormatter(Double.parseDouble(tempInput));
        System.out.print("Enter Remarks: ");
        scanner.nextLine();
        String currentSituation = scanner.nextLine();

        int current = 1000;

        for (Donee donee : donees) {
            if (Integer.parseInt(donee.getDoneeId()) > current) {
                current = Integer.parseInt(donee.getDoneeId());
            }

        }
        current++;
        String doneeId = Integer.toString(current);

        char activeStatus = 'Y';
        int currentNo = donees.size() + 1;
        Donee donee = new Donee(doneeId, doneeIc, name, dob, phoneNo, email, gender, 0, doneeType,
                currentSituation, activeStatus, repName);
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

            if (tempInput.toUpperCase().equals("Y")) {
                System.out.println(divider);
                System.out.println("Donee (Update)");
                System.out.print("\u001B[1m"); // Enable bold
                System.out.println("Enter 0 to avoid update");
                System.out.print("\u001B[0m"); // Reset to normal
                System.out.println(divider + "\n");
                Donee doneeToBeUpdated = (Donee) donees.get(doneeId, "getDoneeId");
                boolean onChange = false;
                scanner.nextLine();
                char doneeType = doneeToBeUpdated.getDoneeType();
                System.out.print("Donee Type ([I]-Individual, [O]-Organization, [F]-Family): " + doneeToBeUpdated.getDoneeType());
                Date dob = doneeToBeUpdated.getDob();
                String name = doneeToBeUpdated.getName();
                String doneeIc = doneeToBeUpdated.getDoneeIc();
                char gender = doneeToBeUpdated.getGender();
                String repName = doneeToBeUpdated.getRepName();

                switch (doneeType) {
                    //Individual
                    case 'I':
                        do {
                            System.out.println("Name: " + doneeToBeUpdated.getName());
                            System.out.print("Enter 0 to avoid update: ");
                            tempInput = scanner.nextLine();
                            validation = Common.requiredField(tempInput);
                        } while (!validation);
                        if (tempInput.equals("0")) {
                            name = doneeToBeUpdated.getName();
                        } else {
                            name = tempInput;
                        }
                        do {
                            System.out.println("IC No. (YYMMDD-XX-XXXX): " + doneeToBeUpdated.getDoneeIc());
                            System.out.print("Enter 0 to avoid update: ");
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

                        do {
                            System.out.println(
                                    "Date of birth (dd-MM-yyyy): " + Common.convertDateToString(doneeToBeUpdated.getDob()));
                            System.out.print("Enter 0 to avoid update: ");
                            tempInput = scanner.next();
                            if (!tempInput.equals("0")) {
                                validation = Common.dateValidator(tempInput, 'M');
                                onChange = true;
                            }
                        } while (!validation);
                        if (onChange) {

                            dob = Common.structureForDateParsing(tempInput);

                            onChange = false;
                        }

                        do {
                            System.out.println("Gender ([M] - Male, [F] - Female): " + doneeToBeUpdated.getGender());
                            System.out.print("Enter 0 to avoid update: ");
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

                        break;

                    //Organization
                    case 'O':
                        do {
                            System.out.println("Organization Name: " + doneeToBeUpdated.getName());
                            System.out.print("Enter 0 to avoid update: ");
                            tempInput = scanner.nextLine();
                            validation = Common.requiredField(tempInput);
                        } while (!validation);
                        if (tempInput.equals("0")) {
                            name = doneeToBeUpdated.getName();
                        } else {
                            name = tempInput;
                        }
                        do {
                            System.out.println(
                                    "Date of Founded (dd-MM-yyyy): " + Common.convertDateToString(doneeToBeUpdated.getDob()));
                            System.out.print("Enter 0 to avoid update: ");
                            tempInput = scanner.next();
                            if (!tempInput.equals("0")) {
                                validation = Common.dateValidator(tempInput, 'M');
                                onChange = true;
                            }
                        } while (!validation);
                        String extractedYear = "";
                        String[] res = new String[3];
                        if (onChange) {

                            dob = Common.structureForDateParsing(tempInput);
                            res = tempInput.split("[-]", 0);
                            extractedYear = res[2];
                            onChange = false;
                        } else {

                            try {
                                LocalDate localDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                                extractedYear = String.valueOf(localDate.getYear());
                            } catch (DateTimeParseException e) {
                                System.err.println(UNXERR);
                            }
                        }

                        do {
                            System.out.println("SME No. (YYYYTTXXXXXX): " + doneeToBeUpdated.getDoneeIc());
                            System.out.print("Enter 0 to avoid update: ");
                            tempInput = scanner.next();
                            if (!tempInput.equals("0")) {
                                validation = Common.SmeNoValidator(tempInput, extractedYear);
                                onChange = true;
                            }
                        } while (!validation);
                        if (onChange) {
                            doneeIc = tempInput;
                            onChange = false;
                        }

                        do {
                            System.out.println("Organization Rep. Name: " + doneeToBeUpdated.getRepName());
                            System.out.print("Enter 0 to avoid update: ");
                            tempInput = scanner.nextLine();
                            validation = Common.requiredField(tempInput);
                        } while (!validation);
                        if (tempInput.equals("0")) {
                            name = doneeToBeUpdated.getRepName();
                        } else {
                            name = tempInput;
                        }

                        do {
                            System.out.println("Organization Rep. Gender ([M] - Male, [F] - Female): " + doneeToBeUpdated.getGender());
                            System.out.print("Enter 0 to avoid update: ");
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
                        break;

                    //Family
                    case 'F':
                        do {
                            System.out.println("Family Name: " + doneeToBeUpdated.getName());
                            System.out.print("Enter 0 to avoid update: ");
                            tempInput = scanner.nextLine();
                            validation = Common.requiredField(tempInput);
                        } while (!validation);
                        if (tempInput.equals("0")) {
                            name = doneeToBeUpdated.getName();
                        } else {
                            name = tempInput;
                        }
                        do {
                            System.out.println(
                                    "Date of Married (dd-MM-yyyy): " + Common.convertDateToString(doneeToBeUpdated.getDob()));
                            System.out.print("Enter 0 to avoid update: ");
                            tempInput = scanner.next();
                            if (!tempInput.equals("0")) {
                                validation = Common.dateValidator(tempInput, 'M');
                                onChange = true;
                            }
                        } while (!validation);
                        if (onChange) {

                            dob = Common.structureForDateParsing(tempInput);

                            onChange = false;
                        }

                        do {
                            System.out.println("Family Rep. Name: " + doneeToBeUpdated.getRepName());
                            System.out.print("Enter 0 to avoid update: ");
                            tempInput = scanner.nextLine();
                            validation = Common.requiredField(tempInput);
                        } while (!validation);
                        if (tempInput.equals("0")) {
                            name = doneeToBeUpdated.getRepName();
                        } else {
                            name = tempInput;
                        }

                        do {
                            System.out.println("Family Rep. Gender ([M] - Male, [F] - Female): " + doneeToBeUpdated.getGender());
                            System.out.print("Enter 0 to avoid update: ");
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

                        break;
                    default:
                        System.err.println(UNXERR);
                }

                String phoneNo = doneeToBeUpdated.getPhoneNo();
                do {
                    System.out.println("Phone No. (Up to 14Digits): " + doneeToBeUpdated.getPhoneNo());
                    System.out.print("Enter 0 to avoid update: ");
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
                    System.out.print("Enter 0 to avoid update: ");
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

                double receivedAmount = doneeToBeUpdated.getReceivedAmount();
                // do {
                //     System.out.println("ReceivedAmount: RM" + doneeToBeUpdated.getReceivedAmount());
                //     System.out.print("Enter 0 to avoid update:");
                //     tempInput = scanner.next();
                //     if (!tempInput.equals("0")) {
                //         validation = Common.doubleValidator(tempInput);
                //         onChange = true;
                //     }
                // } while (!validation);
                // if (onChange) {
                //     receivedAmount = Common.DoubleFormatter(Double.parseDouble(tempInput));
                //     onChange = false;
                // }

                do {
                    System.out.println("Remarks: " + doneeToBeUpdated.getCurrentSituation());
                    System.out.print("Enter 0 to avoid update: ");
                    tempInput = scanner.nextLine();
                    validation = Common.requiredField(tempInput);
                } while (!validation);
                String currentSituation;
                if (tempInput.equals("0")) {
                    currentSituation = doneeToBeUpdated.getCurrentSituation();
                } else {
                    currentSituation = tempInput;
                }
                Date joinDate = doneeToBeUpdated.getJoinDate();

                char activeStatus = doneeToBeUpdated.getActiveStatus();
                do {
                    System.out.println("Active Status (Y - Yes, N - No): " + doneeToBeUpdated.getActiveStatus());
                    System.out.print("Enter 0 to avoid update: ");
                    tempInput = scanner.next();
                    if (!tempInput.equals("0")) {
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
                        receivedAmount, doneeType, currentSituation, joinDate, activeStatus, repName);
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
            System.out.println("Enter Donee Type ([I]-Individual, [O]-Organization, [F]-Family): " + doneeDetail.getDoneeType());
            switch (doneeDetail.getDoneeType()) {
                case 'I':
                    System.out.println("Name: " + doneeDetail.getName());
                    System.out.println("IC No. (YYMMDD-XX-XXXX): " + doneeDetail.getDoneeIc());
                    System.out.println("Date of birth (dd-MM-yyyy): " + Common.convertDateToString(doneeDetail.getDob()));
                    System.out.println("Age : " + doneeDetail.getAge());
                    System.out.println("Gender (M - Male, F - Female): " + doneeDetail.getGender());
                    break;
                case 'O':
                    System.out.println("Org. Name: " + doneeDetail.getName());
                    System.out.println("Date of Found (dd-MM-yyyy): " + Common.convertDateToString(doneeDetail.getDob()));
                    System.out.println("SME No. (YYYYTTXXXXXX): " + doneeDetail.getDoneeIc());
                    System.out.println("Published Year(s): " + doneeDetail.getAge());
                    System.out.println("Org. Rep. Name: " + doneeDetail.getRepName());
                    System.out.println("Org. Rep. Gender (M - Male, F - Female): " + doneeDetail.getGender());
                    break;
                case 'F':
                    System.out.println("Family Name: " + doneeDetail.getName());
                    System.out.println("Date of Married (dd-MM-yyyy): " + Common.convertDateToString(doneeDetail.getDob()));
                    System.out.println("Married Year(s) " + doneeDetail.getAge());
                    System.out.println("Family Rep. Name: " + doneeDetail.getRepName());
                    System.out.println("Family Rep. Gender (M - Male, F - Female): " + doneeDetail.getGender());
                    break;
                default:
                    System.out.println(UNXERR);
            }
            System.out.println("Phone No. (Up to 14Digits): " + doneeDetail.getPhoneNo());
            System.out.println("Email: " + doneeDetail.getEmail());
            System.out.println("ReceivedAmount: RM" + doneeDetail.getReceivedAmount());
            System.out.println("Current Situation (Remarks): " + doneeDetail.getCurrentSituation());
            System.out.println("Joined Date (dd-MM-yyyy): " + Common.convertDateToString(doneeDetail.getJoinDate()));
            System.out.println("Active Status (Y - Yes, N - No): " + doneeDetail.getActiveStatus());
            do {
                System.out.println("1. Update donee - " + doneeId);
                System.out.println("2. Delete donee - " + doneeId);
                System.out.println("3. List Donations asociated with donee - " + doneeId);
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
                    case "3":
                        list(doneeId);
                        break;
                    case "0":
                        break;
                    default:
                        System.out.println("Invalid Input");
                }
            } while (!validation || tempInput.equals("1"));

        }
    }

    public static String list(String search, int age, char activeStatus, char doneeType) {
        LinkedList<Donee> filteredDonees = new LinkedList<>();

        for (Donee donee : donees) {
            boolean matches = true;

            if (!search.equals("") && (!donee.getName().contains(search) && !donee.getDoneeId().contains(search))) {
                matches = false;
            }

            if (age != 0 && donee.getAge() != age) {
                matches = false;
            }
            if (activeStatus != ' ' && donee.getActiveStatus() != activeStatus) {
                matches = false;
            }
            if (doneeType != ' ' && donee.getDoneeType() != doneeType) {
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
            System.out.println(DIVIDER);
            System.out.printf("%-25s | %-20s | %-15s | %-15s | %n", "Donee", "Age/Publish/Married", "Active Status", "Donee Type");
            System.out.println(DIVIDER);
            int countPrint = 1;
            int printNumber = (currentPage + 1) * 10;

            int NoPrintNumber = printNumber - 10;
            for (Donee donee : filteredDonees) {

                if (countPrint <= printNumber && countPrint > NoPrintNumber) {
                    String tempActiveStatus = " - ";
                    switch (donee.getActiveStatus()) {
                        case 'Y':
                            tempActiveStatus = "Yes";
                            break;
                        case 'N':
                            tempActiveStatus = "No";
                            break;

                    }

                    String tempDonee = " - ";
                    switch (donee.getDoneeType()) {
                        case 'I':
                            tempDonee = "Individual";
                            break;
                        case 'O':
                            tempDonee = "Organization";
                            break;
                        case 'F':
                            tempDonee = "Family";
                            break;

                    }

                    String doneeField = donee.getDoneeId() + " - " + donee.getName();

                    System.out.printf("%-25s | %-20d | %-15s | %-15s |%n",
                            doneeField, donee.getAge(), tempActiveStatus, tempDonee);
                }
                countPrint++;
            }
            System.out.println(DIVIDER);
            System.out.println("Page " + (currentPage + 1) + " of " + ((donees.size() + PAGE_SIZE - 1) / PAGE_SIZE));
            System.out.println(
                    "Enter '1' for next page, '2' for previous page, '3' for list by criteria, '4' for generate report, '0' to back:");

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

        // scanner.close();
        return input;
    }

    public static void generate(LinkedList<Donee> filteredDonees) {
        String baseDownloadPath = System.getProperty("user.home") + "/Downloads/donee_list.txt";
        int fileCount = 1;
        String downloadPath = baseDownloadPath;

        while (new File(downloadPath).exists()) {
            downloadPath = baseDownloadPath + "(" + fileCount++ + ").txt";
        }
        try (FileWriter writer = new FileWriter(new File(downloadPath))) {
            writer.write("DONEE NAME LIST\nTotal Donees: " + filteredDonees.size() + "\n");
            writer.write(DIVIDER + "\n");
//            writer.write("Donee       | Age/Publish/Married | Active Status   | Donee Type\n");
            writer.write(String.format("%-30s | %-20s | %-15s | %-15s |%n",
                    "Donee", "Age/Publish/Married", "Active Status", "Donee Type"));
            writer.write(DIVIDER + "\n");
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

                String tempDonee = " - ";
                switch (donee.getDoneeType()) {
                    case 'I':
                        tempDonee = "Individual";
                        break;
                    case 'O':
                        tempDonee = "Organization";
                        break;
                    case 'F':
                        tempDonee = "Family";
                        break;
                }

                String doneeField = donee.getDoneeId() + " - " + donee.getName();

                writer.write(String.format("%-30s | %-20d | %-15s | %-15s |%n",
                        doneeField, donee.getAge(), tempActiveStatus, tempDonee));
                writer.write(DIVIDER + "\n");

            }
            System.out.println("File successfully saved to " + downloadPath);
        } catch (IOException e) {
            System.err.println("An error occurred while writing the file: " + e.getMessage());
        }
    }

    public static void list(String doneeId) {
        String tempInput;
        boolean validation;
        String input = "0";
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        System.out.println(divider);
        System.out.println("Donee with all Donation Made");
        System.out.println("Donee: " + doneeId);
        System.out.println(divider + "\n");
        Donee doneeDetail = (Donee) donees.get(doneeId, "getDoneeId");

        if (doneeDetail == null) {
            System.out.println("Donee Id - " + doneeId + " Not found");
        } else {

            LinkedList<Donation> filteredDonations = new LinkedList<>();

            for (Donation donation : donations) {
                boolean matches = true;

                if (donation.getDoneeId().equals(doneeId)) {
                    filteredDonations.add(donation);
                }
            }
            while (running) {
                int start = currentPage * PAGE_SIZE;
                int end = Math.min(start + PAGE_SIZE, donations.size());
                System.out.println(divider + divider);
                System.out.printf("%-5s | %-20s | %-15s | %-10s | %-10s |%n",
                        "ID", "Donor", "Event", "Type", "Date");
                System.out.println(divider + divider);
                double totalReceivedAmount = 0;
                int countPrint = 1;
                int printNumber = (currentPage + 1) * 10;

                int NoPrintNumber = printNumber - 10;
                for (Donation donation : filteredDonations) {
                    if (countPrint <= printNumber && countPrint > NoPrintNumber) {
                        String donorName = "";
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

                        // totalReceivedAmount += donation.getAmount();
                        for (Donor donor : donors) {
                            if (donor.getDonorID().equals(donation.getDonorId())) {
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
                        System.out.printf("%-5s | %-20s | %-15s | %-10s | %-10s |%n",
                                donation.getDonationId(),
                                donorName,
                                donation.getEventId(),
                                tempType,
                                Common.convertDateToString(donation.getDonationDate()));
                        countPrint++;
                    }
                }

                System.out.println(divider + divider);
                System.err.println("Total Amount Received: " + totalReceivedAmount);
                System.out.println(divider + divider);
                
                System.out.println("Page " + (currentPage + 1) + " of " + ((donations.size() + PAGE_SIZE - 1) / PAGE_SIZE));
                System.out.println("Enter '1' for next page, '2' for previous page, 0' to back:");

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

                    // case "3":
                    //     listDonationsByDonor();
                    //     break;
                    // case "4":
                    //     filterDonations();
                    //     break;
//                    case "3":
//                        generateDonation(filteredDonations);
//                        break;
                    case "0":
                        running = false;
                        break;

                    default:
                        System.out.println("Invalid input, please enter '1', '2' or '0'");
                        break;
                }
            }
        }
    }

}
