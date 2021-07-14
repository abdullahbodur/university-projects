import java.time.temporal.ValueRange;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {


    public static void startGame() {

        // read file and convert this text to array
        String initialTxt = Helper.readFile(Data.initialPath);
        String[] initials = initialTxt.split("\n");

        String commandsTxt = Helper.readFile(Data.commandPath);
        String[] commands = commandsTxt.split("\n");


        // table is always nxn
        int n = Integer.parseInt(initials[1].split("x")[0]);


        Data.characters = new HashMap<>();

        // init character object
        for (int i = 4; i < initials.length; i++) {
            String l = initials[i];

            if (l.equals("ZORDE") || l.isEmpty())
                continue;

            Character ch = characterGenerator(l);

            Data.characters.put(ch.id, ch);

        }

        initTable(n);

        printTable();

        for (String cmd : commands) {

            String[] cmds = cmd.split(" ");

            String id = cmds[0];

            List<Integer> dirs = Arrays.stream(cmds[1].split(";")).
                    map(Integer::parseInt).
                    collect(Collectors.toList());

            Character ch = Data.characters.get(id);

            // current step
            int cs = 0;

            if (ch == null) break;

            if (ch instanceof Ork) ((Ork) ch).doSpecialMove();

            try {

                if (dirs.size() / 2 != ch.stepCount) throw new MoveCountException();


                for (int i = 0; i < dirs.size(); i += 2) {

                    cs++;

                    int x = ch.p.x + dirs.get(i);

                    int y = ch.p.y + dirs.get(i + 1);

                    Position p = new Position(x, y);

                    boolean passed = tableCheck(ch, p);

                    // check the further position is available to move whether not
                    if (passed) {

                        Character targetSquare = Data.table[y][x];

                        if (targetSquare != null) {

                            // if targetSquare is not null, it will be enemy on it.

                            ch.singleAttack(targetSquare);

                            if (Data.characters.get(targetSquare.id) != null) {
                                // will start new attack
                                ch.fightToTheDeath(targetSquare);

                            } else ch.move(p);

                            break;

                        }

                        ch.move(p);

                        ch.attack(i);

                        int step = (i / 2) + 1;

                        if (ch instanceof Elf && ch.stepCount == step)
                            ((Elf) ch).doSpecialMove();

                    } else {

                        break;
                    }


                }

                printTable();
                if (gameOverCheck()) break;


            } catch (MoveCountException | BoundaryException e) {

                if (cs > 1){
                    printTable();
                    if (gameOverCheck()) break;
                }

                Helper.writeFile(Data.outputPath, e.getMessage() + "\n");
            }


        }


    }

    public static void deathCheck() {

        List<Character> characters = new ArrayList<>(Data.characters.values());

        for (Character ch : characters) {
            if (ch.HP <= 0) {
                Data.table[ch.p.y][ch.p.x] = null;
                Data.characters.remove(ch.id);
            }

        }

    }

    private static boolean gameOverCheck() {
        boolean isGameOver = false;

        HashMap<String, Character> chr = Data.characters;

        int chrCount = chr.size();

        long calliancesCount = chr.values().stream().filter(ch -> ch.sideID == 1).count();


        String gmo = "\nGame Finished\n";

        if (calliancesCount == chrCount) {
            isGameOver = true;
            gmo += "Calliance Wins";
        }

        if (calliancesCount == 0) {
            isGameOver = true;
            gmo += "Zorde Wins";
        }


        if (isGameOver) Helper.writeFile(Data.outputPath, gmo);

        return isGameOver;

    }

    private static boolean tableCheck(Character ch, Position p) throws BoundaryException {

        Character[][] table = Data.table;

        boolean passed;
        int li = table.length - 1;

        ValueRange range = java.time.temporal.ValueRange.of(0, li);

        if (range.isValidIntValue(p.x) && range.isValidIntValue(p.y)) {
            passed = true;

            Character nch = table[p.y][p.x];

            if (nch != null) {

                if (nch.sideID == ch.sideID) passed = false;
            }
        } else {

            throw new BoundaryException();
            // throw err
        }


        return passed;
    }

    private static Character characterGenerator(String line) {

        Character character;

        String[] li = line.split(" ");

        String type = li[0];
        String id = li[1];
        int x = Integer.parseInt(li[2]);
        int y = Integer.parseInt(li[3]);

        switch (type) {

            case "ELF":
                character = new Elf(id, x, y);
                break;
            case "DWARF":
                character = new Dwarf(id, x, y);
                break;

            case "HUMAN":
                character = new Human(id, x, y);
                break;
            case "GOBLIN":
                character = new Goblin(id, x, y);
                break;
            case "TROLL":
                character = new Troll(id, x, y);
                break;
            case "ORK":
                character = new Ork(id, x, y);
                break;

            default:
                character = null;

        }


        return character;

    }

    private static void initTable(int n) {

        HashMap<String, Character> chz = Data.characters;

        Data.table = new Character[n][n];


        for (Character ch : chz.values()) {

            Data.table[ch.p.y][ch.p.x] = ch;

        }

    }

    private static void printTable() {

        Character[][] table = Data.table;

        StringBuilder ts = new StringBuilder();

        int n = table.length;
        ts.append(String.join("",Collections.nCopies(n+1,"**"))).append("\n");

        for (Character[] characters : table) {

            ts.append("*");
            for (Character tic : characters) {


                if (tic == null)
                    ts.append("  ");
                else
                    ts.append(tic.id);


            }

            ts.append("*\n");

        }

        ts.append(String.join("",Collections.nCopies(n+1,"**"))).append("\n\n");

        List<Character> orderedChr = Data.characters.values().stream().sorted(Comparator.comparing(character -> character.id)).collect(Collectors.toList());

        for (Character ch : orderedChr) {

            ts.append(String.format("%1$s\t%2$d\t(%3$d)\n", ch.id, ch.HP, ch.defaultHp));

        }

        ts.append("\n");

        Helper.writeFile(Data.outputPath, ts.toString());
    }


}
