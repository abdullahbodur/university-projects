package cs.hacettepe.bbm102;

public class Writer extends Artist{

    public String writingType;


    public Writer(String... args) {
        super(args);
        this.writingType = args[4];
    }



}
