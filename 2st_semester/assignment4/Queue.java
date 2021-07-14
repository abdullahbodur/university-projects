import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings("unchecked")
public class Queue<T> {

    private T[] queue;

    private final Class<T> cls;

    public Queue(Class<T> cls) {
        this.cls = cls;
        this.queue = (T[]) Array.newInstance(cls, 0);
    }

    public void enqueue(T v, boolean sorting) {

        /*
            Method Name : enqueue

            Description :
                It adds new T item to last order.
                This item will given, if items which are added before that are removed.

            @return:
                - None
         */
        this.queue = Arrays.copyOf(this.queue, this.queue.length + 1);

        if (this.queue.length - 1 >= 0) System.arraycopy(this.queue, 0, this.queue, 1, this.queue.length - 1);

        this.queue[0] = v;


        if (sorting) Arrays.sort(this.queue);


    }


    public int size() {
        return this.queue.length;
    }


    public T get(int i) {
        return (T) this.queue[i];
    }

    public void dequeue() {

        this.queue[this.queue.length - 1] = null;

        this.queue = Arrays.copyOf(this.queue, this.queue.length - 1);
    }

    public T[] getAll() {
        return (T[]) this.queue;
    }


    public void remove_index(int index) {

        T[] q = (T[]) Array.newInstance(this.cls, this.queue.length - 1);


        for (int i = 0, j = 0; j < this.queue.length; j++) {

            if (j != index) q[i++] = this.queue[j];
        }

        this.queue = q;

    }
}




