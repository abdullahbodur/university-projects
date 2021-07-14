import java.io.*;
import java.util.ArrayList;

public class Helper {

    public static String readFile(String filePath){
        String lines = "";

        try {

            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine();

            while(line != null) {

                lines += line + "\n";

                line = reader.readLine();
            }

            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
            System.out.println("File Not Found!");
        }

        catch (IOException e) {

            System.out.println("IO Exception");
        }

        return lines;
    }


    public static void writeFile  (String filePath,String lines){


        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));

            writer.write(lines);

            writer.close();


        }catch (FileNotFoundException e){
            System.out.println("File Not Found!");
        }catch (IOException e ){

            System.out.println("IO Exception");

        }
    }

    public static String[] stringToArray(String text){
        return text.split("\n");
    }
}
