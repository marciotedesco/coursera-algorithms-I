/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public final class Solver {


    private int moves;
    private Board initialBoard;
    private MinPQ<SearchNode> priorityQueue;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        initialBoard = initial;
        moves = 0;
        priorityQueue = new MinPQ<>();
        priorityQueue.insert(new SearchNode(initial, moves, null));

        SearchNode minSearchNode; //top of the priority queue, to be removed in each interaction
        SearchNode previousSearchNode; //keeps track of the previousSearchNode to avoid adding it

        while (!priorityQueue.min().board.isGoal()) {
            moves++;
            minSearchNode = priorityQueue.delMin();
            previousSearchNode = minSearchNode;
            for (Board b : minSearchNode.board.neighbors()) {
                if (!b.equals(previousSearchNode.board)) {
                    priorityQueue.insert(new SearchNode(b, moves, previousSearchNode));
                }
            }
        }
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
        Queue<Board> result = new Queue<>();
        for (SearchNode searchNode : priorityQueue) {
            result.enqueue(searchNode.board);
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

        public SearchNode(Board board, int moves, SearchNode previousNodeSearch) {
            this.board = board;
            this.previousNodeSearch = previousNodeSearch;
            this.moves = moves;
            this.manhatanDistance = board.manhattan();
        }

        public int compareTo(SearchNode o) {
            if (o == null)
                return 1;

            if (this.manhatanDistance + this.moves == o.manhatanDistance + o.moves)
                return 0;

            return this.manhatanDistance + this.moves > o.manhatanDistance + o.moves ? 1 : -1;
        }
    }
}