package network.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import authoring.model.game.Game;
import authoring.model.level.Level;
import authoring.model.properties.Property;

public class TestDeserialization {
	public static void main (String[] args) {
//		AuthoringMessage a = null;
		Level a = null;
		
		try
	      {
	         FileInputStream fileIn = new FileInputStream("C:/temp/testSerialization1.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         a = (Level) in.readObject();
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
		
		System.out.println(a.getUniqueID());
		System.out.println(a.getActionMap().get(""));
	}
}
