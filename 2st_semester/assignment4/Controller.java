import java.util.Arrays;
import java.util.Collections;

public class Controller {

    public static void start() {

        String[] parts_text = Helper.readFile(Data.parts_txt);
        String[] items_text = Helper.readFile(Data.items_txt);
        String[] tokens_text = Helper.readFile(Data.tokens_txt);
        String[] tasks_text = Helper.readFile(Data.tasks_txt);

        StringBuilder output = new StringBuilder();

        for (String text : parts_text) {

            Part part = new Part(text);

            Data.parts.put(text, part);
        }


        for (String text : items_text) {

            String[] att = text.split(" ");

            Part p = Data.parts.get(att[1]);

            Item i = new Item(att[0], p);

            Stack<Item> items = Data.items.get(p.name);

            if (items == null) items = new Stack<>(Item.class);

            items.push(i);

            Data.items.put(p.name, items);

        }


        int order = 1;

        for (String text : tokens_text) {

            String[] att = text.split(" ");

            int credit = Integer.parseInt(att[2]);

            Part p = Data.parts.get(att[1]);

            Token token = new Token(credit, att[0], p,order);

            Data.tokens.enqueue(token, true);

            order++;

        }



        for (String text : tasks_text) {

            String[] att = text.split("\t");

            String actType = att[0];

            String[] actions = Arrays.copyOfRange(att, 1, att.length);

            if (actType.equals("BUY")) {

                for (String act : actions) {

                    Item.buy(act);

                }

            } else {

                for (String act : actions) {

                    Item.put(act);

                }

            }


        }



        for (String part : parts_text) {

            output.append(part).append(":\n");

            Item[] items = Data.items.get(part).getAll();

            if (items.length == 0) output.append("\n");

            for (Item i:items) {
                output.append(i.id).append("\n");
            }

            output.append("---------------\n");
        }

        output.append("Token Box:\n");

        // Last sorting before printing
        Token[] tokens =  Data.tokens.getAll();

        Arrays.sort(tokens);

        for (Token t:tokens){
            output.append(t.toString());
        }

        Helper.writeFile(Data.output_txt, output.toString());


    }
}


