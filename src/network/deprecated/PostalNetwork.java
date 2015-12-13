package network.deprecated;

public class PostalNetwork {
	public static void packageAndDeliver (Proxy p, Mail m) {
		if (p != null) {
			p.send(m);
		} 
	}
}
