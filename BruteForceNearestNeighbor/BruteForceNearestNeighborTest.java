/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.algs.model.problems.nearestNeighbor;

import algs.model.IMultiPoint;
import algs.model.problems.nearestNeighbor.BruteForceNearestNeighbor;
import algs.model.twod.TwoDPoint;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author greg
 */
public class BruteForceNearestNeighborTest {
    
    public BruteForceNearestNeighborTest() {
    }
    
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    	
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of nearest method, of class BruteForceNearestNeighbor.
     */
    @Test
    public void testNearest() {
       // System.out.println("nearest");
        IMultiPoint x = new TwoDPoint(10,10) ;
        //IMultiPoint points[]=null;
        
        IMultiPoint points[] = new IMultiPoint[100];
    	for(int i =0 ; i<100; i++)
    	{
    		TwoDPoint tdp= new TwoDPoint(0,i);
    		points[i]=tdp;
    	}
    	
        BruteForceNearestNeighbor instance = new BruteForceNearestNeighbor(points);
        IMultiPoint expResult =  new TwoDPoint(0,10);
        IMultiPoint result = instance.nearest(x);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
      //  fail("Z³e dane");
    }
}