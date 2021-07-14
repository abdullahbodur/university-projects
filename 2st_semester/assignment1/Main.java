import java.util.ArrayList;

public class Main {



    public static void main(String[] args) {



        // write your code here

        String authorFile = args[0];
        String commandFile = args[1];

        String authorTxt = Helper.readFile(authorFile);

        ArrayList<Author> authors = Controller.readAuthors(authorTxt);


        // birden fazla dosya olma durumu
        ArrayList<Article> articles = new ArrayList<>();

        String commandsTxt = Helper.readFile(commandFile);

        String[] commands = Helper.stringToArray(commandsTxt);


        for (int i = 0;i < commands.length; i++) {

            String command = commands[i];

            if (command.contains("list")) {

                Controller.listArticle(authors,articles,commands[i-1]);

            } else if (command.contains("read")) {

                String file_name = command.split(" ")[1];

                String articleTxt = Helper.readFile(file_name);

                articles.addAll(Controller.readArticles(articleTxt));

            }

            else if (command.contains("sortedAll")){

                authors = Controller.sortedAll(authors);
            }
            else if (command.contains("completeAll")){
                authors = Controller.completeAll(authors,articles);
            }
            else if (command.contains("del")){

                String auth_code = command.split(" ")[1];

                authors = Controller.delAuthor(authors,auth_code);

            }
        }
    }
}
