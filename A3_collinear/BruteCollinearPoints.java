
public class BruteCollinearPoints {
    public BruteCollinearPoints(Point[] points) {
    	// finds all line segments containing 4 points
    	int num = points.length;
    	Point[] selectedPoints = (Point[]) new Object[4];
    	for(int i = 0; i < num; i++) {
    		for(int j = i+1; j < num; j++) {
    			for(int k = j+1; k < num; k++) {
    				for(int m = k+1; m < num; m++) {
    					selectedPoints[0] = points[i];
    					selectedPoints[1] = points[j];
    					selectedPoints[2] = points[k];
    					selectedPoints[3] = points[m];
    					
    				}
    			}
    		}
    	}
    	
    }
    public           int numberOfSegments() {
    	// the number of line segments
    }
    public LineSegment[] segments() {
    	// the line segments
    }
}
