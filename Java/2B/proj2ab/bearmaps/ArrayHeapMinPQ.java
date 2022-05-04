package bearmaps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;


/** ArrayHeapMinPQ Class.
 * @author Henry Kang.*/
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    /**Initiate for node.*/
    private ArrayList<Node> items;
    /**Initiate for hashMaps.*/
    private HashMap<T, Integer> maps;

    /**Node class.*/
    private class Node {
        /**Generic form of T.*/
        private T item;
        /**Priority double.*/
        private double priority;

        /**Constructor.
         * @param p priority.
         * @param i T item.*/
        Node(T i, double p) {
            item = i;
            priority = p;
        }
        /** return get Item.
         * @return item.*/
        T getItem() {
            return item;
        }
        /**return priority.
         * @return priority.*/
        double getPriority() {
            return priority;
        }

        /**Setting priority.
         * @param p priority.*/
        void setPriority(double p) {
            this.priority = p;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((ArrayHeapMinPQ.Node) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }

    }

    /**Initiate ArrayHeapMinPQ.*/
    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        maps = new HashMap<>();
    }

    /** Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null.
     * complete
     * @param item item.
     * @param priority priority.*/
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        items.add(new Node(item, priority));
        maps.put(item, size() - 1);
        swimDown(size() - 1);


    }
    /** Re-range from the getting index.
     * @param lower putting index from hash and list. complete(Downward)*/
    private void swimDown(int lower) {
        while (lower > 0) {
            int upper = (lower - 1) / 2;
            if (items.get(lower).getPriority()
                    > items.get(upper).getPriority()) {
                break;
            }
            switchNode(lower, upper);
            lower = upper;
        }
    }
    /** Re-range from the getting index.
     * @param upper putting index from hash and list. complete(upward)*/
    private void swimUp(int upper) {
        while (upper * 2 + 1 < size()) {
            int lower1 = upper * 2 + 1;
            int lower2 = (upper + 1) * 2;
            if (lower2 < size()) {
                if (items.get(lower2).getPriority()
                        < items.get(lower1).getPriority()) {
                    lower1 = lower2;
                }
            }
            if (items.get(upper).getPriority()
                    < items.get(lower1).getPriority()) {
                break;
            }
            switchNode(upper, lower1);
            upper = lower1;
        }
    }

    /** Returns true if the PQ contains the given item.
     * complete*/
    @Override
    public boolean contains(T item) {
        return maps.containsKey(item);
    }

    /** Returns the minimum item.
     * Throws NoSuchElementException if the PQ is empty.
     * complete */
    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return items.get(0).getItem();
    }

    /** Removes and returns the minimum item.
     * Throws NoSuchElementException if the PQ is empty.
     * @return return smallest value.
     * incomplete.*/
    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T temp = getSmallest();
        switchNode(0, size() - 1);
        items.remove(size() - 1);

        maps.remove(temp);
        swimUp(0);
        return temp;
    }

    /** Helper function for switching smallest value.
     * @param i list index.
     * @param j list index. */
    private void switchNode(int i, int j) {
        Node temp = items.get(j);
        items.set(j, items.get(i));
        items.set(i, temp);
        maps.put(items.get(i).getItem(), i);
        maps.put(items.get(j).getItem(), j);
    }

    /** Returns the number of items in the PQ.
     * @return size of array.*/
    @Override
    public int size() {
        return items.size();
    }

    /** Changes the priority of the given item.
     * Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int index = maps.get(item);
        double prePriority = items.get(index).getPriority();
        items.get(index).setPriority(priority);
        if (priority > prePriority) {
            swimUp(index);
        } else {
            swimDown(index);
        }
    }
}
