/** Linked List Deque Class.
 *
 *
 *@author Henry Kang 09/14/20
 **/
public class LinkedListDeque<T> {

    /** Indicate the size of Linked list.*/
    private int size;

    /** Setting for sentinel node.*/
    private LNode sentinel;

    /** prev, next node and item for LNode. */
    private class LNode {
        /** Setting for prev. */
        private LNode prev;
        /** Setting for next. */
        private LNode next;
        /** Setting for item. */
        private T item;


        /**
         * Setting for prev, next and item.
         * @param p     indicate previous node.
         * @param i     indicate type T item.
         * @param n     indicate next node.
         */
        private LNode(LNode p, T i, LNode n) {
            prev = p;
            next = n;
            item = i;
        }
    }

    /** Create empty linked-list-deque such like constructor.*/
    public LinkedListDeque() {
        sentinel = new LNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }



    /** Fucntion that add item.
     * @param item      Type T then, insert item in first place.*/
    public void addFirst(T item) {
        sentinel.next = new LNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /** Function that add item from last.
     * * @param item      Type T then, insert item in first place.*/
    public void addLast(T item) {
        sentinel.prev = new LNode(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }


    /** Function that check if it's empty or not.
     * @return boolean the Linked List is whether empty or not. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Function that check the size of list.
     * @Return the size of list. */
    public int size() {
        if (size < 0) {
            return 0;
        }
        return this.size;
    }

    /** Print out the all of type T items.*/
    public void printDeque() {
        LNode temp = sentinel.next;
        while (temp.next != sentinel) {
            System.out.print(temp.item + " ");
            sentinel = sentinel.next;
        }
        System.out.println("");
    }

    /** Function that remove first element of Linked list.
     * @return return type T element of item which is deleted.*/
    public T removeFirst() {
        T temp = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return temp;
    }

    /** Function that remove last element of Linked list.
     * @return return type T element of item which is deleted.*/
    public T removeLast() {
        T temp = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return temp;
    }

    /** Function that to get index th item.
     * @param index     to get index th item.
     * @return return type T element of item which is selected.*/
    public T get(int index) {
        LNode temp = sentinel.next;
        while (index != 0) {
            temp = temp.next;
            index -= 1;
        }
        return temp.item;
    }

    /** Function that to get index th item.
     * @param index     to get index th item.
     * @return return type T element of item which is selected.*/
    public T getRecursive(int index) {
        if (index == 0) {
            return sentinel.next.item;
        } else {
            return recursiveHelper(index, sentinel.next);
        }
    }

    /** Function that to help recursive item.
     * @param index     to get index th item which is decreased.
     * @param temp      to put into next Node for recursion.
     * @return return recursive function.*/
    private T recursiveHelper(int index, LNode temp) {
        if (index == 0) {
            return temp.item;
        } else {
            return recursiveHelper(index - 1, temp.next);
        }
    }
}
