package algs.model.performance.array;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.array.FirstSelector;
import algs.model.array.IPivotIndex;
import algs.model.array.LastSelector;
import algs.model.array.MedianSelector;
import algs.model.array.PISelector;
import algs.model.array.QuickSort;
import algs.model.array.RandomSelector;
import algs.model.data.points.UniformGenerator;
import algs.model.tests.common.TrialSuite;


public class TimeQuickSortTest extends TestCase {
	
	int BASE = 16777216;

	public void runTrial (int size, int numDigits, TrialSuite set,
			IPoint[]pts, IPivotIndex selector) {
		Integer[] ar = new Integer[size];
		for (int i = 0, idx = 0; i < pts.length; i++) {
			ar[idx++] = (int) (pts[i].getX() * BASE);
			ar[idx++] = (int) (pts[i].getY() * BASE);
		}
		
		QuickSort qs = new QuickSort(ar);
		qs.setMinimumSize(6);
		qs.setPivotMethod(selector);
		
		System.gc();
		long start = System.currentTimeMillis();
		qs.qsort(0,size-1);
		long end = System.currentTimeMillis();
		set.addTrial(size, start, end);
		
		for (int i = 0; i < ar.length-1; i++) {
			assertTrue ("" + i, ar[i] <= ar[i+1]);
		}		
	}
	
	@Test
	public void testQS() {
		UniformGenerator ug = new UniformGenerator();
		TrialSuite pi = new TrialSuite();
		TrialSuite first = new TrialSuite();
		TrialSuite last = new TrialSuite();
		TrialSuite random = new TrialSuite();
		TrialSuite median = new TrialSuite ();
		
		// 50 trials over sizes from 10 to 100000
		for (int size = 10, numDigits = 2; size <= 100000; size *= 10, numDigits++) {
			System.out.println (size + "...");
			for (int t = 0; t < 20; t++) {
				// create random arrays of given SIZE with range [0 .. 32768]
				IPoint[]pts = ug.generate(size/2);  // 500 points = 1000 random [0,1] values
				runTrial (size, numDigits, first, pts, new FirstSelector());
				runTrial (size, numDigits, last, pts, new LastSelector());
				runTrial (size, numDigits, pi, pts, new PISelector(numDigits));
				runTrial (size, numDigits, random, pts, new RandomSelector());
				runTrial (size, numDigits, median, pts, new MedianSelector());	
			}
		}
		
		// random one (based upon pi)
		System.out.println ("PI");
		System.out.println (pi.computeTable());
		
		// first one always
		System.out.println ("FIRST");
		System.out.println (first.computeTable());
		
		// last one always
		System.out.println ("LAST");
		System.out.println (last.computeTable());
		
		// random one always
		System.out.println ("RANDOM");
		System.out.println (last.computeTable());
		
		// median one always
		System.out.println ("MEDIAN");
		System.out.println (last.computeTable());
	}
}
