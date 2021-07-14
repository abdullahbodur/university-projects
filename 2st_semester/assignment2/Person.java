package cs.hacettepe.bbm102;

public class Person {

    private int id;
    public String name;
    public String surname;
    public String country;


    public Person(String... args) {

        this.id = Integer.parseInt(args[0]);
        this.name =args[1];
        this.surname = args[2];
        this.country = args[3];
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
