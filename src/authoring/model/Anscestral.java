package authoring.model;

import java.util.Deque;

import network.deprecated.RequestType;
import network.framework.format.Mail;

public interface Anscestral {
	public Deque<String> getAnscestralPath ();
	
	public default void forward (Deque<String> aDeque, Mail mail) {
		if (!aDeque.isEmpty()) {
			String aID = aDeque.poll();
			getChild(aID).forward(aDeque, mail);
		} else {
			process(mail);
		}
	}
	
	public void process (Mail mail) ;
	
	public Anscestral getChild (String id);
}
