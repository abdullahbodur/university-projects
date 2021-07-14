package cs.hacettepe.bbm102;

import java.util.List;

public class FeatureFilm extends Film{

    public String releaseDate;
    public int budget;
    public List<Integer> writers;
    public List<String> genres;





    public FeatureFilm(String... args) {
        super(args);



        this.genres = Helper.stringToArray(args[7]);
        this.releaseDate = args[8];
        this.writers = Helper.stringToArrayInt(args[9]);
        this.budget = Integer.parseInt(args[10]);

    }
}
