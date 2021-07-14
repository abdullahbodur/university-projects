package cs.hacettepe.bbm102;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static String readFile(String filePath) {
        String lines = "";

        try {

            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine();

            while (line != null) {

                lines += line + "\n";

                line = reader.readLine();
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.out.println("File Not Found!");
        } catch (IOException e) {

            System.out.println("IO Exception");
        }

        return lines;
    }


    public static void writeFile(String filePath, String lines) {


        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));

            writer.write(lines);

            writer.close();


        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");

        } catch (IOException e) {
            System.out.println("IO Exception");

        }
    }


    public static String[] lineSplit(String text) {
        return text.split("\n");
    }

    public static List<String> splitFromWhiteSpace(String text){
        return Arrays.asList(text.split("\\s+"));
    }

    public static List<Integer> stringToArrayInt(String text) {

        if (text.contains(",")) {
            return Arrays.stream(text.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        } else {
            return new ArrayList<>(Collections.singletonList(Integer.parseInt(text)));
        }

    }

    public static List<String> stringToArray(String text) {

        if (text.contains(",")) {
            return Arrays.asList(text.split(","));
        } else {
            return new ArrayList<>(Collections.singletonList(text));
        }
    }

    public static String pointToComma(double number){

        return String.format("%.1f",number).replace(".",",").replace(",0","");
    }
}
