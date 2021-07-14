

import java.util.ArrayList;

public class Author {

    private String id;
    private String name;
    private String university;
    private String department;
    private String email;

    private ArrayList<String> articles =  new ArrayList<>();


    public Author(String id, String name, String university, String department, String email, ArrayList<String> articles) {
        this.id = id;
        this.name = name;
        this.university = university;
        this.department = department;
        this.email = email;
        this.articles = articles;
    }


    public Author() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<String> articles) {
        this.articles = articles;
    }
}
