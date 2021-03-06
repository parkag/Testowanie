package algs.example.gui.problems.nearestNeighbor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Launch the Nearest Neighbor example.
 *
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class Launcher {

    public static void main(String[] args) {
        final MainFrame mf = new MainFrame();
        mf.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                mf.setVisible(false);
                System.exit(0);
            }

            public void windowClosing(WindowEvent e) {
                mf.setVisible(false);
                System.exit(0);
            }
        });

        mf.setVisible(true);

    }
}
