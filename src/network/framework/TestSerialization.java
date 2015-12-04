package network.framework;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestSerialization {
	public static void main (String[] args) {
		
		AuthoringMessage a = new AuthoringMessage(Request.LOAD_GAME, "Game1", new SGameState("TestGame"));
		
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("C:/temp/testSerialization1.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(a);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in /tmp/testSerialization1.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
}