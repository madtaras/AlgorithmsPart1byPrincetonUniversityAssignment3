import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegments;

    public FastCollinearPoints(Point[] points) {
        int i, j, k, m;
        ArrayList<Point[]> lineSegmentsPoints = new ArrayList<>();
        lineSegments = new ArrayList<LineSegment>();
        boolean isDuplicate;

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

        Point[] pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);

        for (i = 0; i < points.length; i++) {
            Arrays.sort(pointsCopy, points[i].slopeOrder());

            int counter = 0;
            Point[] tmpPoint = new Point[]{points[i], points[i]};

            for (j = 1; j < pointsCopy.length - 1; j++) {
                if (points[i].slopeTo(pointsCopy[j]) == points[i].slopeTo(pointsCopy[j+1])) {
                    counter++;
                    if (tmpPoint[1].compareTo(pointsCopy[j]) < 0) {
                        tmpPoint[1] = pointsCopy[j];
                    } else if (tmpPoint[0].compareTo(pointsCopy[j]) > 0) {
                        tmpPoint[0] = pointsCopy[j];
                    }
                    if (tmpPoint[1].compareTo(pointsCopy[j + 1]) < 0) {
                        tmpPoint[1] = pointsCopy[j + 1];
                    } else if (tmpPoint[0].compareTo(pointsCopy[j + 1]) > 0) {
                        tmpPoint[0] = pointsCopy[j + 1];
                    }
                } else {
                    if (counter >= 2) {
                        isDuplicate = false;
                        for (m = 0; m < lineSegmentsPoints.size(); m++) {

                            if (lineSegmentsPoints.get(m)[0].compareTo(tmpPoint[0]) == 0 &&
                                    lineSegmentsPoints.get(m)[1].compareTo(tmpPoint[1]) == 0) {
                                isDuplicate = true;
                            }
                        }
                        if (!isDuplicate) {
                            lineSegmentsPoints.add(new Point[]{tmpPoint[0], tmpPoint[1]});
                        }
                    }

                    counter = 0;
                    tmpPoint = new Point[]{points[i], points[i]};
                }
            }
        }

        for (i = 0; i < lineSegmentsPoints.size(); i++) {
            lineSegments.add(new LineSegment(lineSegmentsPoints.get(i)[0], lineSegmentsPoints.get(i)[1]));
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[lineSegments.size()];
        result = lineSegments.toArray(result);
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