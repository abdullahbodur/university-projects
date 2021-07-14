import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    /*

     - Read Article
     This function read article text and returns a list of Article Object
     @params:
        - articleText
        - authors
     */

    public static ArrayList<Article> readArticles(String ArticleText) {

        String[] articlesLines = Helper.stringToArray(ArticleText);

        ArrayList<Article> articles = new ArrayList<>();

        for (String articleLine : articlesLines) {

            String[] articleAttributes = articleLine.split(" ");

            String paperId = articleAttributes[1];
            // String authCode = articleAttributes[1].substring(0, 3);
            String name = articleAttributes[2];
            String publisherName = articleAttributes[3];
            int publishYear = Integer.parseInt(articleAttributes[4]);

            Article article = new Article(paperId, name, publisherName, publishYear);

            articles.add(article);


        }

        return articles;


    }

    /*

     - Read Authors
     This function read author text and returns a list of Author Object
     @params:
        - authorText
     */

    public static ArrayList<Author> readAuthors(String authorText) {

        String[] authorLines = Helper.stringToArray(authorText);


        ArrayList<Author> authors = new ArrayList<>();


        for (String authorLine : authorLines) {


            String[] authorAttr = authorLine.split(" ");


            String id = authorAttr[1];
            String name = authorAttr.length >= 3 ? authorAttr[2] : "";
            String university = authorAttr.length >= 4 ? authorAttr[3] : "";
            String department = authorAttr.length >= 5 ? authorAttr[4] : "";
            String email = authorAttr.length >= 6 ? authorAttr[5] : "";


            ArrayList<String> articleCodes = new ArrayList<>();

            for (int i = 6; i < authorAttr.length; i++) {

                articleCodes.add(authorAttr[i]);

            }

            Author author = new Author(id, name, university, department, email, articleCodes);

            authors.add(author);


        }

        return authors;
    }


    public static ArrayList<Author> completeAll(ArrayList<Author> authors, ArrayList<Article> articles) {


        for (Article article : articles) {


            String author_id = article.getPaperid().substring(0, 3);


            for (Author author : authors) {

                if (author.getArticles().size() < 5 && author.getId().equals(author_id) ) {


                    ArrayList<String> author_articles = author.getArticles();

                    if (!author_articles.contains(article.getPaperid())) {

                        author_articles.add(article.getPaperid());

                    }

                    int index = authors.indexOf(author);


                    author.setArticles(author_articles);

                    authors.set(index,author);

                }



            }

        }


        return authors;
    }


    public static ArrayList<Author> sortedAll(ArrayList<Author> authors) {


        for (Author author : authors) {

            ArrayList<Integer> author_articles = new ArrayList<>();


            for (String article_code : author.getArticles()) {

                author_articles.add(Integer.parseInt(article_code));
            }

            Arrays.sort(author_articles.toArray());

            ArrayList<String> authorArticleString = new ArrayList<>();

            for (Integer article_code : author_articles) {

                authorArticleString.add(article_code.toString());
            }


            author.setArticles(authorArticleString);

        }

        return authors;

    }


    /*
    -----------------------------------------------------------------------------------------------------------------


     - List Article
     This function iterate authors array and find each of author's article code conjugate. Write extended results.
     @params:
        - authors (array)
        - articles (array)
        - commandName
     */

    public static void listArticle(ArrayList<Author> authors, ArrayList<Article> articles, String commandName) {


        String lines = commandName.contains("read") ? "" :   "*********************************** " + commandName + " Successful ***********************************\n\n";


        lines += "----------------------------------------------List---------------------------------------------\n";

        for (Author author : authors) {

            lines += "Author:" + author.getId() + "\t" + author.getName() + "\t" + author.getUniversity() + "\t" + author.getDepartment() + "\t" + author.getEmail() + "\n";


            for (Article article : articles) {


                if (author.getArticles().contains(article.getPaperid())) {
                    lines += "+" + article.getPaperid() + "\t" + article.getName() + "\t" + article.getPublisherName() + "\t" + article.getPublishYear()+"\n";
                }


            }

            lines += "\n";


        }


        lines += "----------------------------------------------End----------------------------------------------\n\n";



        Helper.writeFile("output.txt", lines.toString());
    }

    /*

    -----------------------------------------------------------------------------------------------------------------

     - Delete Author
     This function delete author from list by using author id.
     @params:
        - authors (array)
        - authorId
     */


    public static ArrayList<Author> delAuthor(ArrayList<Author> authors, String authorId) {

        authors.removeIf(author -> author.getId().equals(authorId));

        return authors;
    }


}
