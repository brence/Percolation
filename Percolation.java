import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private int gridsize;
    private int top;
    private int bottom;
    private boolean[][] opened;
    
    public Percolation(int n) {
        gridsize = n;
        
        grid = new WeightedQuickUnionUF(gridsize * gridsize + 2);
        top = 0;
        bottom = gridsize * gridsize + 1;
        
        // top connects with the first row, bottom connects with the last row
        for(int i=1; i<=gridsize; i++) {
            grid.union(top, i);
            grid.union(bottom, gridsize * gridsize + 1 - i);
        }
        
        // Initially every site is blocked
        opened = new boolean[gridsize+1][gridsize+1];
        for(int i=1; i<=gridsize; i++) {
            for(int j=1; j<=gridsize; j++) {
                opened[i][j] = false;
            }
        }
    }
    
    public void open(int row, int col) {
        opened[row][col] = true;
        // up
        if(row>1 && isOpen(row-1, col)) {
            grid.union(getIndex(row, col), getIndex(row-1, col));
        }
        // bottom
        if(row<gridsize && isOpen(row+1, col)) {
            grid.union(getIndex(row, col), getIndex(row+1, col));
        }
        // left
        if(col>1 && isOpen(row, col-1)) {
            grid.union(getIndex(row, col), getIndex(row, col-1));
        }
        // right
        if(col<gridsize && isOpen(row, col+1)) {
            grid.union(getIndex(row, col), getIndex(row, col+1));
        }
    }
    
    public boolean isOpen(int row, int col) {
        return opened[row][col];
    }
        
    public boolean isFull(int row, int col) {
        return grid.connected(top, getIndex(row, col));
    }
    
    public int numberOfOpenSites() {
        int nOpenSites = 0;
        
        for(int i=1; i<=gridsize; i++) {
            for(int j=1; j<=gridsize; j++) {
                if(opened[i][j]) nOpenSites++;
            }
        }
        
        return nOpenSites;
    }
    
    public boolean percolates() {
        return grid.connected(top, bottom);
    }
    
    private int getIndex(int row, int col) {
        return (row - 1) * gridsize + col;
    }
    
    public static void main(String[] args) {
    }
}