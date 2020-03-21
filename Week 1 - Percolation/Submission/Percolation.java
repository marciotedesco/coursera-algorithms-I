/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int virtualtop;
    private final int virtualbottom;
    private final int n; //widht & height of the grid

    private int[][] grid;
    private int openSites;
    private WeightedQuickUnionUF weightedQuickUnionUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();

        //initialize grid size
        this.n = n;
        this.virtualtop = n * n;
        this.virtualbottom = n * n + 1;

        //create an extra colum, row to handle with the 1-based indexing of the perco grid
        //grid = new int[n + 1][n + 1];
        grid = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }

        openSites = 0;

        //initialize weightedQuickUnionUF with nsquare number of sites plus two virtual sites
        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);

        //connect virtual top to first row
        for (int i = 0; i < n; i++) {
            weightedQuickUnionUF.union(xyTo1D(0, i), virtualtop);
        }

        //connect virtual top to last row
        for (int i = 0; i < n; i++) {
            weightedQuickUnionUF.union(xyTo1D(n - 1, i), virtualbottom);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        //adapt row & col
        row--;
        col--;

        //validate indices
        if (!areIndicesValid(row, col))
            throw new IllegalArgumentException();

        //check if already opened
        if (grid[row][col] == 1) {
            //System.out.println("row: " + row + ", col: " + col + " already opened");
            return;
        }

        //open a site
        grid[row][col] = 1;
        openSites++;

        //links the site with max 4 open neighbors
        if (areIndicesValid(row - 1, col) && grid[row - 1][col] == 1)
            weightedQuickUnionUF.union(xyTo1D(row - 1, col), xyTo1D(row, col));

        if (areIndicesValid(row, col - 1) && grid[row][col - 1] == 1)
            weightedQuickUnionUF.union(xyTo1D(row, col - 1), xyTo1D(row, col));

        if (areIndicesValid(row + 1, col) && grid[row + 1][col] == 1)
            weightedQuickUnionUF.union(xyTo1D(row + 1, col), xyTo1D(row, col));

        if (areIndicesValid(row, col + 1) && grid[row][col + 1] == 1)
            weightedQuickUnionUF.union(xyTo1D(row, col + 1), xyTo1D(row, col));


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        //adapt row & col
        row--;
        col--;

        if (!areIndicesValid(row, col))
            throw new IllegalArgumentException();

        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        //adapt row & col
        row--;
        col--;

        if (!areIndicesValid(row, col))
            throw new IllegalArgumentException();

        //check if it is connected to virtual top site
        return weightedQuickUnionUF.connected(xyTo1D(row, col), virtualtop);

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(virtualtop, virtualbottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        //Test n =2
        int n = 2;
        System.out.println("Start test percolation with n: " + n);
        Percolation percolation = new Percolation(n);
        System.out.println("Check if virtualtop and virtualbottom are created and connected");
        System.out.println(percolation.areVirtualSitesConnected());

        percolation.open(1, 1);
        percolation.open(1, 2);
        System.out.println(percolation.weightedQuickUnionUF.connected(0, 1));

    }

    private int xyTo1D(int row, int col) {

        if (!areIndicesValid(row, col))
            throw new IllegalArgumentException();

        //use row major to convert 2D to 1D array
        return n * row + col;
    }

    private boolean areIndicesValid(int row, int col) {
        if (row < 0 || col < 0 || row > n - 1 || col > n - 1)
            return false;

        return true;
    }

    private boolean areVirtualSitesConnected() {
        //connect virtual top to first row
        for (int i = 0; i < n; i++) {
            if (!weightedQuickUnionUF.connected(xyTo1D(0, i), virtualtop))
                return false;
        }

        //connect virtual top to last row
        for (int i = 0; i < n; i++) {
            if (!weightedQuickUnionUF.connected(xyTo1D(n - 1, i), virtualbottom))
                return false;
        }

        return true;
    }

}