package network.framework;

import java.io.IOException;

import network.core.Hub;
import network.core.TempHub;

public class GameHub {
	private final static int PORT = 6969;
    
    public static void main(String[] args) {
        try {
            new Hub(PORT); 
        }
        catch (IOException e) {
            System.out.println("Can't create listening socket.  Shutting down.");
        }
    }
}
