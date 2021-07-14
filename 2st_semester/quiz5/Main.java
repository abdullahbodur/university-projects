
public class Main {

    public static void main(String[] args) {
        String lines_txt = Helper.readFile(args[0]);

        String[] lines = lines_txt.split("\n");

        StringBuilder output = new StringBuilder();

        outer :
        for (String line : lines) {

            int val = Integer.parseInt(line);

            if (!((1e3 <= val) && (val <= 2e5))) continue;

            Stack remainders = new Stack();


            while (val != 0){

                if (val > 7) {

                    int remainder = val % 8;

                    val -= remainder;

                    val /= 8;

                    if (!remainders.isFull()) remainders.push(remainder);
                    else {

                        System.out.println("sss");
                        continue outer;

                    }

                }else {

                    remainders.push(val);
                    val = 0;

                }

            }


            StringBuilder octal = new StringBuilder();

            if (remainders.isEmpty()) octal.append("0\n");

            else {

                int size = remainders.size();

                for (int i = 0; i < size; i++) {

                    octal.append(remainders.top());
                    remainders.pop();
                }

            }

            output.append(octal).append("\n");
        }

        if(output.length() > 0) output.deleteCharAt(output.length() -1 );

        Helper.writeFile("octal.txt",output.toString());
    }
}
