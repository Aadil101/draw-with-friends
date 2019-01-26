import java.awt.*;
import java.util.*;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 * @author Aadil Islam, Spring 2018
 */
public class Polyline implements Shape {
	// TODO: YOUR CODE HERE
	private ArrayList<Point> points;
	private Color color;
	private static final int threshold = 5;
	
	public Polyline(Point p, Color color) {
		points = new ArrayList<Point>();
		this.color = color;
		points.add(p);
	}
	
	public Polyline(String command, Color color) {
		//build the polyline based off of the string command
		String[] enter = command.split(" ");
		points = new ArrayList<Point>();
		this.color = color;
		for(int i = 1; i < enter.length-1; i+=2) {
			Point p = new Point(Integer.parseInt(enter[i]), Integer.parseInt(enter[i+1]));
			points.add(p);
		}
	}
	
	public void addPoint(Point p) {
		points.add(p);
	}
	
	@Override
	public void moveBy(int dx, int dy) {
		for(Point p : points) {
			p.setLocation(p.x + dx, p.y + dy);
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	//checks if a coordinate is contained by the polyline 
	//(near enough to based on a given threshold)
	public boolean contains(int x, int y) {
		//accesses each point in the polyline
		for(int i = 0; i < points.size()-1; i++) {
			//checks if the distance from a given point to the segment between the polyline's points is less than a given threshold
			if(Segment.pointToSegmentDistance(x, y, points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y) < threshold) {
				return true;
			}
		}
		return false;
	}

	@Override
	//draws the polyline
	public void draw(Graphics g) {
		
		//accesses each point in the polyline
		for(int i = 0; i < points.size()-1; i++) {
			//draws lines between the polyline's points to create the overall polyline
			g.setColor(color);
			g.drawLine(points.get(i).x, points.get(i).y, points.get(i+1).x, points.get(i+1).y);
			
		}
	}

	@Override
	//string representation of polyline (name, points, color)
	public String toString() {
		String result =  "polyline ";
		//adds the coordinates on to the result string for each point in polyline
		for(Point p : points) {
			result = result + " " + p.x + " " + p.y;
		}
		return result + " " + color.getRGB();
	}
}
