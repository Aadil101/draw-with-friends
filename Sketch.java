import java.awt.*;
import java.util.*;

/**
* @author Aadil Islam, Spring 2018
*/

public class Sketch {
	private TreeMap<Integer, Shape> shapes;
	private int NextID = -1;
	
	//sketch constructor, instantiates shapes TreeMap
	public Sketch() {
		shapes = new TreeMap<Integer, Shape>();
	}
	
	//getter for shapes TreeMap
	public TreeMap<Integer, Shape> getShapes() {
		return shapes;
	}
	
	//returns the shape in the shapes TreeMap for a given id	
	public Shape getShape(int id) {
		return shapes.get(id);
	}
	
	//checks if the given point (mouse press in our editor) is contained by a shape in shapes TreeMap
	public Shape containsShape(Point p) {
		Shape shape = null;
		//navigableKeySet to order shapes in terms of newness
		for(int id : shapes.navigableKeySet())
		{
			//checks if the given shape in the Map contains the point
			if(shapes.get(id).contains(p.x, p.y))
			{
				shape = shapes.get(id);
			}
		}
		return shape;
	}
	
	//adds a new shape to shapes TreeMap given its id and shape
	public void add(int id, Shape shape) {
		shapes.put(id, shape);
	}
	
	//acts an incrementer for addWithoutID
	public int getNextID() {
		return NextID++;
	}
	
	//adds a new shape at a unique ID (next ID)
	public void addWithoutID(Shape shape) {
		
		shapes.put(getNextID(), shape);
	}
	
	//removes the shape from the shapes TreeMap
	public void remove(int id) {
		shapes.remove(id);
	}
	
	//moves a given shape (id) by the given x and y coordinates
	public void moveShape(int id, int dx, int dy) {
		shapes.get(id).moveBy(dx, dy);
	}
	
	//recolors a given shape (id) by the given x and y coordinates
	public void recolorShape(int id, Color color) {
		shapes.get(id).setColor(color);
	}
	
	//returns the matching id for a given shape
	public Integer getId(Shape shape) {
		for(Integer id : shapes.keySet()) {
			if(shapes.get(id).equals(shape)) {
				return id;
			}
		}
		return null;
	}
	
	//draws each shape in order of newness (low to high for IDs)
	public void draw(Graphics g) {
		for(int id : shapes.navigableKeySet()) {
			shapes.get(id).draw(g);
		}
	}	
}
