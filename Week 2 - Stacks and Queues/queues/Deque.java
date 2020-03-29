/* *****************************************************************************
 *  Name: Marcio TEDESCO
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private Node first, last = null;
    private int size = 0;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;

        if (size == 0)
            last = first;
        else
            oldFirst.previous = first;

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node newLast = new Node();
        newLast.item = item;
        newLast.previous = last;

        if (size == 0) {
            first = newLast;
            last = first;
        }
        else {
            last.next = newLast;
            last = newLast;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException();

        Node oldFirst = first;
        first = oldFirst.next;
        //avoids loitering?
        oldFirst.next = null;
        size--;

        //case when there was only one element in the deque and now it is empty
        if (size == 0)
            last = null;

        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException();

        Node oldLast = last;

        last = oldLast.previous;
        oldLast.previous = null;

        if (last != null)
            last.next = null;

        size--;

        //case when there was only one element in the deque and now it is empty
        if (size == 0)
            first = null;

        return oldLast.item;


    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null)
                throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        System.out.println("Create Deque");
        Deque<Integer> deque = new Deque<>();

        //Test 1: Add 2 items in the beginning of the queue and 1 in the end
        //Remove 2 items from the end and 1 from the beginning
        System.out.println("Test 1");
        System.out.println("Add 1 to the beginning of deque");
        deque.addFirst(1);
        System.out.println("Add 2 to the beginning of deque");
        deque.addFirst(2);
        System.out.println("Add 3 to the end of deque");
        deque.addLast(3);

        System.out.println("Deque size: " + deque.size());
        assert (deque.size() == 0);

        System.out.println("Remove last element from deque: " + deque.removeLast());
        System.out.println("Remove last element from deque: " + deque.removeLast());
        System.out.println("Remove first element from deque: " + deque.removeFirst());

        System.out.println("Deque size: " + deque.size());
        assert (deque.size() == 0);

        //Test2: Add 4 items in the beginning and remove 4 from the end
        System.out.println("Test 2");
        System.out.println("Add 10 to the beginning of deque");
        deque.addFirst(10);
        System.out.println("Add 15 to the beginning of deque");
        deque.addFirst(15);
        System.out.println("Add 20 to the beginning of deque");
        deque.addFirst(20);
        System.out.println("Add 25 to the beginning of deque");
        deque.addFirst(25);

        System.out.println("Deque size: " + deque.size());
        assert (deque.size() == 4);

        System.out.println("Remove last element from deque: " + deque.removeLast());
        System.out.println("Remove last element from deque: " + deque.removeLast());
        System.out.println("Remove last element from deque: " + deque.removeLast());
        System.out.println("Remove last element from deque: " + deque.removeLast());
        System.out.println("Deque size: " + deque.size());
        assert (deque.size() == 0);


        //Test3: Add 1 item at the end, Remove 1 item at the beginning, Add 1 item at the end
        System.out.println("Add 1000 to the end of deque");
        deque.addLast(1000);

        System.out.println("Remove first element from deque: " + deque.removeFirst());

        System.out.println("Add 2000 to the end of deque");
        deque.addLast(2000);

        System.out.println("Deque size: " + deque.size());
        assert (deque.size() == 1);

        //Test4: Add 1 item at the beginning, Remove 1 item at the end, Add 1 item at the beginning
        System.out.println("Add 1000 to the end of deque");
        deque.addFirst(555);

        System.out.println("Remove first element from deque: " + deque.removeFirst());

        System.out.println("Add 777 to the end of deque");
        deque.addLast(555);

        System.out.println("Deque size: " + deque.size());
        assert (deque.size() == 1);
    }


}


