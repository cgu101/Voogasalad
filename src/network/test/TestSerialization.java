package network.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import authoring.model.game.Game;
import authoring.model.level.Level;
import authoring.model.properties.Property;

public class TestSerialization {
	public static void main (String[] args) {

		//		AuthoringMessage a = new AuthoringMessage(RequestType.LOAD_GAME, "Game1", new SGameState("TestGame"));
		Level a = new Level("questionaire");

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

		Level b = null;

		try
		{
			FileInputStream fileIn = new FileInputStream("C:/temp/testSerialization1.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			b = (Level) in.readObject();
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

		Level c = b;

		try
		{
			FileOutputStream fileOut =
					new FileOutputStream("C:/temp/testSerialization1.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(c);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in /tmp/testSerialization1.ser");
		}catch(IOException i)
		{
			i.printStackTrace();
		}
		
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
		}catch(ClassNotFoundException crr)
		{
			System.out.println("AuthoringMessage class not found");
			crr.printStackTrace();
			return;
		}

		if (a != c) {
			//			System.out.println("They are the same!");
			System.out.println(a);
			System.out.println(b);
		}
	}
}
