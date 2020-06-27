import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * A specific <code>Map</code> to store <code>Sting, Integer</code> associations.
 *
 * @author Marcel Turcotte (turcotte@eecs.uottawa.ca)
 */
public class Dictionary implements Map<String, Integer> {

    private final static int INITIAL_CAPACITY = 10;
    private final static int INCREMENT = 5;
    private int count;

    private Pair[] elems;

    public Dictionary() {
        elems = new Pair[INITIAL_CAPACITY];
        count = 0;
    }

    @Override
    public void put(String key, Integer value) {

        if (count == elems.length) {
            increaseCapacity();
        }

        // Similarly to the array-based implementation
        // of a stack, I am adding elements at the end
        elems[count] = new Pair(key, value);
        count++;
    }

    private void increaseCapacity() {

        Pair[] copy;
        copy = new Pair[elems.length + INCREMENT];
        System.arraycopy(elems, 0, copy, 0, count);

        elems = copy;
    }

    @Override
    public boolean contains(String key) {

        boolean found = false;
        int pos = count - 1;

        // Searching the dictionary from top to bottom,
        // similarly to the implementaiton of a stack.
        while (pos >= 0 && !found) {
            if (elems[pos].key.equals(key)) {
                found = true;
            } else {
                pos--;
            }
        }

        return found;
    }

    @Override
    public Integer get(String key) {

        boolean found = false;
        int pos = count - 1;

        // Searching the dictionary from top to bottom,
        // similarly to the implementaiton of a stack.
        while (pos >= 0 && !found) {
            if (elems[pos].key.equals(key)) {
                found = true;
            } else {
                pos--;
            }
        }

        return elems[pos].value;
    }

    @Override
    public void replace(String key, Integer value) {

        boolean found = false;
        int pos = count - 1;

        // Searching the dictionary from top to bottom,
        // similarly to the implementaiton of a stack.
        while (pos >= 0 && !found) {
            if (elems[pos].key.equals(key)) {
                found = true;
            } else {
                pos--;
            }
        }

        elems[pos].value = value;
    }

    @Override
    public Integer remove(String key) {

        boolean found = false;
        int pos = count - 1;

        // Searching the dictionary from top to bottom,
        // similarly to the implementaiton of a stack.
        while (pos >= 0 && !found) {
            if (elems[pos].key.equals(key)) {
                found = true;
            } else {
                pos--;
            }
        }

        Integer saved = elems[pos].value;

        while (pos < count - 1) {
            elems[pos] = elems[pos + 1];
            pos++;
        }

        count--;

        elems[count] = null; // scrubbing

        return saved;
    }

    @Override
    public String toString() {
        Pair[] reverse;
        reverse = new Pair[count];
        for (int i = 0; i < count; i++) {
            reverse[i] = elems[count - i - 1];
        }
        return "Dictionary: {elems = " + Arrays.toString(reverse) + "}";
    }

}
