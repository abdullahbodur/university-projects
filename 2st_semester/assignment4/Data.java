import java.util.HashMap;
import java.util.Map;

public class Data {

    public static String tasks_txt,items_txt,tokens_txt,parts_txt,output_txt;

    public static Map<String,Part> parts = new HashMap<>();

    public static Queue<Token> tokens = new Queue<>(Token.class);

    public static HashMap<String,Stack<Item> > items = new HashMap<>();

}
