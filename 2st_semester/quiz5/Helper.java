import java.io.*;

public class Helper {

    public static String readFile(String filePath) {
        StringBuilder lines = new StringBuilder();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line = reader.readLine();

            while (line != null) {

                lines.append(line).append("\n");

                line = reader.readLine();
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
        } catch (IOException e) {

            System.out.println("IO Exception");
        }

        return lines.toString();
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
}
