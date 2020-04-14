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
    private int[] zeroPosition; //TODO: remove it
    private final int manhantanDistance;
    private Board twin;
    private final int hamming;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        board = new int[tiles.length][tiles.length];
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

        //calculates Manhatan distance
        manhantanDistance = this.calculateManhattan();
        hamming = this.calculateHamming();

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
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhantanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != goalValue(i, j, board.length))
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

        if (!Arrays.deepEquals(this.board, otherBoard.board))
            return false;

        //should I replace here by Arrays.deepEquals(a,b) ?
        //for (int i = 0; i < board.length; i++) {
        //    for (int j = 0; j < board.length; j++) {
        //        if (board[i][j] != otherBoard.board[i][j])
        //            return false;
        //    }
        //}

        //for (int i = 0; i < this.board.length; i++) {
        //    if (!Arrays.deepEquals(this.board, otherBoard.board))
        //        return false;
        //}


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

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twin != null)
            return twin;

        int[][] twinBoardTiles = new int[board.length][board.length];

        for (int i = 0; i < twinBoardTiles.length; i++) {
            for (int j = 0; j < twinBoardTiles.length; j++) {
                twinBoardTiles[i][j] = board[i][j];
            }
        }

        int i, j, p, k;
        i = j = p = k = 0;
        while (twinBoardTiles[i][j] == twinBoardTiles[p][k] || twinBoardTiles[i][j] == 0
                || twinBoardTiles[p][k] == 0) {
            i = StdRandom.uniform(twinBoardTiles.length);
            j = StdRandom.uniform(twinBoardTiles.length);
            p = StdRandom.uniform(twinBoardTiles.length);
            k = StdRandom.uniform(twinBoardTiles.length);
        }
        swap(twinBoardTiles, i, j, p, k);

        twin = new Board(twinBoardTiles);

        return twin;
    }

    /**
     * Swap point on (i,j) by (o,k) one
     *
     * @param tiles
     * @param i
     * @param j
     * @param p
     * @param k
     */
    private void swap(int[][] tiles, int i, int j, int p, int k) {
        int temp = tiles[i][j];
        tiles[i][j] = tiles[p][k];
        tiles[p][k] = temp;
    }

    private int calculateHamming() {
        int count = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && board[i][j] != goalValue(i, j, board.length))
                    count++;
            }
        }
        return count;
    }

    private int calculateManhattan() {
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
        return i == j && j == n - 1 ? 0 : n * i + j + 1;
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

    private int[][] cloneBoard(int[][] boardToClone) {
        int[][] clonedBoard = new int[boardToClone.length][boardToClone.length];

        for (int i = 0; i < boardToClone.length; i++) {
            for (int j = 0; j < boardToClone.length; j++) {
                clonedBoard[i][j] = boardToClone[i][j];
            }
        }

        return clonedBoard;
    }


}
