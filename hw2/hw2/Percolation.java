package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] sites;
    private WeightedQuickUnionUF unionFind;
    private WeightedQuickUnionUF unionFindNoBottom; // Prevents backwash
    private int numberOfOpenSites;
    private int N;
    private int VIRTUAL_TOP_SITE_INDEX;
    private int VIRTUAL_BOTTOM_SITE_INDEX;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        this.sites = new boolean[N][N];
        this.unionFind = new WeightedQuickUnionUF(N * N + 2);
        this.unionFindNoBottom = new WeightedQuickUnionUF(N * N + 1);
        this.numberOfOpenSites = 0;
        this.N = N;
        this.VIRTUAL_TOP_SITE_INDEX = N * N;
        this.VIRTUAL_BOTTOM_SITE_INDEX = N * N + 1;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (isOpen(row, col)) {
            return;
        }
        sites[row][col] = true;
        numberOfOpenSites++;
        neighborsUnion(row, col);

        if (row == 0) {
            unionFind.union(VIRTUAL_TOP_SITE_INDEX, xyTo1D(row, col));
            unionFindNoBottom.union(VIRTUAL_TOP_SITE_INDEX, xyTo1D(row, col));
        }

        if (row == N - 1) {
            unionFind.union(VIRTUAL_BOTTOM_SITE_INDEX, xyTo1D(row, col));
        }
    }
    private void neighborsUnion(int row, int col) {
        int currentsite = xyTo1D(row, col);
        if (row - 1 >= 0 && isOpen(row - 1, col)) {
            int topNeighbour = xyTo1D(row - 1, col);
            unionFind.union(currentsite, topNeighbour);
            unionFindNoBottom.union(currentsite, topNeighbour);
        }
        if (row + 1 <= N && isOpen(row + 1, col)) {
            int bottomNeighbour = xyTo1D(row - 1, col);
            unionFind.union(currentsite, bottomNeighbour);
            unionFindNoBottom.union(currentsite, bottomNeighbour);
        }
        if (col - 1 >= 0 && isOpen(row, col - 1)) {
            int leftNeighbour = xyTo1D(row, col - 1);
            unionFind.union(currentsite, leftNeighbour);
            unionFindNoBottom.union(currentsite, leftNeighbour);
        }
        if (col + 1 <= N && isOpen(row, col + 1)) {
            int rightNeighbour = xyTo1D(row, col + 1);
            unionFind.union(currentsite, rightNeighbour);
            unionFindNoBottom.union(currentsite, rightNeighbour);
        }
    }
    private int xyTo1D(int r, int c) {
        return  N * r + c;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return sites[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int index = xyTo1D(row, col);
        return unionFind.connected(VIRTUAL_TOP_SITE_INDEX, index)
                && unionFindNoBottom.connected(VIRTUAL_TOP_SITE_INDEX, index);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.connected(VIRTUAL_TOP_SITE_INDEX, VIRTUAL_BOTTOM_SITE_INDEX);
    }

}
