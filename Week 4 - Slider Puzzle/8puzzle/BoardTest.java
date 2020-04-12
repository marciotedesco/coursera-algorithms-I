import edu.princeton.cs.algs4.In;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class BoardTest {

    @Test
    public void hamming_puzzle04_4() {
        Board board = getBoardFromFile("puzzle04.txt");
        System.out.println(board.toString());
        assertEquals(4, board.hamming());
    }

    @Test
    public void mahattan_puzzle04_8() {
        Board board = getBoardFromFile("puzzle04.txt");
        System.out.println(board.toString());
        assertEquals(4, board.manhattan());
    }

    private Board getBoardFromFile(String fileName) {
        In in = new In(fileName);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        return new Board(tiles);
    }
}