import java.io.*;
import java.net.Socket;

/**
 * Handles communication between the server and one client, for SketchServer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate SketchServerCommunicator
 * @author Aadil Islam, Spring 2018
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;			// handling communication for

	public SketchServerCommunicator(Socket sock, SketchServer server) {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg
	 */
	public void send(String msg) {
		out.println(msg);
	}
	
	/**
	 * Keeps listening for and handling (your code) messages from the client
	 */
	public void run() {
		try {
			System.out.println("someone connected");
			
			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Tell the client the current state of the world. Broadcast
			// TODO: YOUR CODE HERE
			for(int ID : server.getSketch().getShapes().keySet()) {
				send("add " + (server.getSketch().getShapes().get(ID)));
			}

			// Keep getting and handling messages from the client
			// TODO: YOUR CODE HERE
			
			String current;
			Sketch serverSketch = server.getSketch();
			
			while(!((current = in.readLine()) == null)) {
				//parse message
				//update method that takes in sketch and then does something
				ParseMessage.parseMessage(serverSketch, current); //add in concurrency synchronization
				System.out.println("recieved: " + current);
				server.broadcast(current);
			}

			// Clean up -- note that also remove self from server's list so it doesn't broadcast here
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
