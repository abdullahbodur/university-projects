package cs.hacettepe.bbm102;

import java.util.List;

public class TvSeries extends Film{

    public String startDate;
    public String endDate;
    public int numberOfSeason;
    public int numberOfEpisode;
    public List<Integer> writers;
    public List<String> genres;


    public TvSeries(String... args) {
        super(args);
        this.genres = Helper.stringToArray(args[7]);
        this.writers = Helper.stringToArrayInt(args[8]);
        this.startDate = args[9];
        this.endDate = args[10];
        this.numberOfSeason = Integer.parseInt(args[11]);
        this.numberOfEpisode = Integer.parseInt(args[12]);


    }
}
