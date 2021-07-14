import java.util.Arrays;

public class Item {
    public String id;
    public Part part;


    public Item(String id, Part part) {
        this.id = id;
        this.part = part;
    }


    public static void buy(String t) {

        String[] s = t.split(",");

        String part = s[0];

        int n = Integer.parseInt(s[1]);

        Queue<Token> tokens = Data.tokens;

        // last item index
        int li = tokens.size() - 1;


        while (n > 0) {

            Token token = tokens.get(li);

            int delta;

            // check token part is the same expected
            // this is token what we wanted

            if (token.part.name.equals(part)) {

                // comparing token credit with expected buy value
                if (token.credit >= n) {

                    delta = n;

                    token.credit -= n;

                    n = 0;


                } else {


                    n -= token.credit;

                    delta = token.credit;

                    token.credit = 0;

                }

                tokens.remove_index(li);

                // if token credit after processing remains
                if (token.credit > 0){
                    tokens.enqueue(token, false);
                    li++;
                }

                // pop item repeating x times
                for (int j = 0; j < delta; j++) {

                    Data.items.get(part).pop();

                }
            }


            li--;


        }


        Data.tokens = tokens;

    }


    public static void put(String t) {

        String[] s = t.split(",");

        Part p = Data.parts.get(s[0]);

        String[] its = Arrays.copyOfRange(s, 1, s.length);

        Stack<Item> items = Data.items.get(p.name);


        for (String st : its) {

            Item it = new Item(st, p);

            items.push(it);

        }

        Data.items.put(p.name, items);
    }



}


