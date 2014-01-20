package test.kdTreeNearest;
import static org.junit.Assert.*;

import org.junit.Test;

import junit.framework.TestCase;



import algs.model.kdtree.DimensionalNode;
import algs.model.kdtree.KDTree;
import algs.model.nd.Hyperpoint;
import algs.model.twod.TwoDPoint;

public class kdTreeNearestTest  {
	
	@Test
	public void nearestTest(){
		KDTree kdt;
		try{
			
		
		 kdt= new KDTree(0);
	
		}
		catch(IllegalArgumentException e){
			
		}
		kdt= new KDTree(2);
		assertNull(kdt.getRoot());
		assertEquals(2,kdt.maxDimension);
		
		assertNull(kdt.nearest(new TwoDPoint(10,30)));
		
		DimensionalNode n = new DimensionalNode(2,new TwoDPoint(10,30));
		kdt.setRoot(n);
		assertNull(kdt.nearest(null));
		
		TwoDPoint point = new TwoDPoint(10,30);
		assertEquals(point,kdt.nearest(new TwoDPoint(10,30)));
		
	}
	
	@Test
	public void nearestTest1(){
		KDTree kdt= new KDTree(2);
		TwoDPoint[] points = new TwoDPoint[100];
		for(int i=0;i<100;i++){
			points[i]= new TwoDPoint(10,i);
			kdt.insert(points[i]);
		}
		assertEquals(new TwoDPoint(10,10),kdt.nearest(new TwoDPoint(100,10)) );
		assertEquals(new TwoDPoint(10,33),kdt.nearest(new TwoDPoint(-100,33)) );
		assertEquals(new TwoDPoint(10,0),kdt.nearest(new TwoDPoint(100,-999)) );
	}
	
	@Test
	public void nearestTest2(){
		KDTree kdt= new KDTree(4);
		Hyperpoint hp =new Hyperpoint(new double[]{1,2,3});
		Hyperpoint hp2 =new Hyperpoint(new double[]{1,2,3,4});
		kdt.insert(hp);
		kdt.insert(hp2);
		
		try{
		kdt.nearest(new TwoDPoint(0,0));
		fail("");
		}
		catch(IllegalArgumentException iae){
			
		}
		try{
			kdt.nearest(new Hyperpoint(new double[]{1,2,3,4,5}));
			fail("");
			}
			catch(IllegalArgumentException iae){
				
			}
		assertEquals(hp2,kdt.nearest(new Hyperpoint(new double[]{1,2,3,4})));
		
		//throw exception, should find hp
		try{
		kdt.nearest(new Hyperpoint(new double[]{1,2,3}));
		}
		catch(IllegalArgumentException iae){
			
		}
	}
}
