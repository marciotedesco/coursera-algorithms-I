/* *****************************************************************************
 *  Name: Marcio TEDESCO
 *  Date: 29/03/2020
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args == null || args[0] == null)
            throw new IllegalArgumentException("args is empty");

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rqueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rqueue.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            System.out.println(rqueue.dequeue());
        }


    }
}
