package cs.hacettepe.bbm102;

public class Actor extends Artist{

    private int height;

    public Actor(String... args) {
        super(args);
        this.height = Integer.parseInt(args[4]) ;
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
