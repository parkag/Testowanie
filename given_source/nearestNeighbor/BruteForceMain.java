/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author greg
 */


public class BruteForceMain {
    public static void main(String ... args){
        TwoDPoint[] pts = new TwoDPoint[10];
        for(int i = 0; i<10; i++){
            pts[i] = new TwoDPoint(i, i*2);
            System.out.println(pts[i]);
        }
        BruteForceNearestNeighbor nn = new BruteForceNearestNeighbor(pts);
        TwoDPoint probe = new TwoDPoint(10,20);
        System.out.println("nearest to "+probe+" is "+nn.nearest(probe));
    }
}
