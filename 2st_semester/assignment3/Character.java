import java.time.temporal.ValueRange;
import java.util.HashMap;

public abstract class Character {
    public String id;
    public int HP;
    public int AP;
    public int stepCount;
    public Position p;
    public int sideID;
    public boolean attackEveryStep;
    public int defaultHp;

    public Character(String id, int HP, int AP, int stepCount, int x, int y, int sideID, boolean attackEveryStep, int defaultHp) {
        this.id = id;
        this.HP = HP;
        this.AP = AP;
        this.stepCount = stepCount;
        this.p = new Position(x, y);
        this.sideID = sideID;
        this.attackEveryStep = attackEveryStep;
        this.defaultHp = defaultHp;
    }

    public void attack(int i) {

        /*
            Method name:
                - attack

            What does the method do?
            This method calculates the given damage against enemies which are nearby 1 square

            @params:
                - i (int) : currentStep
            @return:
                - None
         */

        HashMap<String, Character> chr = Data.characters;

        int step = (i / 2) + 1;

        if (step == this.stepCount || this.attackEveryStep) {

            if (!(this instanceof Elf && step == this.stepCount)) {

                ValueRange range = java.time.temporal.ValueRange.of(0, 1);

                for (Character ch : chr.values()) {

                    int x_distance = Math.abs(ch.p.x - this.p.x);

                    int y_distance = Math.abs(ch.p.y - this.p.y);

                    if (range.isValidIntValue(x_distance) && range.isValidIntValue(y_distance)) {

                        if (ch.sideID != this.sideID) {

                            ch.HP -= this.AP;
                            Data.characters.put(ch.id, ch);
                        }


                    }

                }

            }


        }

        Data.characters = chr;

        Controller.deathCheck();
    }


    public void fightToTheDeath(Character enemy) {

        /*
            Method name:
                - fightToTheDeath

            What does the method do?
            This method calculates the result of any Character against an enemy which one is alive if they are fighting

            @params:
                - Character enemy
            @return:
                - None
         */


        Position p = enemy.p;


        if (this.HP > enemy.HP) {

            this.HP -= enemy.HP;
            enemy.HP = 0;


        } else if (this.HP == enemy.HP) {

            enemy.HP = 0;
            this.HP = 0;

            Data.table[p.y][p.x] = null;
            Data.table[this.p.y][this.p.x] = null;

        } else {

            enemy.HP -= this.HP;
            this.HP = 0;

            Data.table[this.p.y][this.p.x] = null;
        }

        Data.characters.put(enemy.id, enemy);
        Data.characters.put(this.id, this);

        Controller.deathCheck();

        if (this.HP > 0) move(p);

    }

    public void singleAttack(Character enemy) {

        /*
            Method name:
                - singleAttack

            What does the method do?
            This method calculates the first given damage to the target which character will fight to death
            @params:
                - Character enemy:
            @return:
                - None
         */


        HashMap<String, Character> chr = Data.characters;

        enemy.HP -= this.AP;

        chr.put(enemy.id, enemy);

        Data.characters = chr;

        Controller.deathCheck();
    }


    public void move(Position p) {

        /*
            Method name:
                - move

            What does the method do?
            This method move Character inside the table to specific point. It changes table index value.
            @params:
                - Position p:
            @return:
                - None
         */


        HashMap<String, Character> chr = Data.characters;

        Data.table[this.p.y][this.p.x] = null;

        this.p = p;

        Data.table[p.y][p.x] = this;

        chr.put(this.id, this);

        Data.characters = chr;
    }
}
