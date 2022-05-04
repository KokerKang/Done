/** Deque interface.
 *
 *
 *@author Henry Kang 09/15/20
 **/
public interface Deque<T> {

    /** overriding method addFirst.
     * @param item which is put into array at first*/
    void addFirst(T item);

    /** overriding method addLast.
     * @param item which is put into array at last*/
    void addLast(T item);

    /** overriding method is_empty.
     * @return      return that empty list or not*/
    default boolean isEmpty() {
        return size() == 0;
    }

    /** overriding method for the size.
     * @return      return size of list*/
    int size();

    /** overriding method printing deque. */
    void printDeque();

    /** overriding method which remove first.
     * @return      return what we removed*/
    T removeFirst();

    /** overriding method which remove last.
     * @return      return what we removed */
    T removeLast();

    /** overriding method addFirst.
     * @param index which is put into array
     * @return      what we want to know*/
    T get(int index);

}
