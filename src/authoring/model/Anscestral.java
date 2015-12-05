package authoring.model;

import java.util.Deque;

import network.framework.Mail;
import network.framework.Request;

public interface Anscestral {
	public Deque<String> getAnscestralPath ();
	
	public default void forward (Deque<String> aDeque, Anscestral a, Mail mail) {
		if (!aDeque.isEmpty()) {
			aDeque.removeFirst();
			a.forward(aDeque, a, mail);
		} else {
			a.process(mail);
		}
	}
	
	public void process (Mail mail) ;
}
