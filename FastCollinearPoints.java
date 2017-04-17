import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        int i, j, k;
        int counter;
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

        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);
        Point[] selectedFour = new Point[4];

        for (i = 0; i < points.length; i++) {
            Arrays.sort(pointsCopy, points[i].slopeOrder());
            for (j = 0; j < pointsCopy.length - 2; j++) {
                double slope1 = points[i].slopeTo(pointsCopy[j]);
                double slope2 = points[i].slopeTo(pointsCopy[j+1]);
                double slope3 = points[i].slopeTo(pointsCopy[j+2]);

                if (slope1 == slope2 && slope2 == slope3) {
                    selectedFour[0] = points[i];
                    selectedFour[1] = pointsCopy[j];
                    selectedFour[2] = pointsCopy[j+1];
                    selectedFour[3] = pointsCopy[j+2];

                    SortArray(selectedFour);

                    LineSegment newLineSegment = new LineSegment(selectedFour[0], selectedFour[3]);
                    String newLineSegmentStr = newLineSegment.toString();
                    counter = 0;
                    boolean isDuplicate = false;
                    for (int m = 0; m < tempLineSegments.length; m++) {
                        if (tempLineSegments[m] != null) {
                            counter++;
                            if (tempLineSegments[m].toString().equals(newLineSegmentStr)) isDuplicate = true;
                        }
                    }

                    if (!isDuplicate) tempLineSegments[counter] = newLineSegment;
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


    private static void SortArray(Point[] miniPointsArray) {
        Point temp;

        for (int i = 0; i < miniPointsArray.length; i++) {
            for (int j = i+1; j < miniPointsArray.length; j++) {
                if (miniPointsArray[i].compareTo(miniPointsArray[j]) == 1) {
                    temp = miniPointsArray[i];
                    miniPointsArray[i] = miniPointsArray[j];
                    miniPointsArray[j] = temp;
                }
            }
        }
    }

    public int numberOfSegments() {
        return this.lineSegments.length;
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[this.lineSegments.length];
        System.arraycopy(this.lineSegments, 0, result, 0, this.lineSegments.length);
        return result;
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