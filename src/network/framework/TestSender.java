package network.framework;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import network.framework.format.Mail;
import network.framework.format.Request;
import network.instances.DataDecorator;

public class TestSender {
	
	private static final String ipaddress = "localhost";
	private static final Integer port = 6969;
	
	public static void main(String...args) {
		try {
			Socket connection = new Socket(ipaddress, port);
			System.out.println("Sender just connected to: " + connection.getLocalAddress());
			
			ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
			
			Mail toSend = new DataDecorator(Request.MODIFY, null, null);
			
			out.writeObject(toSend);
			out.flush();
			
			out.close();
			in.close();
			connection.close();
			
		} catch(Exception e) {}		
	}
}
