/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.LinkedList;
import utils.List;

/**
 *
 * @author quinton, zheheng
 */
public class Common {

    final static String folder = "documents/";
    public static final char[] GENDER_TYPE_CODE = {'M', 'F'};

    public static void writeObjectsToFile(LinkedList<?> list, String filename) throws FileNotFoundException, IOException {
        File file = new File(folder + filename);
        ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
        Object[] newList = list.toArray();
        ooStream.writeObject(newList);
        ooStream.close();
    }

    public static void writeObjectsToFile(ArrayList<?> list, String filename) throws FileNotFoundException, IOException {
        File file = new File(folder + filename);
        ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
//        Object[] newList = list.toArray();
        ooStream.writeObject(list);
        ooStream.close();
    }

    public static Object retrieveObjectsFromFile(LinkedList<?> list, String filename) throws FileNotFoundException, IOException, ClassNotFoundException {
        File file = new File(folder + filename);
        if (file.exists() && !file.isDirectory()) {
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));

            Object obj = oiStream.readObject();
            oiStream.close();
            return obj;

        } else {
            file.createNewFile();
            return null;
        }

    }

    public static boolean ICNoValidator(String icNo) {
        String regex = "^\\d{2}(0[1-9]|1[0-2])(\\d{2})-\\d{2}-\\d{4}$";

        if (icNo.matches(regex)) {

            return true;
        } else {
            System.out.println("The IC No. format is invalid.");
            System.out.println("Please follow the format (DDMMYY-XX-XXXX)");
            return false;
        }
    }

    public static boolean integerValidator(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("This number is invalid.");

            return false;
        }
    }

    public static boolean charValidator(String character, char[] charArray) {
        boolean charArrayFlag = false;
        if (character.length() == 1) {
            for (char compare : charArray) {
                if (Character.toUpperCase(character.charAt(0)) == compare) {
                    charArrayFlag = true;
                    break;
                }
            }
            if(charArrayFlag == false){
                System.out.print("Character is invalid:\nPlease input either ");
                for(char word: charArray){
                    System.out.print("[" + word + "] ");
                }
                System.out.print("\n");
            }

            return charArrayFlag;
        } else {
            System.out.println("This character is invalid.");
            return false;
        }

    }

    public static boolean emailValidator(String email) {
        String regex = "^\\w+(?:[.-]\\w+)*(\\+[a-zA-Z0-9-]+)?@\\w+(?:[.-]\\w+)*(?:\\.\\w{2,3})+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            System.out.println("The Email format is invalid.");
            return false;
        }

    }

    public static boolean dateValidator(String dateString, char constraint) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\\\d{4}$";
        if (dateString.matches(regex)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate inputDate = LocalDate.parse(dateString, formatter);
            LocalDate today = LocalDate.now();
            switch (constraint) {
                case 'G':
                    if (inputDate.isAfter(today)) {
                        return true;
                    } else {
                        System.out.println("Date must be after today.");
                        return false;
                    }
                case 'L':
                    if (inputDate.isBefore(today)) {
                        return true;
                    } else {
                        System.out.println("Date must be before today.");
                        return false;
                    }

                case 'M':
                    if (inputDate.isBefore(today) || inputDate.equals(today)) {
                        return true;
                    } else {
                        System.out.println("Date must be before today or today.");
                        return false;
                    }

                case 'H':
                    if (inputDate.isBefore(today) || inputDate.equals(today)) {
                        return true;
                    } else {
                        System.out.println("Date must be after today or today.");
                        return false;
                    }

                default:
                    return true; // No constraint, return true
            }
        } else {
            System.out.println("Wrong Format of Date.");
            System.out.println("Should be (dd-MM-yyyy)");
            return false;
        }

    }
    public static boolean dateValidator2(String dateString, char constraint) {
        String regex = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\\d{4}$";
        if (dateString.matches(regex)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate inputDate = LocalDate.parse(dateString, formatter);
            LocalDate today = LocalDate.now();
            switch (constraint) {
                case 'G':
                    if (inputDate.isAfter(today)) {
                        return true;
                    } else {
                        System.out.println("Date must be after today.");
                        return false;
                    }
                case 'L':
                    if (inputDate.isBefore(today)) {
                        return true;
                    } else {
                        System.out.println("Date must be before today.");
                        return false;
                    }

                case 'M':
                    if (inputDate.isBefore(today) || inputDate.equals(today)) {
                        return true;
                    } else {
                        System.out.println("Date must be before today or today.");
                        return false;
                    }

                case 'H':
                    if (inputDate.isBefore(today) || inputDate.equals(today)) {
                        return true;
                    } else {
                        System.out.println("Date must be after today or today.");
                        return false;
                    }

                default:
                    return true; // No constraint, return true
            }
        } else {
            System.out.println("Wrong Format of Date.");
            System.out.println("Should be (dddd-MM-yyyy)");
            return false;
        }

    }

    public static boolean phoneNoValidator(String phoneNo) {
        String regex = "^(?:[0-9]?){14}[0-9]$";

        if (phoneNo.matches(regex)) {
            return true;
        } else {
            System.out.println("The Phone No. format is invalid.");
            System.out.println("Phone No. only allowed up to 14 Digits");
            return false;
        }
    }

    public static boolean doubleValidator(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("This number is invalid.");

            return false;
        }
    }

    public static double DoubleFormatter(double number) {
        number = Math.round(number * 100.0) / 100.0;
        return number;
    }

    public static int calculateAge(Date birth) {

        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.setTime(birth);

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTimeInMillis(System.currentTimeMillis());

        int birthYear = birthCalendar.get(Calendar.YEAR);
        int currentYear = todayCalendar.get(Calendar.YEAR);
        return (currentYear - birthYear);

    }
    
    public static String convertDateToString(Date date){
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);

    }
}
