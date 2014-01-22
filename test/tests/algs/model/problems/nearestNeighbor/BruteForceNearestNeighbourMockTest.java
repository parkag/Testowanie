/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algs.model.problems.nearestNeighbor;

import algs.model.IMultiPoint;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Paweł Woźniak
 */
public class BruteForceNearestNeighbourMockTest {
    
    private static List<double[]> testData = new ArrayList<>();
  
    private static IMultiPoint[] points=new IMultiPoint[5];
    /**
    
     * Wypełnianie danych testowych dla konkretnych mocków
     * Tworzy 5 mocków na punktach do porównania
     */
    @BeforeClass
    public static void setup(){
        testData.add(new double[]{10,10,0});
        testData.add(new double[]{1,1,4});
        testData.add(new double[]{3,0,-8});
        testData.add(new double[]{2,-2,1});
        testData.add(new double[]{0,5,0});
        for(int i=0;i<5;i++){
            points[i]=EasyMock.createMock(IMultiPoint.class);
            EasyMock.expect(points[i].raw()).andReturn(testData.get(i)).times(6);
        }
        EasyMock.replay(points);
    }
   
    
    /**
     * 
     * Wywołuje algorytm BruteForceNearestNeighbor i porównuje wynik z oczekiwanym.
     */
    
    private void assertTabs(double[] tab1,double[] tab2) throws AssertionError{
        for(int i=0;i<tab1.length;i++){
            Assert.assertEquals(tab1, tab2);
        }
    }
    
    @Test
    public void mockTest01(){
        IMultiPoint startingPoint=EasyMock.createMock(IMultiPoint.class);
        EasyMock.expect(startingPoint.raw()).andReturn(new double[]{0,0,0}).times(2);
        
        EasyMock.replay(startingPoint);
        
        //wywołanie algorytmu nn
        BruteForceNearestNeighbor bnn=new BruteForceNearestNeighbor(points);
        double[] actual=bnn.nearest(startingPoint).raw();
        
        double[] expected=points[3].raw();
        assertTabs(actual,expected);
    }
    
    @Test
    public void mockTest02(){
        IMultiPoint startingPoint=EasyMock.createMock(IMultiPoint.class);
        EasyMock.expect(startingPoint.raw()).andReturn(new double[]{10,10,1}).times(2);
        
        EasyMock.replay(startingPoint);
        
        //wywołanie algorytmu nn
        BruteForceNearestNeighbor bnn=new BruteForceNearestNeighbor(points);
        double[] actual=bnn.nearest(startingPoint).raw();
 
        double[] expected=points[0].raw();
        assertTabs(actual,expected);
        
        
    }
    
    @Test(expected=AssertionError.class)
    public void mockTest03(){
        IMultiPoint startingPoint=EasyMock.createMock(IMultiPoint.class);
        EasyMock.expect(startingPoint.raw()).andReturn(new double[]{0,0,0}).times(2);
        
        EasyMock.replay(startingPoint);
        
        //wywołanie algorytmu nn
        BruteForceNearestNeighbor bnn=new BruteForceNearestNeighbor(points);
        double[] actual=bnn.nearest(startingPoint).raw();
        
        double[] expected=points[4].raw();
        assertTabs(actual,expected);
    }
    
}
