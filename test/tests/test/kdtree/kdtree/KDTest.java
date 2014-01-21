package tests.algs.model.tests.kdtree;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IMultiPoint;
import algs.model.kdtree.CounterKDTree;
import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.IVisitKDNode;
import algs.model.kdtree.KDTraversal;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hypercube;
import algs.model.nd.Hyperpoint;
import algs.model.problems.nearestNeighbor.BruteForceNearestNeighbor;
import algs.model.twod.TwoDRectangle;
import algs.model.twod.TwoDPoint;

/**
 * These are nothing more than the TwoDTests converted to using KD trees.
 */
public class KDTest extends TestCase {

    // use for all max dimensions.
    int maxDimension = 2;

    /**
     * Validate RectangularRegion.
     */
    @Test
    public void testRectangularRegion() {
        TwoDRectangle rr = new TwoDRectangle(10, 10, 300, 153);
        assertEquals("[10.0,10.0 : 300.0,153.0]", rr.toString());
        assertFalse(rr.equals("slkdjlds"));
        assertTrue(rr.equals(rr));
        assertFalse(rr.equals(null));
    }

    /**
     * Validate the structure of KDNode trees, with alternating levels.
     */
    @Test
    public void testStructure() {
        DimensionalNode hn = new DimensionalNode(1, new TwoDPoint(100, 100));
        DimensionalNode hn2 = new DimensionalNode(1, new TwoDPoint(100, 253));
        DimensionalNode vn = new DimensionalNode(2, new TwoDPoint(90, 120));
        DimensionalNode vn2 = new DimensionalNode(2, new TwoDPoint(110, 230));

        try {
            hn.setBelow(hn2);
            fail("Structure of KDTree can be compromised.");
        } catch (IllegalArgumentException iae) {
        }

        try {
            hn.setAbove(hn2);
            fail("Structure of KDTree can be compromised.");
        } catch (IllegalArgumentException iae) {
        }

        try {
            vn.setBelow(vn2);
            fail("Structure of KDTree can be compromised.");
        } catch (IllegalArgumentException iae) {
        }

        try {
            vn.setAbove(vn2);
            fail("Structure of KDTree can be compromised.");
        } catch (IllegalArgumentException iae) {
        }

        // allowed [These only check the proper types are allowed. This actually 
        // produces a cyclic tree...]
        hn.setBelow(vn);
        vn.setBelow(hn2);
        hn.setAbove(vn2);
        vn2.setAbove(hn);

    }

    @Test
    public void testKDPoint() {
        TwoDPoint pt = new TwoDPoint(10, 30);
        assertEquals(10.0, pt.getX());
        assertEquals(30.0, pt.getY());

        // probe equals
        TwoDPoint pt2 = new TwoDPoint(10, 30);
        assertEquals(pt, pt2);
        assertEquals(pt2, pt);
        assertFalse(pt.equals("SDS"));
        assertFalse(pt.equals(null));

    }

    @Test
    public void testHorizontalNode() {
        DimensionalNode n = new DimensionalNode(2, new TwoDPoint(200, 153));
        assertEquals(2, n.dimension);

        assertTrue(n.isBelow(new TwoDPoint(10, 10)));
        assertFalse(n.isBelow(new TwoDPoint(10, 330)));
    }

    @Test
    public void testRootSetter() {
        // just here for closure.
        KDTree tt = new KDTree(2);
        DimensionalNode n = new DimensionalNode(1, new TwoDPoint(90, 120));
        tt.setRoot(n);
        assertEquals(new TwoDPoint(90, 120), tt.getRoot().point);
    }

    @Test
    public void testVerticalNode() {
        DimensionalNode n2, n3;
        DimensionalNode n = new DimensionalNode(1, new TwoDPoint(90, 120));
        assertEquals(n.dimension, 1);

        assertTrue(n.isBelow(new TwoDPoint(10, 10)));     // means to the left
        assertFalse(n.isBelow(new TwoDPoint(210, 330)));  // to the right

        //IHypercube space = new Hypercube (10, 300, 10, 300);

        // split vertical node 'below' (i.e., to the left)
        n = new DimensionalNode(1, new TwoDPoint(90, 120));
        n2 = new DimensionalNode(2, new TwoDPoint(10, 300));
        n.setBelow(n2);
        assertEquals(new Hypercube(Double.NEGATIVE_INFINITY, 90, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), n2.region());

        // split vertical node 'above' (i.e., to the right)
        n = new DimensionalNode(1, new TwoDPoint(90, 120));
        n2 = new DimensionalNode(2, new TwoDPoint(300, 300));
        n.setAbove(n2);
        assertEquals(new Hypercube(90, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), n2.region());

        n = new DimensionalNode(1, new TwoDPoint(90, 120));
        n2 = new DimensionalNode(2, new TwoDPoint(300, 300));
        n.setAbove(n2);
        n3 = new DimensionalNode(1, new TwoDPoint(120, 400));
        n2.setAbove(n3);
        assertEquals(new Hypercube(90, Double.POSITIVE_INFINITY, 300.0, Double.POSITIVE_INFINITY), n3.region());

        n = new DimensionalNode(1, new TwoDPoint(90, 120));
        n2 = new DimensionalNode(2, new TwoDPoint(300, 300));
        n.setAbove(n2);
        n3 = new DimensionalNode(1, new TwoDPoint(120, 200));
        n2.setBelow(n3);
        assertEquals(new Hypercube(90, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 300.0), n3.region());
    }

    @Test
    public void testExceptions() {

        try {
            new KDTree(-2);
            fail("KDTree constructor fails to check for proper parameters.");
        } catch (IllegalArgumentException iae) {
        }

        try {
            new KDTree(0);
            fail("KDTree constructor fails to check for proper parameters.");
        } catch (IllegalArgumentException iae) {
        }

        KDTree tt = new KDTree(2);

        try {
            tt.insert(null);
            fail("KDTree insert fails to detect null");
        } catch (IllegalArgumentException iae) {
        }

    }

    @Test
    public void testVariety() {
        KDTree tt = new KDTree(2);
        assertEquals(2, tt.maxDimension);
        tt.insert(new TwoDPoint(200, 153));

        DimensionalNode root = tt.getRoot();
        assertEquals(1, root.dimension);
        assertEquals(2, tt.nextDimension(root));
        assertEquals(2, tt.nextDimension(1));
        assertEquals(1, tt.nextDimension(2));

        final StringBuilder empRecord = new StringBuilder();
        Hypercube hc = new Hypercube(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
        IVisitKDNode visitor = new IVisitKDNode() {
            public void visit(DimensionalNode node) {
                empRecord.append("*");
            }

            public void drain(DimensionalNode node) {
                empRecord.append("+");
            }
        };
        tt.search(hc, visitor);
        assertEquals("*", empRecord.toString());
        tt.removeAll();

        // ensure none left.
        empRecord.delete(0, empRecord.length());
        tt.search(hc, visitor);
        assertEquals("", empRecord.toString());
    }

    @Test
    public void testNearest() {
        KDTree tt = new KDTree(2);

        IMultiPoint p1, p2, p3, p4;
        tt.insert(p1 = new TwoDPoint(100, 100));
        tt.insert(p2 = new TwoDPoint(-100, 100));
        tt.insert(p3 = new TwoDPoint(100, -100));
        tt.insert(p4 = new TwoDPoint(-100, -100));
        BruteForceNearestNeighbor bfnn = new BruteForceNearestNeighbor(new IMultiPoint[]{p1, p2, p3, p4});

        // right in the middle! All four are the same distance.
        IMultiPoint p0 = new TwoDPoint(0, 0);

        IMultiPoint near = tt.nearest(p0);
        assertTrue(near == p1 || near == p2 || near == p3 || near == p4);
        // no point checking whether result of bfnn is same as tt since
        // any of the above four could be the closest...

        // clearly one.
        assertEquals(p1, tt.nearest(p1));
        assertEquals(p2, tt.nearest(p2));
        assertEquals(p3, tt.nearest(p3));
        assertEquals(p4, tt.nearest(p4));
        assertEquals(p1, bfnn.nearest(p1));
        assertEquals(p2, bfnn.nearest(p2));
        assertEquals(p3, bfnn.nearest(p3));
        assertEquals(p4, bfnn.nearest(p4));

        assertEquals(p1, tt.nearest(new TwoDPoint(1, 1)));
        assertEquals(p3, tt.nearest(new TwoDPoint(1, -1)));
        assertEquals(p4, tt.nearest(new TwoDPoint(-1, -1)));
        assertEquals(p2, tt.nearest(new TwoDPoint(-1, 1)));
        assertEquals(p1, bfnn.nearest(new TwoDPoint(1, 1)));
        assertEquals(p3, bfnn.nearest(new TwoDPoint(1, -1)));
        assertEquals(p4, bfnn.nearest(new TwoDPoint(-1, -1)));
        assertEquals(p2, bfnn.nearest(new TwoDPoint(-1, 1)));

    }

    @Test
    public void testParent() {
        KDTree tt = new KDTree(2);

        try {
            tt.parent(null);
            fail("KDTree fails to prevent parent of null");
        } catch (IllegalArgumentException e) {
        }

        assertNull(tt.parent(new TwoDPoint(200, 153)));

        IMultiPoint p1 = new TwoDPoint(100, 100);
        IMultiPoint p2 = new TwoDPoint(80, 90);
        IMultiPoint p3 = new TwoDPoint(120, 180);
        IMultiPoint p4 = new TwoDPoint(70, 110);
        IMultiPoint p5 = new TwoDPoint(220, 110);
        IMultiPoint p6 = new TwoDPoint(20, 20);   // not inserted
        IMultiPoint p7 = new TwoDPoint(300, 160); // not inserted

        tt.insert(p1);

        assertEquals(p1, tt.parent(p2).point);
        assertEquals(p1, tt.parent(p3).point);

        tt.insert(p2);
        tt.insert(p3);

        assertEquals(p2, tt.parent(p4).point);
        assertEquals(p3, tt.parent(p5).point);
        assertEquals(p2, tt.parent(p6).point);
        assertEquals(p3, tt.parent(p7).point);

        tt.insert(p4);
        tt.insert(p5);

        assertEquals(p5, tt.parent(p7).point);

        // show working count.
        CounterKDTree cdt = new CounterKDTree();
        assertEquals(0, cdt.getCount());
        Hypercube space = new Hypercube(Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY);
        tt.search(space, cdt);
        assertEquals(5, cdt.getCount());
        cdt.reset();
        assertEquals(0, cdt.getCount());
        tt.search(new Hypercube(60, 80, 100, 120), cdt);
        assertEquals(1, cdt.getCount());

        // deal with traversal.
        final StringBuffer sb = new StringBuffer();
        KDTraversal kdt = new KDTraversal(tt) {
            public void visit(DimensionalNode node) {
                sb.append("*");
            }
        };
        kdt.traverse();
        assertEquals("*****", sb.toString());
    }

    @Test
    public void testSample() {
        KDTree tt = new KDTree(2);

        // perform null search on empty tree
        ArrayList<IMultiPoint> empResults = tt.search(new Hypercube(0, 2, 0, 2));
        assertEquals(0, empResults.size());

        final StringBuilder empRecord = new StringBuilder();
        tt.search(new Hypercube(79, 81, 143, 145), new IVisitKDNode() {
            public void visit(DimensionalNode node) {
                empRecord.append("*");
            }

            public void drain(DimensionalNode node) {
                empRecord.append("+");
            }
        });
        assertEquals("", empRecord.toString());

        // construct tree in top-down fashion
        tt.insert(new TwoDPoint(200, 153));

        assertNotNull(tt.getRoot());
        assertEquals(new TwoDPoint(200, 153), tt.getRoot().point);

        tt.insert(new TwoDPoint(180, 133));
        tt.insert(new TwoDPoint(245, 120));

        tt.insert(new TwoDPoint(120, 80));
        tt.insert(new TwoDPoint(80, 144));
        tt.insert(new TwoDPoint(210, 40));
        tt.insert(new TwoDPoint(238, 150));

        tt.insert(new TwoDPoint(195, 30));
        tt.insert(new TwoDPoint(190, 140));
        tt.insert(new TwoDPoint(230, 90));
        tt.insert(new TwoDPoint(250, 148));

        assertEquals("1:<120.0,80.0>2:<195.0,30.0>2:<180.0,133.0>1:<80.0,144.0>2:<190.0,140.0>1:<200.0,153.0>1:<210.0,40.0>2:<230.0,90.0>2:<245.0,120.0>1:<238.0,150.0>2:<250.0,148.0>",
                tt.toString());

        // do empty query
        ArrayList<IMultiPoint> results = tt.search(new Hypercube(0, 2, 0, 2));
        assertEquals(0, results.size());

        // try one by itself
        results = tt.search(new Hypercube(79, 81, 143, 145));
        assertEquals(1, results.size());

        // try all by itself
        results = tt.search(new Hypercube(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertEquals(11, results.size());

        // try more complex search.
        final StringBuilder record = new StringBuilder("");
        tt.search(new Hypercube(79, 81, 143, 145), new IVisitKDNode() {
            public void visit(DimensionalNode node) {
                record.append("*");
            }

            public void drain(DimensionalNode node) {
                record.append("+");
            }
        });
        assertEquals("*", record.toString());

        // this will DRAIN all of the nodes...
        record.setLength(0);
        tt.search(new Hypercube(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), new IVisitKDNode() {
            public void visit(DimensionalNode node) {
                record.append("*");
            }

            public void drain(DimensionalNode node) {
                record.append("+");
            }
        });
        assertEquals("+++++++++++", record.toString());
    }
    
    ////////////
    
    @Test
    public void testMoreNearest(){
    	//test setRoot
    	KDTree tt = new KDTree(2);
    	tt.setRoot(null);
    	assertEquals(null,tt.getRoot());
    	Hyperpoint p = null;
    	Hyperpoint p1 = new Hyperpoint("1,1");
    	assertEquals(null,tt.nearest(p));
    	DimensionalNode dp = new DimensionalNode(2,p1);
    	tt.setRoot(dp);
    	assertEquals(null,tt.nearest(p));
    	
    }
    
    @Test
    public void testMoreToString(){
    	KDTree tt = new KDTree(2);
    	tt.setRoot(null);
    	Hyperpoint p1 = new Hyperpoint("1,1");
    	DimensionalNode dp = new DimensionalNode(2,p1);
    	assertEquals("",tt.toString());
    	tt.setRoot(dp);
    	
    	assertEquals("2:<1.0,1.0>",tt.toString());
    	
    }
    @Test
    public void testMore(){
    	KDTree tt = new KDTree(2);
    	tt.setRoot(null);
    	//count and height
    	assertEquals(0,tt.count());
    	assertEquals(0,tt.getNumRecursion());
    	assertEquals(0,tt.getNumDoubleRecursion());
    	assertEquals(0,tt.height());
    	Hyperpoint p1 = new Hyperpoint("1,1");
    	DimensionalNode dp = new DimensionalNode(2,p1);
    	tt.setRoot(dp);
    	assertEquals(1,tt.count());
    	assertEquals(1,tt.height());
    }
    
}
