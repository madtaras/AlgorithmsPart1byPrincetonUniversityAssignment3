import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        int i, j, k;
        int counter;
        double slope;
        Point currentPoint;
        LineSegment[] tempLineSegments = new LineSegment[100];

        if (points == null) {
            throw new java.lang.NullPointerException();
        }
        for (i = 0; i < points.length; i++) {
            if (points[i] == null) throw new java.lang.NullPointerException();
        }

        for (j = 0; j < points.length; j++) {
            for (k = 0; k < points.length; k++) {
                if (j != k && points[k] == points[j]) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }

        printArray(points);
        currentPoint = points[0];
        StdOut.println("-------\n");
        Arrays.sort(points, currentPoint.slopeOrder());
        printArray(points);

        // TODO: copy array
        //        for (i = 0; i < points.length; i++) {
//            currentPoint = points[i];
//            // TODO: copy array
//            Arrays.sort(twoDim, currentPoint.slopeOrder());
//            // Arrays.sort()
//        }

        counter = 0;
        for (int m = 0; m < tempLineSegments.length; m++) {
            if (tempLineSegments[m] != null) counter++;
        }
        lineSegments = new LineSegment[counter];
        for (i = 0; i < counter; i++) {
            lineSegments[i] = tempLineSegments[i];
        }
    }

    private static void printArray(Point[] anArray) {
        for (int i = 0; i < anArray.length; i++) {
            System.out.print(anArray[i]);
            System.out.print(", \n");
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}