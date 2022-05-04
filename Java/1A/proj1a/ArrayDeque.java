/** Array Deque Class.
 *
 *
 *@author Henry Kang 09/14/20
 **/
public class ArrayDeque<T> {
    /** Indicate the size of array deque.*/
    private int size;

    /** Indicate the element of array which is called item.*/
    private T[] items;

    /**Indicate the concept of next pointer for first.*/
    private int nextFirst;

    /**Indicate the concept of next pointer for last.*/
    private int nextLast;

    /** Create empty Array Deque List.*/
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
    }

    /** Function that resizing the array size.
     * @param capacity      it will decide how big or small size of array.*/
    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];

        int tempPosition = (nextFirst + 1) % items.length;
        for (int i = 0; i < size; i++) {
            temp[i] = items[tempPosition];
            tempPosition = (tempPosition + 1) % items.length;
        }
        items = temp;
        nextFirst = temp.length - 1;
        nextLast = size;
    }

    /** Function that add first the T type of element.
     * @param item      the value to be inserted in first.*/
    public void addFirst(T item) {
        if (items.length == size) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        size++;
        nextFirst = (nextFirst - 1 + items.length) % items.length;

    }

    /** Function that add last the T type of element.
     * @param item      the value to be inserted in last.*/
    public void addLast(T item) {
        if (items.length == size) {
            resize(size * 2);
        }
        items[nextLast] = item;
        size++;
        nextLast = (nextLast + 1 + items.length) % items.length;
    }

    /** Function that check empty array or not.
     * @return      return the boolean whether it's empty array or not.*/
    public boolean isEmpty() {
        return size == 0;
    }

    /** Function that return the size of array.
     * @return      return the size of array.*/
    public int size() {
        return size;
    }

    /** Print out the all of type T items.*/
    public void printDeque() {
        int tempFirst = (nextFirst + 1 + items.length) % items.length;
        for (int i = 0; i < size; i++) {
            System.out.print(items[tempFirst] + " ");
            tempFirst = (tempFirst + 1 + items.length) % items.length;
        }
        System.out.println();
    }

    /** Function that remove first element of Linked list.
     * @return return type T element of item which is deleted.*/
    public T removeFirst() {
        if (size <= 0) {
            return null;
        }
        nextFirst = (nextFirst + 1 + items.length) % items.length;
        T removeResult = items[nextFirst];
        items[nextFirst] = null;
        size--;

        if (size >= 16 &&  ((double) size / items.length) < 0.25) {
            resize(items.length / 2);
        }
        return removeResult;
    }

    /** Function that remove last element of Linked list.
     * @return return type T element of item which is deleted.*/
    public T removeLast() {
        if (size <= 0) {
            return null;
        }
        nextLast = (nextLast - 1 + items.length) % items.length;
        T removeResult = items[nextLast];
        items[nextLast] = null;
        size--;

        if (size >= 16 && ((double) size / items.length) < 0.25) {
            resize(items.length / 2);
        }
        return removeResult;
    }

    /** Function that to get index th item.
     * @param index     to get index th item.
     * @return return type T element of item which is selected.*/
    public T get(int index) {
        if (index > size) {
            return null;
        }
        int tempNext = (nextFirst + 1 + index) % items.length;
        return items[tempNext];
    }
}
