import java.io.*;
import java.util.Collections;
import java.util.List;

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

            System.out.println(e);
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

    public static  List<Sports> putProperOrder(List<Sports> clubs){

        Collections.sort(clubs, (o1, o2) -> {


            Integer p1 = o1.totalPoints;
            Integer p2 = o2.totalPoints;

            int sComp = p1.compareTo(p2);


            if(sComp != 0){
                return sComp;
            }


            Integer ts1 = o1.totalScores - o1.totalAgainstScores;
            Integer ts2 = o2.totalScores - o2.totalAgainstScores;


            return ts1.compareTo(ts2);

        });


        Collections.reverse(clubs);

        return clubs;

    }

}
