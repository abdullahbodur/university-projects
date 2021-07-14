public class MoveCountException extends Exception{

    public MoveCountException() {
        super("Error : Move sequence contains wrong number of move steps. Input line ignored.\n");
    }

}
