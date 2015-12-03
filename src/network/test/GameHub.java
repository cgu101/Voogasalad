package network.test;

import java.io.IOException;

import network.core.Hub;

public class GameHub {
	private final static int PORT = 37818;
    
    public static void main(String[] args) {
        try {
            new Hub(PORT);
        }
        catch (IOException e) {
            System.out.println("Can't create listening socket.  Shutting down.");
        }
    }
}
