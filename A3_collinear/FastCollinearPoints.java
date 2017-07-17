import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private final LineSegment[] lineSegs;
	private final LineSegment[] uniqueLineSegs;
	
    public FastCollinearPoints(Point[] points) {
	   // finds all line segments containing 4 or more points
    	if (points == null) throw new java.lang.IllegalArgumentException();
    	for(int i = 0; i < points.length; i++) {
    		if (points[i] == null) throw new java.lang.IllegalArgumentException();
    		for (int j = i + 1; j < points.length; j++) {
    			if (points[i].compareTo(points[j]) == 0) throw new java.lang.IllegalArgumentException();
    		}
    	}
    ArrayList<LineSegment> foundSegments = new ArrayList<>();
    	for (int k = 0; k < points.length; k++) {
        	Point[] pointsCopy = Arrays.copyOf(points, points.length);
    	    Comparator<Point> comparator = pointsCopy[k].slopeOrder();
    	    Comparator<Point> xComparator = xyCoordinate();

    	    Arrays.sort(pointsCopy, xComparator);
    	    Arrays.sort(pointsCopy, comparator);

    	    int i = 1;

    	    while(i < pointsCopy.length - 2) {
    		    if (pointsCopy[0].slopeTo(pointsCopy[i]) == pointsCopy[0].slopeTo(pointsCopy[i + 2])) {
    		    	int j = 0;
    			    while ((i + 2 + j + 1) < pointsCopy.length 
    			    		&& pointsCopy[i].slopeTo(pointsCopy[i + 2]) == pointsCopy[i].slopeTo(pointsCopy[i + 2 + j + 1])) {
    			    	j++;
    			    }
    			    if (pointsCopy[0].compareTo(pointsCopy[i + 2 + j]) > 0) {
    			    	foundSegments.add(new LineSegment(pointsCopy[i], pointsCopy[0]));
    			    }
    			    else if (pointsCopy[0].compareTo(pointsCopy[i]) < 0) {
    			    	foundSegments.add(new LineSegment(pointsCopy[0], pointsCopy[i + j + 2]));
    			    }
    			    else {
    			    	foundSegments.add(new LineSegment(pointsCopy[i], pointsCopy[i + j + 2]));
    			    }

    			    i += (3 + j);
			    }

    		    else i++;
    	   }
    	}
	    lineSegs = foundSegments.toArray(new LineSegment[foundSegments.size()]);
	    //delete duplcates
    	ArrayList<LineSegment> distinctSegments = new ArrayList<>();
    	LineSegment[] lineSegsCopy = Arrays.copyOf(lineSegs, lineSegs.length);
	    Comparator<LineSegment> segComparator = compareSeg();
    	Arrays.sort(lineSegsCopy, segComparator);
    	int pointer2 = 1;
    	int pointer1 = 0;
    	while (pointer1 < lineSegsCopy.length) {
    		while (pointer2 < lineSegsCopy.length && 
    				lineSegsCopy[pointer1].toString().equals(lineSegsCopy[pointer2].toString())) {
    			pointer2++;
    		}
    		distinctSegments.add(lineSegsCopy[pointer1]);
    		pointer1 = pointer2;
    		
    	}
    	uniqueLineSegs = distinctSegments.toArray(new LineSegment[distinctSegments.size()]);
   }
   

    private final Comparator<Point> xyCoordinate() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.compareTo(o2);
            }
        };
    }
    
    private final Comparator<LineSegment> compareSeg() {
        return new Comparator<LineSegment>() {
            @Override
            public int compare(LineSegment l1, LineSegment l2) {
                return l1.toString().compareTo(l2.toString());
            }
        };
    }
    
    public int numberOfSegments() {
	   // the number of line segments
	    return uniqueLineSegs.length;
    }
   
   public LineSegment[] segments() {
	   // the line segments
	   return Arrays.copyOf(uniqueLineSegs, numberOfSegments());
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
       StdOut.println(collinear.numberOfSegments());
       for (LineSegment segment : collinear.segments()) {
    	   StdOut.println(segment);
           segment.draw();
       }
       StdDraw.show();
   }
}