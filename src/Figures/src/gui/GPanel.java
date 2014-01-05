/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import algs.example.gui.problems.nearestNeighbor.model.Model;
import algs.model.FloatingPoint;
import algs.model.IMultiPoint;
import algs.model.nd.Hyperpoint;
import algs.model.problems.nearestNeighbor.BruteForceNearestNeighbor;
import algs.model.twod.TwoDPoint;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author greg
 */
public class GPanel extends JPanel {

    IMultiPoint[] orig;
    IMultiPoint[] pts;
    IMultiPoint reforig;
    IMultiPoint ref;
    double x1, x2, y1, y2;
    double xFactor, yFactor, minX, minY, maxX, maxY;
    double dist, nearestx, nearesty;
    double lastOperationTime;

    public GPanel() {
        setPreferredSize(new Dimension(300, 300));
    }

    void setIPoints(IMultiPoint[] pts) {
        this.orig = pts;
        this.pts = transform(orig, reforig, this.getWidth(), this.getHeight());
    }

    void setRef(IMultiPoint ref) {
        reforig = ref;
        this.pts = transform(orig, reforig, this.getWidth(), this.getHeight());
    }

    @Override
    public void paintComponent(Graphics sc) {
        super.paintComponent(sc);
        //points
        if (pts != null) {
            for (int i = 0; i < pts.length; i++) {
                IMultiPoint p = pts[i];
                double x = p.getCoordinate(1);  // x
                double y = getHeight() - p.getCoordinate(2);  // y

                sc.setColor(Color.green);
                sc.fillOval((int) x + 25, (int) y - 20, 8, 8);
                sc.setColor(Color.black);
                sc.drawOval((int) x + 25, (int) y - 20, 8, 8);

                if (pts.length < 30) {
                    String coords = "(" + orig[i].getCoordinate(1) + "," + orig[i].getCoordinate(2) + ")";
                    sc.drawString(coords, (int) x + 5, (int) y - 25);
                }
            }
        }
        //reference point with coordinates
        if (ref != null) {
            double x = ref.getCoordinate(1);  // x
            double y = getHeight() - ref.getCoordinate(2);  // y

            sc.setColor(Color.red);
            sc.fillOval((int) x + 25, (int) y - 20, 8, 8);
            sc.setColor(Color.black);
            sc.drawOval((int) x + 25, (int) y - 20, 8, 8);
            String coords = "(" + reforig.getCoordinate(1) + "," + reforig.getCoordinate(2) + ")";
            sc.drawString(coords, (int) x + 5, (int) y - 25);
        }
        //line from reference point to the nearest point
        sc.drawLine((int) x1, getHeight() - (int) y1, (int) x2, getHeight() - (int) y2);
    }

    protected IMultiPoint[] transform(IMultiPoint[] points, IMultiPoint ref,
            int width, int height) {
        minX = 0;
        minY = 0;
        maxX = 0;
        maxY = 0;

        if (points != null) {
            for (IMultiPoint mp : points) {
                double x1 = mp.getCoordinate(1);
                double y1 = mp.getCoordinate(2);

                if (x1 < minX) {
                    minX = x1;
                }
                if (y1 < minY) {
                    minY = y1;
                }
                if (x1 > maxX) {
                    maxX = x1;
                }
                if (y1 > maxY) {
                    maxY = y1;
                }
            }
        }
        if (reforig != null) {
            double x1 = reforig.getCoordinate(1);
            double y1 = reforig.getCoordinate(2);

            if (x1 < minX) {
                minX = x1;
            }
            if (y1 < minY) {
                minY = y1;
            }
            if (x1 > maxX) {
                maxX = x1;
            }
            if (y1 > maxY) {
                maxY = y1;
            }
        }
        this.xFactor = 0.85 * width / (maxX - minX);
        this.yFactor = 0.85 * height / (maxY - minY);

        // Scale appropriately
        IMultiPoint[] retVal = null;
        if (points != null) {
            double[] vals = new double[2];
            retVal = new IMultiPoint[points.length];
            int idx = 0;
            for (IMultiPoint mp : points) {
                vals[0] = (mp.getCoordinate(1) - minX) * xFactor;
                vals[1] = (mp.getCoordinate(2) - minY) * yFactor;

                retVal[idx++] = new Hyperpoint(vals);
            }
        }
        if (ref != null) {
            double[] r = {(ref.getCoordinate(1) - minX) * xFactor,
                (ref.getCoordinate(2) - minY) * yFactor};
            this.ref = new Hyperpoint(r);
        }
        // convert as array
        return retVal;
    }

    void calculateElement(IMultiPoint reforig, IMultiPoint[] pts, String method, boolean calculateTime) {
        IMultiPoint norig;
        if (method.equals("BF")) {
            norig = findNearestBF(reforig, calculateTime);
        } else {
            norig = findNearestKD(reforig, calculateTime);
        }

        nearestx = norig.getCoordinate(1);
        nearesty = norig.getCoordinate(2);
        x1 = ref.getCoordinate(1) + 30;
        y1 = ref.getCoordinate(2) + 16;
        x2 = (norig.getCoordinate(1) - minX) * xFactor + 30;
        y2 = (norig.getCoordinate(2) - minY) * yFactor + 16;
        dist = Math.sqrt(Math.pow((nearestx - reforig.getCoordinate(1)), 2)
                + Math.pow((nearesty - reforig.getCoordinate(2)), 2));
        //System.out.println(x1+" ,"+ y1+"  "+ x2 +", "+ y2);
        //System.out.println(minX+" "+minY);
    }

    //kd tree approach
    public IMultiPoint findNearestKD(final IMultiPoint pt, boolean calculateTime) {
        Model model = null;
        if (calculateTime == true) {
            Long startTime = System.nanoTime();
            model = new Model();
            model.setItems(orig);
            for (int i = 0; i < 10000; i++) {
                 model.computeNearest(pt);
            }
            lastOperationTime = (System.nanoTime() - startTime) / Math.pow(10, 9) / 10000;
            System.out.println(lastOperationTime);
        } else {
            model = new Model();
            model.setItems(orig);
            model.computeNearest(pt);
        }
        return model.getNearest();
    }

    public Hyperpoint findNearestBF(final IMultiPoint pt, boolean calculateTime) {
        IMultiPoint result = null;
        if (calculateTime == true) {
            Long startTime = System.nanoTime();
            BruteForceNearestNeighbor BF = new BruteForceNearestNeighbor(orig);
            for (int i = 0; i < 10000; i++) {
                result = BF.nearest(pt);
            }
            lastOperationTime = (System.nanoTime() - startTime) / Math.pow(10, 9) / 10000;
            System.out.println(lastOperationTime);
        } else {
            BruteForceNearestNeighbor BF = new BruteForceNearestNeighbor(orig);
            result = BF.nearest(pt);
        }
        return (Hyperpoint) result;

    }

    void calculateElement(String method, boolean calculateTime) {
        if (orig != null && reforig != null) {
            calculateElement(reforig, orig, method, calculateTime);
        }
    }
}
