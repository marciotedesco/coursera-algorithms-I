/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public final class Board {

    private final int[][] board;
    private final int[][] goal;
    private int[] zeroPosition;
    private int manhantanDistance = Integer.MIN_VALUE;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        board = new int[tiles.length][tiles.length];
        goal = new int[tiles.length][tiles.length];
        zeroPosition = new int[2];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = tiles[i][j];
                if (board[i][j] == 0) {
                    zeroPosition[0] = i;
                    zeroPosition[1] = j;
                }

            }
        }

        int count = 1;
        for (int i = 0; i < goal.length; i++) {
            for (int j = 0; j < goal.length; j++) {
                goal[i][j] = count++;

            }
        }

        //place the blank
        goal[tiles.length - 1][tiles.length - 1] = 0;

        //calculates Manhatan distance
        manhantanDistance = this.manhattan();

    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(board.length + "\n");

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                stringBuilder
                        .append(board[i][j] < 10 ? " " + board[i][j] + " " : board[i][j] + " ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    // board dimension n
    public int dimension() {
        return board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && board[i][j] != goal[i][j])
                    count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhantanDistance != Integer.MIN_VALUE)
            return manhantanDistance;

        int count = 0;
        int[] goalCoordinates;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0) {
                    goalCoordinates = goalCoordinates(board[i][j], board.length);
                    count += calculateDistance(goalCoordinates[0], goalCoordinates[1], i, j);
                }
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != goal[i][j])
                    return false;
            }
        }

        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // null check
        if (y == null)
            return false;

        // self check
        if (this == y)
            return true;

        // type check and cast
        if (getClass() != y.getClass())
            return false;

        Board otherBoard = (Board) y;

        //check if boards have same length
        if (this.board.length != otherBoard.board.length)
            return false;
        //should I replace here by Arrays.deepEquals(a,b) ?
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != otherBoard.board[i][j])
                    return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighborsList = new Stack<>();

        //iterating the 4 neighbors

        //neighbor 1
        if (zeroPosition[0] - 1 >= 0 && zeroPosition[0] - 1 < board.length) {
            int[][] tiles = cloneBoard(board);
            swap(tiles, zeroPosition[0], zeroPosition[1], zeroPosition[0] - 1, zeroPosition[1]);
            neighborsList.push(new Board(tiles));
        }

        //neighbor 2
        if (zeroPosition[1] - 1 >= 0 && zeroPosition[1] - 1 < board.length) {
            int[][] tiles = cloneBoard(board);
            swap(tiles, zeroPosition[0], zeroPosition[1], zeroPosition[0], zeroPosition[1] - 1);
            neighborsList.push(new Board(tiles));
        }

        //neighbor 3
        if (zeroPosition[0] + 1 >= 0 && zeroPosition[0] + 1 < board.length) {
            int[][] tiles = cloneBoard(board);
            swap(tiles, zeroPosition[0], zeroPosition[1], zeroPosition[0] + 1, zeroPosition[1]);
            neighborsList.push(new Board(tiles));
        }

        //neighbor 4
        if (zeroPosition[1] + 1 >= 0 && zeroPosition[1] + 1 < board.length) {
            int[][] tiles = cloneBoard(board);
            swap(tiles, zeroPosition[0], zeroPosition[1], zeroPosition[0], zeroPosition[1] + 1);
            neighborsList.push(new Board(tiles));
        }

        return neighborsList;
    }

    /**
     * Swap point on (i,j) by (o,k) one
     *
     * @param tiles
     * @param i
     * @param j
     * @param o
     * @param k
     */
    private void swap(int[][] tiles, int i, int j, int o, int k) {
        int temp = tiles[i][j];
        tiles[i][j] = tiles[o][k];
        tiles[o][k] = temp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] twinBoardTiles = new int[board.length][board.length];

        for (int i = 0; i < twinBoardTiles.length; i++) {
            for (int j = 0; j < twinBoardTiles.length; j++) {
                twinBoardTiles[i][j] = board[i][j];
            }
        }

        int i, j, o, k;
        i = j = o = k = 0;
        while (twinBoardTiles[i][j] == twinBoardTiles[o][k] || twinBoardTiles[i][j] == 0
                || twinBoardTiles[o][k] == 0) {
            i = StdRandom.uniform(twinBoardTiles.length - 1);
            j = StdRandom.uniform(twinBoardTiles.length - 1);
            o = StdRandom.uniform(twinBoardTiles.length - 1);
            k = StdRandom.uniform(twinBoardTiles.length - 1);
        }
        swap(twinBoardTiles, i, j, o, k);

        return new Board(twinBoardTiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        System.out.println(initial.toString());

        //Test goalCoordinates;
        System.out.println("Test goal coordinates for n: " + 4);
        System.out.println(Arrays.toString(initial.goalCoordinates(1, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(2, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(3, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(4, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(5, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(6, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(7, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(8, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(9, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(10, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(11, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(12, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(13, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(14, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(15, 4)));
        System.out.println(Arrays.toString(initial.goalCoordinates(0, 4)));

        //Test goalCoordinates;
        System.out.println("Test goal coordinates for n: " + 5);
        System.out.println(Arrays.toString(initial.goalCoordinates(1, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(2, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(3, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(4, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(5, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(6, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(7, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(8, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(9, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(10, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(11, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(12, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(13, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(14, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(15, 5)));
        System.out.println(Arrays.toString(initial.goalCoordinates(16, 5)));

        int k = 1;
        for (Board board : initial.neighbors()) {
            System.out.println("Neighbour " + k);
            System.out.println(board.toString());
            k++;
        }

        System.out.println("Board");
        System.out.println(initial.toString());

        System.out.println("Twin board 1");
        System.out.println(initial.twin());
        System.out.println("Twin board 2");
        System.out.println(initial.twin());
        System.out.println("Twin board 3");
        System.out.println(initial.twin());
        System.out.println("Twin board 4");
        System.out.println(initial.twin());
        System.out.println("Twin board 5");
        System.out.println(initial.twin());

        System.out.println("Check hamming");

    }

    /**
     * Return (i,j) coordinates of the value on the corresponding nxn goal board
     *
     * @param value
     * @param n
     * @return
     */
    private int[] goalCoordinates(int value, int n) {
        int[] pair = new int[2];
        pair[0] = 0;
        pair[1] = 0;
        if (value == 0) {
            pair[0] = n - 1;
            pair[1] = n - 1;
        }
        else {
            int originalValue = value;
            while (value > n) {
                value -= n;
                pair[0]++;
            }
            if (value <= 0)
                pair[1] = originalValue - 1;
            else
                pair[1] = value - 1;
        }
        return pair;
    }

    /**
     * Returns the value on the nxn goal board corresponding to (i,j) position
     *
     * @param i
     * @param j
     * @param n
     * @return
     */
    private int goalValue(int i, int j, int n) {
        return n * i + j + 1;
    }

    /**
     * Calculate Manhattan distance between two pairs (i,j) and (o,k)
     *
     * @param i
     * @param j
     * @param o
     * @param k
     * @return
     */
    private int calculateDistance(int i, int j, int o, int k) {
        return Math.abs(i - o) + Math.abs(j - k);
    }

    private int[][] cloneBoard(int[][] board) {
        int[][] clonedBoard = new int[board.length][board.length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                clonedBoard[i][j] = board[i][j];
            }
        }

        return clonedBoard;
    }


}
