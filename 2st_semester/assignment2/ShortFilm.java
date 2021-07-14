package cs.hacettepe.bbm102;

import java.util.List;

public class ShortFilm extends Film{

    public String releaseDate;
    public List<Integer> writers;
    public List<String> genres;


    public ShortFilm(String... args) {
        super(args);
        this.genres = Helper.stringToArray(args[7]);
        this.releaseDate = args[8];
        this.writers = Helper.stringToArrayInt(args[9]);

    }
}
