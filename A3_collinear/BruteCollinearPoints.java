import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
	private final LineSegment[] lineSegs;
    public BruteCollinearPoints(Point[] points) {
    	// finds all line segments containing 4 points
    	if (points == null) throw new java.lang.IllegalArgumentException();
    	for(int i = 0; i < points.length; i++) {
    		if (points[i] == null) throw new java.lang.IllegalArgumentException();
    		for (int j = i + 1; j < points.length; j++) {
    			if (points[i] == points[j]) throw new java.lang.IllegalArgumentException();
    		}
    	}
    	ArrayList<LineSegment> foundSegments = new ArrayList<>();
    	Point[] pointsCopy = Arrays.copyOf(points, points.length);
    	Arrays.sort(pointsCopy);
    	int num = points.length;
    	for(int i = 0; i < num; i++) {
    		for(int j = i +1; j < num; j++) {
    			for(int k = j + 1; k < num; k++) {
    				for(int m = k + 1; m < num; m++) {
    					Point point1 = pointsCopy[i];
    					Point point2 = pointsCopy[j];
    					Point point3 = pointsCopy[k];
    					Point point4 = pointsCopy[m];
    					if (point1.slopeTo(point2) == point2.slopeTo(point3)
                                && point2.slopeTo(point3) == point3.slopeTo(point4)) {
    						foundSegments.add(new LineSegment(point1, point4));
    						
    					}
    						
    				}
    			}
    		}
    	}
    	lineSegs = foundSegments.toArray(new LineSegment[foundSegments.size()]);
    	
    }
    public           int numberOfSegments() {
    	// the number of line segments
    	return lineSegs.length;
    }
    public LineSegment[] segments() {
    	// the line segments
    	return Arrays.copyOf(lineSegs, numberOfSegments());
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