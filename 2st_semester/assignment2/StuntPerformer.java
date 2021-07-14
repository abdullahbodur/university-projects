package cs.hacettepe.bbm102;

import java.util.List;

public class StuntPerformer extends Actor {

    public List<Integer> realActorId;

    public StuntPerformer(String... args) {
        super(args);
        this.realActorId = Helper.stringToArrayInt(args[5]);
    }
}
