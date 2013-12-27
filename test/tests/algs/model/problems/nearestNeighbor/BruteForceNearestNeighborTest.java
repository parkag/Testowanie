/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package algs.model.problems.nearestNeighbor;

import algs.model.IMultiPoint;
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
        System.out.println("nearest");
        IMultiPoint x = null;
        BruteForceNearestNeighbor instance = null;
        IMultiPoint expResult = null;
        IMultiPoint result = instance.nearest(x);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}