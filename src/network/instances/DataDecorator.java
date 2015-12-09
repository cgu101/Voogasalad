package network.instances;

import java.io.Serializable;
import java.util.Deque;

import network.framework.format.Mail;
import network.framework.format.Request;

public class DataDecorator implements Mail {
	
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = -6320582607260270478L;
	
	Request request;
	
	Serializable data;
	Deque<String> anscestralPath;

	public DataDecorator (Request r, Serializable data, Deque<String> path) {
		this.request = r;
		
		this.data = data;
		this.anscestralPath = path;
	}
	
	@Override
	public Request getRequest() {
		return request;
	}

	@Override
	public Serializable getData() {
		return data;
	}

	@Override
	public Deque<String> getPath() {
		return anscestralPath;
	}

}
