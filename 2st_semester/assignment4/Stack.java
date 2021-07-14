import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings("unchecked")
public class Stack<T> {

    private T[] stack;


    public Stack(Class<T> cls) {
        this.stack = (T[]) Array.newInstance(cls, 0);
    }


    public void push(T v) {
        /*
            Method Name : push

            Description :
                It adds new T item to first order.
                If any item need from this stack, lastly added item will given.

            @return:
                - None
         */
        this.stack = Arrays.copyOf(this.stack, this.stack.length + 1);

        this.stack[this.stack.length - 1] = v;
    }


    public void pop() {

        /*
            Method Name : pop

            Description :
                It removes lastly added item

            @return:
                - None
         */

        this.stack[this.stack.length - 1] = null;

        this.stack = Arrays.copyOf(this.stack, this.stack.length - 1);

    }

    public T[] getAll() {
        return reverse(this.stack);
    }

    private T[] reverse(T[] rr) {
        Collections.reverse(Arrays.asList(rr));
        return rr;
    }


}

