/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.kdTreeNearest.kdTreeNearestTest;
import test.kdtree.kdtree.HypercubeTest;
import test.kdtree.kdtree.HyperpointTest;
import test.kdtree.kdtree.KDExtendedTest;
import test.kdtree.kdtree.KDTest;
import test.kdtree.kdtree.TwoDTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    //supporting classes
        TwoDTest.class,
        HyperpointTest.class,
        HypercubeTest.class,
    //kdTree tests
        KDTest.class,
        KDExtendedTest.class,
    //algorithm test
        kdTreeNearestTest.class
})

public class OurSuite {
    
}