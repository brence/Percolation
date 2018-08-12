import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
    private double[] gdThresholds; 
    private int gnTrialCount;
    
    public PercolationStats(int nSize, int nTrialCount) {
        if(nSize<=0) throw new IllegalArgumentException("Grid size needs to be bigger than 0!");
        if(nTrialCount<=0) throw new IllegalArgumentException("Trial times needs to be bigger than 0!");
        
        private Percolation oPercalation;
        
        for(int i=0; i<nTrialCount; i++) {
            oPercalation = new Percolation(nSize);
            int nOpenSite = 0;
            while(!oPercalation.percolates()) {
                int nRow = StdRandom.uniform(1, nSize + 1);
                int nCol = StdRandom.uniform(1, nSize + 1);
                if(!oPercalation.isOpen(nRow, nCol)) {
                    oPercalation.open(nRow, nCol);
                    nOpenSite++;
                }
            }
            double dThreshold = (double) nOpenSite / (nSize * nSize);
            gdgThresholds[i] = dThreshold;
        }
    }
    
    public double mean() {
        return StdStats.mean(gdThresholds);
    }
    
    public double stddev() {
        return StdStats.stddev(gdThresholds);
    }
    
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(gnTrialCount));
    }
    
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(gnTrialCount));
    }
    
    
    public static void main(String[] args) {
        int nSize = Integer.parseInt(args[0]);
        gnTrialCount = Integer.parseInt(args[1]);
        PercolationStats oPercolationStats = new PercolationStats(nSize, gnTrialCount);
        
        System.out.println("mean                    = " + mean());
        System.out.println("stddev                  = " + stddev());
        System.out.println("95% confidence interval = [" + confidenceLo() + ", " + confidenceHi() + "]");
    }
}
        