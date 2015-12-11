package network.util;

import javafx.application.Platform;

public class ThreadFactory {
	public static void execute (Runnable r, ThreadType t) {
		switch (t) {
		case DEFAULT:
			//TODO
			break;
		case JAVAFX:
			Platform.runLater(r);
			break;
		default:
			break;
		
		}
			
	}
}
