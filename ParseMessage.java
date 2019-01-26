import java.awt.Color;

/**
* Handle messages sent between server and client
* @author Aadil Islam, Spring 2018
*/

public class ParseMessage {
	
	public static void parseMessage(Sketch localSketch, String current) {
		//splits the current message by spaces to get the individual tokens
		String[] tokens = current.split(" ");
		//except in add's case, tokens[1] represents the shape ID - for add, it's the name of the shape
		String id = tokens[1];
		//the command being run - basis of parsing
		String command = tokens[0];
		if(command.equals("move")) {
			//moves the shape with the given id by x (tokens[2]) and y (tokens[3])
			localSketch.moveShape(Integer.parseInt(id), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
		}
		if(command.equals("recolor")) {
			//getting a color from tokens[2] RGB
			Color color = new Color(Integer.parseInt(tokens[2]));
			//recoloring based on a given id and color
			localSketch.recolorShape(Integer.parseInt(id), color);
		}
		if(command.equals("delete")) {
			//remove the shape from the sketch
			localSketch.remove(Integer.parseInt(id));
		}
		if(command.equals("add")) {
			if(id.equals("ellipse")) {
					//adds ellipse to the localsketch
					Color addColor = new Color(Integer.parseInt(tokens[6]));
					Ellipse ellipse = new Ellipse(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), 
							Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), addColor);
					localSketch.addWithoutID(ellipse);
				}
			else if(id.equals("rectangle")) {
				//adds rectangle
				Color addColor = new Color(Integer.parseInt(tokens[6]));
				Rectangle rectangle = new Rectangle(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), 
						Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), addColor);
				localSketch.addWithoutID(rectangle);
			}
			else if(id.equals("segment")) {
				//adds segment
				Color addColor = new Color(Integer.parseInt(tokens[6]));
				Segment segment = new Segment(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), 
						Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), addColor);
				localSketch.addWithoutID(segment);
			}
			else if(id.equals("polyline")) {
				//create a string holding info for the polyline
				String enter = "";
				for(int i = 2; i < tokens.length-1; i++) {
					enter += tokens[i] + " ";
				}
				int length = tokens.length-1;
				//add the polyline using a constructer to the localsketch
				Polyline polyline = new Polyline(enter, new Color(Integer.parseInt(tokens[length])));
				localSketch.addWithoutID(polyline);
			}
		}
	}
}
