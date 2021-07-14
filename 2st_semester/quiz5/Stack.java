import java.util.ArrayList;

public class Stack {

    private ArrayList<Integer> stack;


    public Stack() {
        stack = new ArrayList<>();
    }

    public void push(int n){
        this.stack.add(0,n);
    }


    public void pop(){
        this.stack.remove(0);
    }

    public int top(){
        return this.stack.get(0);
    }


    public boolean isFull(){
        return size() == 20;
    }


    public boolean isEmpty(){

        return this.stack.size() == 0;
    }

    public int size(){
        return this.stack.size();
    }


}
