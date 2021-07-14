package cs.hacettepe.bbm102;


public class Director extends Artist {

    public String agent;


    public Director(String... args) {
        super(args);
        this.agent = args[4];
    }


}
