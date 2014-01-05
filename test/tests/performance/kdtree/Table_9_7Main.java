package algs.model.performance.kdtree;


/**
 * Generate results of table 97
 */
import java.util.Random;

import algs.model.IMultiPoint;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.KDFactory;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hyperpoint;
import algs.model.tests.common.TrialSuite;

public class Table_9_7Main {
	// random number generator.
	static Random rGen;


	/** 
	 * generate array of n d-dimensional points whose coordinates are
	 * values in the range 0 .. scale
	 */
	public static IMultiPoint[] randomPoints (int n, int d, int scale) {
		IMultiPoint points[] = new IMultiPoint[n];
		for (int i = 0; i < n; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < d; j++) {
				sb.append(rGen.nextDouble()*scale);
				if (j < d-1) { sb.append (","); }
			}
			points[i] = new Hyperpoint(sb.toString());
		}

		return points;
	}	


	public static void main (String []args) {
		rGen = new Random();
		rGen.setSeed(1);  // be consistent across platforms and runs.

		// dimension for points.
		int numSearches = 50;
		int NUM_TRIALS = 1;

		int scale = 1;

		//  n=4 to 131,072 random two dimensional 
		for (int d = 2; d <= 10; d *= 5) {
			TrialSuite kdSearch = new TrialSuite();
			for (int n=4; n <= 131072; n*=2) { /* start from 4 for table 9-7 */
				for (int t = 1; t <= NUM_TRIALS; t++) {
					long now, done;

					// create n random points in d dimensions drawn from [0,1] uniformly
					IMultiPoint[] points = randomPoints (n, d, scale);

					// Perform a number of searches drawn from same [0,1] uniformly.
					System.gc();
					IMultiPoint[] searchPoints = randomPoints (numSearches, d, scale);

					// This forms the basis for the kd-tree. These are the points p. Note
					// that the KDTree generate method will likely shuffle the points. 
					KDTree tree= KDFactory.generate(points);

					DimensionalNode.numDoubleRecursions=0;
					DimensionalNode.numRecursions=0;
					System.gc();
					now = System.currentTimeMillis();
					for (IMultiPoint imp : searchPoints) {
						tree.nearest(imp);
					}
					done = System.currentTimeMillis();
					kdSearch.addTrial(n, now, done);
					double dr = DimensionalNode.numDoubleRecursions/(1.0*numSearches);
					double r = DimensionalNode.numRecursions/(1.0*numSearches);

					System.out.println(n + "," + r + "," + dr);
				}
			}
		}

		// now generate table for d=2 to d=10
		for (int d = 2; d <= 15; d++) {
			int n = 131072;
			// create n random points in d dimensions drawn from [0,1] uniformly
			IMultiPoint[] points = randomPoints (n, d, scale);
		
			// Perform a number of searches drawn from same [0,1] uniformly.
			System.gc();
			IMultiPoint[] searchPoints = randomPoints (numSearches, d, scale);
			
			// This forms the basis for the kd-tree. These are the points p. Note
			// that the KDTree generate method will likely shuffle the points. 
			KDTree tree= KDFactory.generate(points);
			
			DimensionalNode.numDoubleRecursions=0;
			DimensionalNode.numRecursions=0;
			System.gc();
			for (IMultiPoint imp : searchPoints) {
				tree.nearest(imp);
			}
			double dr = DimensionalNode.numDoubleRecursions/(1.0*numSearches);
			double r = DimensionalNode.numRecursions/(1.0*numSearches);
			
			System.out.println(n + "," + r + "," + dr);
		}
	}
}
