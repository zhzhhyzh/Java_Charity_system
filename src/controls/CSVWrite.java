package controls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVWrite {

    public CSVWrite() throws IOException {

    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(data1 -> CSVWrite.escapeSpecialCharacters(data1))
                .collect(Collectors.joining(","));
    }

    public static String escapeSpecialCharacters(String data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data cannot be null");
        }
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static void givenDataArray_whenConvertToCSV_thenOutputCreated(List<String[]> dataLines, String fileName, boolean isAppend) throws IOException {
        FileWriter csvOutputFile = new FileWriter(fileName, isAppend);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(data -> CSVWrite.convertToCSV(data))
                    .forEach(pw::println);
        }
    }

    public static double getTotalValueOfColumn(String fileName, int columnNo, int headerLines, String memberNo) throws IOException {  // specify the header lines in the csv file to skip

        double sum = 0;

        // Read the csv file
        File file = new File(fileName);

        // Read all lines
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        lines.remove(0);
        // variable to hold int values after conversion
        double a;
        //current row number
        int rowcount = 1;
        for (String line : lines) {

            //skip the number of header lines in csv file
            if (rowcount <= headerLines) {
                rowcount = rowcount + 1;
                continue;
            }
            String[] array = line.split(",", -1);

            //read the numbers from the second column after skipping the header lines
            a = Double.parseDouble(array[columnNo]);
            // System.out.println("Icecream Sales (in INR) for the month of "+ array[0] + " is "+ a);
            if (fileName == "earning.csv") {
                if (array[0].equals(memberNo) && array[3].equals("false") && LocalDate.parse(array[5]).isAfter(LocalDate.now())) {
                    sum += a;
                }
            } else {
                //Adding the numbers from the second column of CSV file
                sum = sum + a;
            }

        }

        return sum;
    }

     private void writeToFile(String filename, Object object) {
        File file = new File(filename);
        if (!(file.exists() && !(file.isDirectory()))) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(CSVWrite.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<String[]> dataLines;
            dataLines = new ArrayList<>();
            dataLines.add(new String[]{"Member No.", "Invoice No.", "Current Value", "Original Value", "Earning Date", "Expiry Date"});
            try {
                CSVWrite.givenDataArray_whenConvertToCSV_thenOutputCreated(dataLines, filename, true);
            } catch (IOException ex) {
                Logger.getLogger(CSVWrite.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        try {
//            CSVWrite csvWrite = new CSVWrite();
            List<String[]> dataLines;
            dataLines = new ArrayList<>();
            dataLines.add(new String[]{object.toString()});

            CSVWrite.givenDataArray_whenConvertToCSV_thenOutputCreated(dataLines, filename, true);
        } catch (IOException ex) {
            Logger.getLogger(CSVWrite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
}
