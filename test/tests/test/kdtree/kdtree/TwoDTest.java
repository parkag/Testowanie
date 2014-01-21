package tests.algs.model.tests.kdtree;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;

import algs.model.IPoint;
import algs.model.kdtree.HorizontalNode;
import algs.model.kdtree.IVisitTwoDNode;
import algs.model.kdtree.TwoDNode;
import algs.model.kdtree.TwoDTree;
import algs.model.kdtree.VerticalNode;
import algs.model.nd.Hyperpoint;
import algs.model.twod.TwoDRectangle;
import algs.model.twod.TwoDPoint;

public class TwoDTest extends TestCase {

    /**
     * Validate RectangularRegion.
     */
    @Test
    public void testRectangularRegion() {
        TwoDRectangle rr = new TwoDRectangle(10, 10, 300, 153);
        TwoDRectangle rr2 = new TwoDRectangle(10, 10, 300, 153);
        assertEquals("[10.0,10.0 : 300.0,153.0]", rr.toString());
        assertFalse(rr.equals("slkdjlds"));
        assertTrue(rr.equals(rr));
        assertTrue(rr.equals(rr2));
        assertTrue(rr2.equals(rr));
        assertFalse(rr.equals(null));
    }

    /**
     * Validate the structure of TwoDNode trees, with alternating levels.
     */
    @Test
    public void testStructure() {
        HorizontalNode hn = new HorizontalNode(new TwoDPoint(200, 153));
        HorizontalNode hn2 = new HorizontalNode(new TwoDPoint(100, 253));
        VerticalNode vn = new VerticalNode(new TwoDPoint(90, 120));
        VerticalNode vn2 = new VerticalNode(new TwoDPoint(110, 230));

        try {
            hn.setBelow(hn2);
            fail("Structure of TwoDTree can be compromised.");
        } catch (IllegalArgumentException iae) {
        }

        try {
            hn.setAbove(hn2);
            fail("Structure of TwoDTree can be compromised.");
        } catch (IllegalArgumentException iae) {
        }

        try {
            vn.setBelow(vn2);
            fail("Structure of TwoDTree can be compromised.");
        } catch (IllegalArgumentException iae) {
        }

        try {
            vn.setAbove(vn2);
            fail("Structure of TwoDTree can be compromised.");
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
    public void testTwoDPoint() {
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
        HorizontalNode hn = new HorizontalNode(new TwoDPoint(200, 153));
        assertFalse(hn.isVertical());

        assertTrue(hn.isBelow(new TwoDPoint(10, 10)));   // below
        assertFalse(hn.isBelow(new TwoDPoint(10, 330))); // above
    }

    @Test
    public void testVerticalNode() {
        TwoDNode n2, n3;
        TwoDNode n = new VerticalNode(new TwoDPoint(90, 120));

        assertTrue(n.isBelow(new TwoDPoint(10, 10)));     // means to the left
        assertFalse(n.isBelow(new TwoDPoint(210, 330)));  // to the right

        //IHypercube space = new Hypercube (10, 300, 10, 300);

        // split vertical node 'below' (i.e., to the left)
        n = new VerticalNode(new TwoDPoint(90, 120));
        n2 = new HorizontalNode(new TwoDPoint(10, 300));
        n.setBelow(n2);
        // double left, double bottom, double right, double top
        assertEquals(new TwoDRectangle(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, 90.0, Double.POSITIVE_INFINITY), n2.getRegion());

        // split vertical node 'above' (i.e., to the right)
        n = new VerticalNode(new TwoDPoint(90, 120));
        n2 = new HorizontalNode(new TwoDPoint(300, 300));
        n.setAbove(n2);
        assertEquals(new TwoDRectangle(90, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), n2.getRegion());

        n = new VerticalNode(new TwoDPoint(90, 120));
        n2 = new HorizontalNode(new TwoDPoint(300, 300));
        n.setAbove(n2);
        n3 = new VerticalNode(new TwoDPoint(120, 400));
        n2.setAbove(n3);
        // double left, double bottom, double right, double top
        assertEquals(new TwoDRectangle(90, 300, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), n3.getRegion());

        n = new VerticalNode(new TwoDPoint(90, 120));
        n2 = new HorizontalNode(new TwoDPoint(300, 300));
        n.setAbove(n2);
        n3 = new VerticalNode(new TwoDPoint(120, 200));
        n2.setBelow(n3);
        assertEquals(new TwoDRectangle(90, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 300.0), n3.getRegion());
    }

    @Test
    public void testSample() {
        TwoDTree tt = new TwoDTree();

        // perform null search on empty tree
        ArrayList<IPoint> empResults = tt.search(new TwoDRectangle(0, 0, 2, 2));
        assertEquals(0, empResults.size());

        final StringBuilder empRecord = new StringBuilder();
        tt.search(new TwoDRectangle(79, 143, 81, 145), new IVisitTwoDNode() {
            public void visit(TwoDNode node) {
                empRecord.append("*");
            }

            public void drain(TwoDNode node) {
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
        tt.insert(new TwoDPoint(80, 144));   // THE ONE fond.
        tt.insert(new TwoDPoint(210, 40));
        tt.insert(new TwoDPoint(238, 150));

        tt.insert(new TwoDPoint(195, 30));
        tt.insert(new TwoDPoint(190, 140));
        tt.insert(new TwoDPoint(230, 90));
        tt.insert(new TwoDPoint(250, 148));

        // do empty query
        ArrayList<IPoint> results = tt.search(new TwoDRectangle(0, 0, 2, 2));
        assertEquals(0, results.size());

        // try one by itself
        results = tt.search(new TwoDRectangle(79, 143, 81, 145));
        assertEquals(1, results.size());

        // try all by itself
        results = tt.search(new TwoDRectangle(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
        assertEquals(11, results.size());

        // try more complex search.
        final StringBuilder record = new StringBuilder("");
        tt.search(new TwoDRectangle(79, 143, 81, 145), new IVisitTwoDNode() {
            public void visit(TwoDNode node) {
                record.append("*");
            }

            public void drain(TwoDNode node) {
                record.append("+");
            }
        });
        assertEquals("*", record.toString());

        record.setLength(0);
        tt.search(new TwoDRectangle(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY), new IVisitTwoDNode() {
            public void visit(TwoDNode node) {
                record.append("*");
            }

            public void drain(TwoDNode node) {
                record.append("+");
            }
        });
        assertEquals("***********", record.toString());
    }
    
    //////////////////////////
    @Test
    public void testTwoDPoint1() {
    	TwoDPoint pt = new TwoDPoint(10, 30);
        assertEquals(10.0, pt.getX());
        assertEquals(30.0, pt.getY());
        
        pt = new TwoDPoint("10, 30");
        assertEquals(10.0, pt.getX());
        assertEquals(30.0, pt.getY());
        
        IPoint pt1 = new TwoDPoint(pt);
        assertEquals(10.0, pt.getX());
        assertEquals(30.0, pt.getY());
        
        //Equals test
        IPoint pt2 = (IPoint) new TwoDPoint(10,31);
        assertFalse(pt.equals(pt2));
        pt2 = (IPoint) new TwoDPoint(11,31);
        assertFalse(pt.equals(pt2));
    }
    
    @Test
    public void testMore() {
    	//to String
    	TwoDPoint pt = new TwoDPoint(10, 30);
    	assertEquals(pt.toString(),"10.0,30.0");
    	
    	//dimensionality
    	assertEquals(pt.dimensionality(),2);
    	assertFalse((pt.dimensionality()==3));
    	
    	//getCoordinate
    	assertEquals(pt.getCoordinate(1),10.0);
    	assertEquals(pt.getCoordinate(2),30.0);
    	assertFalse(pt.getCoordinate(2)==31);
    	
    	//distance
    	TwoDPoint pt1 = new TwoDPoint(6, 33);
    	assertEquals(pt.distance(pt1),5.0);
    	
    	try{
    		Hyperpoint hp= new Hyperpoint("1.0,2.0,3.0");
    		pt.distance(hp);
    	}
    	catch (IllegalArgumentException e){
    	}
    	
    	//Hashcode
    	assertEquals(pt.hashCode(),-2019164160);
    	assertEquals(pt1.hashCode(),-2015920128);
    	
    	double[] tab= new double[]{10.0,30.0};
    	double[] raw=pt.raw();
    	//raw
    	assertEquals(raw[0],tab[0]);
    	assertEquals(raw[1],tab[1]);
    }
    
    ///////////////////
    	
    	@Test
    	public void testTwoDTree(){
    		TwoDPoint pt = new TwoDPoint(10, 30);
    		TwoDPoint pt1 = new TwoDPoint(10, 31);
    		TwoDPoint pt2= new TwoDPoint(9, 30);
    		TwoDPoint pt3 = new TwoDPoint(9, 29);
    		TwoDPoint targetpt = new TwoDPoint(30, 30);
    		VerticalNode n = new VerticalNode(pt);
    		TwoDTree tdt= new TwoDTree();
    		
    		
    		try{
    			tdt.insert(null);
    			fail("");
    		}
    		catch(IllegalArgumentException e){
    			
    		}
    		try{
    			tdt.parent(null);
    			fail("");
    		}
    		catch(IllegalArgumentException e){
    			
    		}
    		
			assertNull(tdt.parent(pt));
    		
			tdt.insert(pt);
			assertEquals(tdt.getRoot(),tdt.parent(pt));
			tdt.insert(pt2);
			assertEquals(tdt.getRoot(),tdt.parent(pt));
			tdt.insert(pt1);
			assertEquals(tdt.getRoot().getAbove(),tdt.parent(pt));
			tdt.insert(pt3);
			tdt.parent(pt3);
			assertEquals(tdt.getRoot().getAbove(),tdt.parent(pt));
			
			
			tdt.setRoot(n);
			assertEquals(n,tdt.getRoot());
			
			//nearest test
			tdt.setRoot(null);
			assertNull(tdt.nearest(targetpt));
			
			tdt.insert(pt);
			tdt.insert(pt2);
			tdt.insert(pt1);
			tdt.insert(pt3);
			//tdt.setRoot(n);
			assertNull(tdt.nearest(null));
			assertEquals(pt,tdt.nearest(targetpt));
			
			assertEquals(pt3,tdt.nearest(new TwoDPoint(9, 0)));
			
			//toString
			
			tdt.setRoot(null);
			assertEquals("",tdt.toString());
			
			tdt.insert(pt);
			
			
			assertEquals("V:<10.0,30.0 region:[-Infinity,-Infinity : Infinity,Infinity]>",tdt.toString());
			
			tdt.insert(pt2);
			tdt.insert(pt1);
			assertEquals("H:<9.0,30.0 region:[-Infinity,-Infinity : 10.0,Infinity]>V:<10.0,30.0 region:[-Infinity,-Infinity : Infinity,Infinity]>H:<10.0,31.0 region:[10.0,-Infinity : Infinity,Infinity]>",tdt.toString());
    		
			//updateRectangles()
			
			tdt.updateRectangles();
			assertEquals(tdt.getRoot().getAbove(),tdt.parent(pt));
    	}
}
