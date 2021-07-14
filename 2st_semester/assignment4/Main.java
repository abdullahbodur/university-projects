public class Main {

    public static void main(String[] args) {
	// write your code here
        Data.parts_txt = args[0];
        Data.items_txt = args[1];
        Data.tokens_txt = args[2];
        Data.tasks_txt = args[3];
        Data.output_txt = args[4];


        Controller.start();
    }
}
