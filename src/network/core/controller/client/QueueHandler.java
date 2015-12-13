package network.core.controller.client;

import java.util.concurrent.BlockingQueue;

import network.core.connections.ICloseable;
import network.core.messages.Message;

public abstract class QueueHandler implements ICloseable {

	private BlockingQueue<Message> myQueue;
	private Boolean executing = true;
	
	public QueueHandler(String queueId) {
		ClientConnectionController.getInstance().addQueue(queueId, myQueue);
		listen();
	}
	
	private void listen() {
		new Thread() {
			@Override
			public void run() {
				try {
					while(executing) {
						handleMessage(myQueue.take());
					}
				} catch (InterruptedException e) {
					// TODO handle exception
				}
			}
		}.start();
	}
	
	public abstract void handleMessage(Message message);
	
	public void close() throws Exception {
		executing = false;
	}
	
	public Boolean isClosed() {
		return executing;
	}
	
}
