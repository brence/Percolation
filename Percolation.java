import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF grid;
    private final int gridsize;
    private final int top;
    private final int bottom;
    private boolean[][] opened;
    private int gnOpenSites;
    
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("Grid size needs to be bigger than 0!");
        gridsize = n;
        
        grid = new WeightedQuickUnionUF(gridsize * gridsize + 2);
        top = 0;
        bottom = gridsize * gridsize + 1;
        
        // top connects with the first row, bottom connects with the last row
        for (int i = 1; i <= gridsize; i++) {
            grid.union(top, i);
            grid.union(bottom, gridsize * gridsize + 1 - i);
        }
        
        // Initially every site is blocked
        opened = new boolean[gridsize+1][gridsize+1];
        for (int i = 1; i <= gridsize; i++) {
            for (int j = 1; j <= gridsize; j++) {
                opened[i][j] = false;
            }
        }
        gnOpenSites = 0;
    }
    
    public void open(int row, int col) {
        if (row < 1 || row > gridsize) throw new IllegalArgumentException("Row must be between 1 and grid size!");
        if (col < 1 || col > gridsize) throw new IllegalArgumentException("Column must be between 1 and grid size!");
        if (!opened[row][col]) {
            opened[row][col] = true;
            gnOpenSites++;
            // up
            if (row > 1 && isOpen(row - 1, col)) {
                grid.union(getIndex(row, col), getIndex(row - 1, col));
            }
            // bottom
            if (row < gridsize && isOpen(row + 1, col)) {
                grid.union(getIndex(row, col), getIndex(row + 1, col));
            }
            // left
            if (col > 1 && isOpen(row, col - 1)) {
                grid.union(getIndex(row, col), getIndex(row, col - 1));
            }
            // right
            if (col < gridsize && isOpen(row, col + 1)) {
                grid.union(getIndex(row, col), getIndex(row, col + 1));
            }
        }
    }
    
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > gridsize) throw new IllegalArgumentException("Row must be between 1 and grid size!");
        if (col < 1 || col > gridsize) throw new IllegalArgumentException("Column must be between 1 and grid size!");
        return opened[row][col];
    }
        
    public boolean isFull(int row, int col) {
        if (row < 1 || row > gridsize) throw new IllegalArgumentException("Row must be between 1 and grid size!");
        if (col < 1 || col > gridsize) throw new IllegalArgumentException("Column must be between 1 and grid size!");
        if (!isOpen(row, col)) return false;
        return grid.connected(top, getIndex(row, col));
    }
    
    public int numberOfOpenSites() {
        return gnOpenSites;
    }
    
    public boolean percolates() {
        if (numberOfOpenSites() == 0) return false;
        return grid.connected(top, bottom);
    }
    
    private int getIndex(int row, int col) {
        if (row < 1 || row > gridsize) throw new IllegalArgumentException("Row must be between 1 and grid size!");
        if (col < 1 || col > gridsize) throw new IllegalArgumentException("Column must be between 1 and grid size!");
        return (row - 1) * gridsize + col;
    }
    
    public static void main(String[] args) {
        // Leave it to add future test clients
    }
}