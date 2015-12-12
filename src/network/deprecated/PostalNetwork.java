package network.deprecated;

import network.framework.format.Mail;
import network.framework.format.Proxy;

public class PostalNetwork {
	public static void packageAndDeliver (Proxy p, Mail m) {
		if (p != null) {
			p.send(m);
		} 
	}
}
