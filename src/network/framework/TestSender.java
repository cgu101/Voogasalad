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
			
			Mail toSend = new DataDecorator(Request.ADD, null, null);
			Mail toSend1 = new DataDecorator(Request.DELETE, null, null);
			Mail toSend2 = new DataDecorator(Request.MODIFY, null, null);
			Mail toSend3 = new DataDecorator(Request.TRANSITION, null, null);

			out.writeObject(toSend);
			out.writeObject(toSend1);
			out.writeObject(toSend2);
			out.writeObject(toSend3);
			out.flush();
			
			out.close();
			in.close();
			connection.close();
			
		} catch(Exception e) {}		
	}
}
