/* *****************************************************************************
 *  Name: Marcio TEDESCO
 *  Date: 29/03/2020
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int size;
    private int insertCursor;
    private int nullHit;
    private int nullCount;
    private final int NULL_HIT_THRESHOLD = 10;
    private final double NULL_COUNT_TRESHOLD = 0.5;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        insertCursor = 0;
        nullHit = 0;
        nullCount = 0;
        array = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        //old implementation
        //if (size == array.length) resize(2 * array.length, insertCursor);

        //if insertCursor is at the end of arrayLenght -> resize
        if (insertCursor == array.length) {
            insertCursor = resize(2 * array.length, insertCursor);
            size = insertCursor;
        }

        //add at the insertCursor position
        array[insertCursor++] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();

        //resize if
        if (nullCount / (double) array.length >= NULL_COUNT_TRESHOLD) {
            insertCursor = resize(array.length, insertCursor);
            nullCount = 0;
        }

        int randomElement = 0;
        Item item = null;
        while (item == null) {
            randomElement = StdRandom.uniform(size);
            item = array[randomElement];

            //count nullHits if item is null, verify if too much nullHits if yes, resize
            if (item == null)
                nullHit++;
            else if (nullHit == NULL_HIT_THRESHOLD)
                resize(array.length, insertCursor);
        }

        size--;
        nullCount++;
        //removes element from the array
        array[randomElement] = null;

        //resize array
        if (size > 0 && size == array.length / 4) {
            insertCursor = resize(array.length / 2, insertCursor);
            nullCount = 0;
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();

        return array[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rqueue = new RandomizedQueue<>();
        //Test 1: enqueuing
        rqueue.enqueue(1);
        rqueue.enqueue(2);
        rqueue.enqueue(3);
        rqueue.enqueue(4);
        rqueue.enqueue(5);
        rqueue.enqueue(6);
        rqueue.enqueue(7);
        rqueue.enqueue(8);
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());


        //Test 2: dequeuing
        rqueue.enqueue(1);
        rqueue.enqueue(2);
        rqueue.enqueue(3);
        System.out.println(rqueue.dequeue());

        rqueue.enqueue(4);
        rqueue.enqueue(5);
        rqueue.enqueue(6);
        System.out.println(rqueue.dequeue());

        rqueue.enqueue(7);
        rqueue.enqueue(8);
        rqueue.enqueue(9);
        rqueue.enqueue(10);
        rqueue.enqueue(11);
        rqueue.enqueue(12);
        rqueue.enqueue(13);
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());
        System.out.println(rqueue.dequeue());

        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }


    }

    /**
     * Resize and return inserting point
     *
     * @param max
     * @param insertCursor
     * @return
     */
    private int resize(int max, int insertCursor) {
        //Create a new array of size max > size to dynamically extend array
        Item[] temp = (Item[]) new Object[max];
        int previousInsertCursor = insertCursor;
        insertCursor = 0;
        for (int i = 0, j = 0; i < previousInsertCursor; i++) {
            if (array[i] != null) {
                temp[j++] = array[i];
                insertCursor++;
            }
        }
        array = temp;

        return insertCursor;
    }

    private class ArrayIterator implements Iterator<Item> {

        private int i = size;

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (i < 0)
                throw new NoSuchElementException();

            return array[--i];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
