package cs.hacettepe.bbm102;

import cs.hacettepe.bbm102.Helper;
import cs.hacettepe.bbm102.Score;
import java.util.HashMap;
import java.util.List;

public class Film {

    public int Id;
    public String title;
    public String language;
    public int length;
    public String country;
    public List<Integer> directors;
    public List<Integer> performers;
    public HashMap<Integer,Score> score;
    public double totalScore;



    public Film(String... args) {

        Id = Integer.parseInt(args[0]);
        this.title = args[1];
        this.language = args[2];
        this.directors = Helper.stringToArrayInt(args[3]);
        this.length = Integer.parseInt(args[4]);
        this.country = args[5];
        this.performers = Helper.stringToArrayInt(args[6]);
        this.score = new HashMap<>();


    }

}
