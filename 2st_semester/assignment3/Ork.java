import java.time.temporal.ValueRange;
import java.util.HashMap;

public class Ork extends Zorde implements SpecialMove {



    public Ork(String id,int x,int y) {
        super(id,Constants.orkHP , Constants.orkAP, Constants.orkMaxMove,x,y,false,Constants.orkHP);
    }


    @Override
    public void doSpecialMove() {

        HashMap<String,Character> chr = Data.characters;

        ValueRange range = java.time.temporal.ValueRange.of(0, 1);

            for (Character ch : chr.values()) {

                int x_distance = Math.abs(ch.p.x - this.p.x);

                int y_distance = Math.abs(ch.p.y - this.p.y);

                if (range.isValidIntValue(x_distance) && range.isValidIntValue(y_distance)) {

                    if(this.sideID == ch.sideID){
                        ch.HP += 10;

                        if(ch instanceof Ork && ch.HP > Constants.orkHP) ch.HP = Constants.orkHP;

                        if(ch instanceof Troll && ch.HP > Constants.trollHP) ch.HP = Constants.trollHP;

                        if(ch instanceof Goblin && ch.HP > Constants.goblinHP) ch.HP = Constants.goblinHP;
                    }

                }

            }
    }
}
