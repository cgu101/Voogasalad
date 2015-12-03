package network.framework;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TestDeserialization {
	public static void main (String[] args) {
		AuthoringMessage a = null;
		
		try
	      {
	         FileInputStream fileIn = new FileInputStream("C:/temp/testSerialization1.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         a = (AuthoringMessage) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("AuthoringMessage class not found");
	         c.printStackTrace();
	         return;
	      }
		
		System.out.println(a.getRequest());
		System.out.println(a.getData());
		System.out.println(a.getGameName());
	}
}
