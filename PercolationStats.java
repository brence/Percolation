import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] gdThresholds; 
    private final int gnTrialCount;
    private double gLastMean = 0;
    private double gLastStddev = 0;
    
    public PercolationStats(int nSize, int nTrialCount) {
        if (nSize <= 0) throw new IllegalArgumentException("Grid size needs to be bigger than 0!");
        if (nTrialCount <= 0) throw new IllegalArgumentException("Trial times needs to be bigger than 0!");
        
        Percolation oPercalation;
        gnTrialCount = nTrialCount;
        gdThresholds = new double[gnTrialCount];
        
        for (int i = 0; i < nTrialCount; i++) {
            // System.out.println("Trial time: " + (i+1));
            oPercalation = new Percolation(nSize);
            int nOpenSite = 0;
            while (!oPercalation.percolates()) {
                // System.out.println("  Open Sites: " + nOpenSite);
                int nRow = StdRandom.uniform(1, nSize + 1);
                int nCol = StdRandom.uniform(1, nSize + 1);
                if (!oPercalation.isOpen(nRow, nCol)) {
                    oPercalation.open(nRow, nCol);
                    nOpenSite++;
                }
            }            
            double dThreshold = ((double) nOpenSite) / (nSize * nSize);
            // System.out.println("Immediate Threshold: " + dThreshold);
            gdThresholds[i] = dThreshold;
        }
        // for(int i=0; i<nTrialCount; i++) {
        //    System.out.println("Threshold: " + gdThresholds[i]);
        // }
    }
    
    public double mean() {
        gLastMean = StdStats.mean(gdThresholds);
        return gLastMean;
    }
    
    public double stddev() {
        gLastStddev = StdStats.stddev(gdThresholds);
        return gLastStddev;
    }
    
    public double confidenceLo() {
        return gLastMean - ((CONFIDENCE_95 * gLastStddev) / Math.sqrt(gnTrialCount));
    }
    
    public double confidenceHi() {
        return gLastMean + ((CONFIDENCE_95 * gLastStddev) / Math.sqrt(gnTrialCount));
    }
    
    
    public static void main(String[] args) {
        int nSize = Integer.parseInt(args[0]);
        int nTrialCount = Integer.parseInt(args[1]);
        PercolationStats oPercolationStats = new PercolationStats(nSize, nTrialCount);
        
        System.out.println("mean                    = " + oPercolationStats.mean());
        System.out.println("stddev                  = " + oPercolationStats.stddev());
        System.out.println("95% confidence interval = [" + oPercolationStats.confidenceLo() + ", " + oPercolationStats.confidenceHi() + "]");
    }
}
        