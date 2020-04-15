/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {

    private int moves;
    private final boolean isSolvable;
    private Stack<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        Board twinBoard = initial.twin();

        MinPQ<SearchNode> priorityQueueTwin = new MinPQ<>();
        MinPQ<SearchNode> priorityQueueInitial = new MinPQ<>();

        priorityQueueInitial.insert(new SearchNode(initial, 0, null));
        priorityQueueTwin.insert(new SearchNode(twinBoard, 0, null));

        SearchNode minSearchNodeInitial;
        SearchNode minSearchNodeTwin;

        while (!priorityQueueInitial.min().board.isGoal() && !priorityQueueTwin.min().board
                .isGoal()) {
            //Try to solve initial
            //debug(priorityQueueInitial, priorityQueueInitial.min().moves);

            minSearchNodeInitial = priorityQueueInitial.delMin();

            for (Board b : minSearchNodeInitial.board.neighbors()) {
                if (minSearchNodeInitial.previousNodeSearch == null || !b
                        .equals(minSearchNodeInitial.previousNodeSearch.board)) {
                    priorityQueueInitial
                            .insert(new SearchNode(b, minSearchNodeInitial.moves + 1,
                                                   minSearchNodeInitial));
                }
            }

            //Try to solve twin
            //debug(priorityQueue, moves);

            minSearchNodeTwin = priorityQueueTwin.delMin();

            for (Board b : minSearchNodeTwin.board.neighbors()) {
                if (minSearchNodeTwin.previousNodeSearch == null || !b
                        .equals(minSearchNodeTwin.previousNodeSearch.board)) {
                    priorityQueueTwin.insert(new SearchNode(b, minSearchNodeTwin.moves + 1,
                                                            minSearchNodeTwin));
                }
            }
        }
        isSolvable = priorityQueueInitial.min().board.isGoal();
        if (isSolvable) {
            moves = priorityQueueInitial.min().moves;
            solution = new Stack<>();
            SearchNode currentSearchNode = priorityQueueInitial.min();
            while (currentSearchNode != null) {
                solution.push(currentSearchNode.board);
                currentSearchNode = currentSearchNode.previousNodeSearch;
            }
        }
        else
            moves = -1;

        //debug(priorityQueue, moves);
    }

    private void debug(MinPQ<SearchNode> priorityQueue, int movesStep) {
        System.out.println("Step " + movesStep + ":");
        priorityQueue.forEach(s ->
                              {
                                  System.out.println("priority  = " + s.priority + "  ");
                                  System.out.println("moves     = " + s.moves + "  ");
                                  System.out.println("manhattan = " + s.manhatanDistance + "  ");
                                  System.out.println(s.board);
                              });
        System.out.println("---------------------------------");
    }

    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        return this.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return this.solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            StdOut.println(solver.solution().iterator().next());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    private static class SearchNode implements Comparable<SearchNode> {
        Board board;
        int moves;
        SearchNode previousNodeSearch;
        int manhatanDistance;
        int priority;

        public SearchNode(Board board, int moves, SearchNode previousNodeSearch) {
            this.board = board;
            this.previousNodeSearch = previousNodeSearch;
            this.moves = moves;
            this.manhatanDistance = board.manhattan();
            //calculate priority
            this.priority = this.manhatanDistance + this.moves;
        }

        public int compareTo(SearchNode other) {
            if (other == null)
                return 1;

            if (this.priority == other.priority)
                return 0;

            return this.priority > other.priority ? 1 : -1;
        }
    }
}