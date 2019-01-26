import java.io.*;
import java.awt.Color;
import java.net.Socket;

/**
 * Handles communication to/from the server for the editor
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 * @author Aadil Islam, Spring 2018
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Handle messages
			// TODO: YOUR CODE HERE
			String current;							  //current message
			Sketch editorSketch = editor.getSketch(); //local editor sketch
			
			while(!(current = in.readLine()).equals(null))
			{
				//parse message (see separate class)
				ParseMessage.parseMessage(editorSketch, current);
				//repaints the editor after a message is parsed
				// need this repaint here because any repaint in editor is only called while in the process of drawing the shape...
				// ...need one after getting a response from the server because you only send the request before, not actually paint it
				editor.repaint(); 
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
	}	

	// Send editor requests to the server
	// TODO: YOUR CODE HERE
	//sends move command to server
	public void requestToMove(int x, int y, int id) {
		send("move" + " " + id + " " + x + " " + y);
	}
	
	//sends recolor command to server
	public void requestToRecolor(int id, int color) {
		send("recolor" + " " + id + " " + color);
	}
	
	//sends delete command to server
	public void requestToDelete(int id) {
		send("delete" + " " + id);
	}
	
	//sends add command to server
	public void requestToAdd(Shape shape) {
		send("add" + " " + shape.toString());
	}
}
