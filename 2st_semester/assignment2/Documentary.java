package cs.hacettepe.bbm102;

public class Documentary extends Film {
    public String releaseDate;


    public Documentary(String... args) {
        super(args);
        this.releaseDate = args[7];
    }
}
