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
    private final Board initialBoard;
    private final MinPQ<SearchNode> priorityQueue = new MinPQ<>();

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        initialBoard = initial;
        moves = 0;
        priorityQueue.insert(new SearchNode(initial, moves, null));
        SearchNode minSearchNode; //top of the priority queue, to be removed in each interaction
        SearchNode previousSearchNode; //keeps track of the previousSearchNode to avoid adding it

        while (!priorityQueue.min().board.isGoal()) {
            debug(priorityQueue, moves);
            moves++;
            minSearchNode = priorityQueue.delMin();
            //previousSearchNode = minSearchNode;
            for (Board b : minSearchNode.board.neighbors()) {
                if (minSearchNode.previousNodeSearch == null || !b
                        .equals(minSearchNode.previousNodeSearch.board)) {
                    priorityQueue.insert(new SearchNode(b, moves, minSearchNode));
                }
            }
        }
        debug(priorityQueue, moves);
    }

    private void debug(MinPQ<SearchNode> priorityQueue, int moves) {
        System.out.println("Step " + moves + ":");
        priorityQueue.forEach(s ->
                              {
                                  System.out.println("priority  = " + s.priority + "  ");
                                  System.out.println("moves     = " + s.moves + "  ");
                                  System.out.println("manhattan = " + s.manhatanDistance + "  ");
                                  System.out.println(s.board);
                              });
        System.out.println("---------------------------------");
    }

    // is the initial board solvable? (see below)
    //That is one of the few techniques that can be used to show that a board can/can not be solved.
    // Given a twin board, either original or twin board will be solvable but not both. Proof is not
    // trivial/outside scope of this course.
    //
    // As defined in specification, you try to solve both of them, alternating one step at time on
    // each and stop when either of these two boards is solved. If it is a twin board which was
    // solved then original board has no solution.
    public boolean isSolvable() {
        Board twinBoard = this.initialBoard.twin();
        System.out.println("Twin board");
        System.out.println(twinBoard);
        int movesTwin = 0;
        int movesInitial = 0;
        MinPQ<SearchNode> priorityQueueTwin = new MinPQ<>();
        MinPQ<SearchNode> priorityQueueInitial = new MinPQ<>();

        priorityQueueInitial.insert(new SearchNode(initialBoard, movesInitial, null));
        priorityQueueTwin.insert(new SearchNode(twinBoard, movesTwin, null));

        SearchNode minSearchNodeInitial;
        SearchNode previousSearchNodeInitial;

        SearchNode minSearchNodeTwin;
        SearchNode previousSearchNodeTwin;

        while (!priorityQueueInitial.min().board.isGoal() && !priorityQueueTwin.min().board
                .isGoal()) {
            //Try to solve initial
            movesInitial++;
            minSearchNodeInitial = priorityQueueInitial.delMin();
            previousSearchNodeInitial = minSearchNodeInitial;
            for (Board b : minSearchNodeInitial.board.neighbors()) {
                if (!b.equals(previousSearchNodeInitial.board)) {
                    priorityQueueInitial
                            .insert(new SearchNode(b, moves, previousSearchNodeInitial));
                }
            }

            //Try to solve twin
            movesTwin++;
            minSearchNodeTwin = priorityQueueTwin.delMin();
            previousSearchNodeTwin = minSearchNodeTwin;
            for (Board b : minSearchNodeTwin.board.neighbors()) {
                if (!b.equals(previousSearchNodeTwin.board)) {
                    priorityQueueTwin.insert(new SearchNode(b, moves, previousSearchNodeTwin));
                }
            }
        }

        return priorityQueueInitial.min().board.isGoal();
    }

    // min number of moves to solve initial board
    public int moves() {
        return this.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> result = new Stack<>();
        for (SearchNode searchNode : priorityQueue) {
            result.push(searchNode.board);
        }
        return result;
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