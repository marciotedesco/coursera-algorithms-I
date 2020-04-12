import edu.princeton.cs.algs4.In;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
public class SolverTest {

    @Test
    public void moves_puzzle04_test() {
        Board board = getBoardFromFile("puzzle04.txt");
        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        System.out.println("Iterate solution");
        solver.solution().forEach(b -> System.out.println(b.toString()));
    }

    @Test
    public void moves_puzzle3x3_00_0() {
        Board board = getBoardFromFile("puzzle3x3-00.txt");
        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(0, solver.moves());

    }

    @Test
    public void moves_puzzle3x3_01_1() {
        Board board = getBoardFromFile("puzzle3x3-01.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(1, solver.moves());

    }

    @Test
    public void moves_puzzle3x3_02_2() {
        Board board = getBoardFromFile("puzzle3x3-02.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(2, solver.moves());

    }

    @Test
    public void moves_puzzle3x3_03_3() {
        Board board = getBoardFromFile("puzzle3x3-03.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(3, solver.moves());

    }

    @Test
    public void moves_puzzle3x3_04_4() {
        Board board = getBoardFromFile("puzzle3x3-04.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(4, solver.moves());

    }

    @Test
    public void moves_puzzle3x3_05_5() {
        Board board = getBoardFromFile("puzzle3x3-05.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(5, solver.moves());

    }

    @Test
    public void moves_puzzle3x3_06_6() {
        Board board = getBoardFromFile("puzzle3x3-06.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(6, solver.moves());

    }

    @Test
    public void moves_puzzle3x3_07_7() {
        Board board = getBoardFromFile("puzzle3x3-07.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(7, solver.moves());

    }

    @Test
    public void moves_puzzle3x3_10_10() {
        Board board = getBoardFromFile("puzzle3x3-10.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(10, solver.moves());

    }

    @Test
    public void moves_puzzle3x3_15_15() {
        Board board = getBoardFromFile("puzzle3x3-15.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(15, solver.moves());

    }

    @Test
    public void moves_puzzle4x4_01_1() {
        Board board = getBoardFromFile("puzzle4x4-01.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(1, solver.moves());

    }

    @Test
    public void moves_puzzle4x4_02_2() {
        Board board = getBoardFromFile("puzzle4x4-02.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(2, solver.moves());

    }

    @Test
    public void moves_puzzle4x4_03_3() {
        Board board = getBoardFromFile("puzzle4x4-03.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(3, solver.moves());

    }

    @Test
    public void moves_puzzle4x4_04_4() {
        Board board = getBoardFromFile("puzzle4x4-04.txt");

        System.out.println("Initial board");
        System.out.println(board.toString());

        Solver solver = new Solver(board);
        assertEquals(4, solver.moves());

    }

    @Test
    public void moves_puzzle4x4_05_5() {
        Board board = getBoardFromFile("puzzle4x4-05.txt");

        Solver solver = new Solver(board);
        assertEquals(5, solver.moves());

    }

    @Test
    public void moves_puzzle4x4_06_6() {
        Board board = getBoardFromFile("puzzle4x4-06.txt");

        Solver solver = new Solver(board);
        assertEquals(6, solver.moves());

    }

    @Test
    public void moves_puzzle4x4_07_7() {
        Board board = getBoardFromFile("puzzle4x4-07.txt");

        Solver solver = new Solver(board);
        assertEquals(7, solver.moves());

    }

    @Test
    public void moves_puzzle4x4_08_8() {
        Board board = getBoardFromFile("puzzle4x4-08.txt");

        Solver solver = new Solver(board);
        assertEquals(8, solver.moves());

    }

    @Test
    public void moves_puzzle4x4_09_9() {
        Board board = getBoardFromFile("puzzle4x4-09.txt");

        Solver solver = new Solver(board);
        assertEquals(9, solver.moves());

    }

    @Test
    @Ignore
    public void moves_puzzle4x4_10_10() {
        Board board = getBoardFromFile("puzzle4x4-10.txt");

        Solver solver = new Solver(board);
        assertEquals(4, solver.moves());

    }

    @Test
    public void moves_puzzle04_4() {
        Board board = getBoardFromFile("puzzle04.txt");

        Solver solver = new Solver(board);
        assertEquals(4, solver.moves());

    }

    @Test
    @Ignore
    public void moves_puzzle3x3_unsolvable() {
        Board board = getBoardFromFile("puzzle3x3-unsolvable.txt");

        Solver solver = new Solver(board);
        assertEquals(4, solver.moves());

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