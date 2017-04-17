import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        int i, j, k, l;
        int counter;
        double slope;
        LineSegment[] tempLineSegments = new LineSegment[100];

        if (points == null) {
            throw new java.lang.NullPointerException();
        }
        for (i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.NullPointerException();
        }

        for (j = 0; j < points.length; j++) {
            for (k = 0; k < points.length; k++) {
                if (j != k && points[k].compareTo(points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }

        for (i = 0; i < points.length; i++) {
            for (j = 0; j < points.length; j++) {
                for (k = 0; k < points.length; k++) {
                    for (l = 0; l < points.length; l++) {
                        if (i == j || i == k || i == l || j == k || j == l ||
                                k == l) {
                            continue;
                        }

                        // Check if order is ascending
                        if (points[i].compareTo(points[j]) != -1 ||
                                points[j].compareTo(points[k]) != -1 ||
                                points[k].compareTo(points[l]) != -1) {
                            continue;
                        }

                        slope = points[i].slopeTo(points[j]);
                        if (points[j].slopeTo(points[k]) == slope) {
                            if (points[k].slopeTo(points[l]) == slope) {
                                counter = 0;
                                for (int m = 0; m < tempLineSegments.length; m++) {
                                    if (tempLineSegments[m] != null) counter++;
                                }

                                tempLineSegments[counter] = new LineSegment(points[i], points[l]);
                            }
                        }
                    }
                }
            }
        }

        counter = 0;
        for (int m = 0; m < tempLineSegments.length; m++) {
            if (tempLineSegments[m] != null) counter++;
        }
        lineSegments = new LineSegment[counter];
        for (i = 0; i < counter; i++) {
            lineSegments[i] = tempLineSegments[i];
        }
    }

    public int numberOfSegments() {
        return this.lineSegments.length;
    }

    public LineSegment[] segments() {
        return this.lineSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
