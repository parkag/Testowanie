/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import algs.model.tests.fp.ExampleFloatingPointTest;
import algs.model.tests.kdtree.HyperpointTest;
import algs.model.tests.kdtree.KDExtendedTest;
import algs.model.tests.kdtree.KDTest;
import algs.model.tests.kdtree.TwoDTest;
import algs.model.tests.twod.TwoDPointTest;
/**
 *
 * @author greg
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        ExampleFloatingPointTest.class,
        TwoDPointTest.class,
        TwoDTest.class,
        HyperpointTest.class,
        KDTest.class,
        KDExtendedTest.class
})

public class AuthorSuite {
}

