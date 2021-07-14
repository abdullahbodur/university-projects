package cs.hacettepe.bbm102;

public class ChildActor extends Artist{

    private int age;



    public ChildActor(String... args) {
        super(args);
        this.age = Integer.parseInt(args[4]);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
