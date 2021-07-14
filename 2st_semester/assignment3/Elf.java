import java.time.temporal.ValueRange;
import java.util.HashMap;

public class Elf extends Calliance implements SpecialMove{


    public Elf( String id, int x, int y) {
        super( id, Constants.elfHP, Constants.elfAP, Constants.elfMaxMove, x, y,true,Constants.elfHP);
    }


    @Override
    public void doSpecialMove() {

        HashMap<String,Character> chr = Data.characters;

        ValueRange range = java.time.temporal.ValueRange.of(0, 2);

        for (Character ch : chr.values()) {

            int x_distance = Math.abs(ch.p.x - this.p.x);

            int y_distance = Math.abs(ch.p.y - this.p.y);


            if (range.isValidIntValue(x_distance) && range.isValidIntValue(y_distance)) {

                if(this.sideID != ch.sideID){
                    ch.HP -= Constants.elfRangedAP;

                    Data.characters.put(ch.id,ch);
                }


            }
        }


    }
}
