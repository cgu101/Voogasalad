package network.core.controller.client;

import java.util.concurrent.BlockingQueue;

import network.core.messages.Message;

public abstract class QueueHandler {

	private BlockingQueue<Message> myQueue;
	
	public QueueHandler(String queueId) {
		ClientConnectionController.getInstance().addQueue(queueId, myQueue);
		listen();
	}
	
	private void listen() {
		new Thread() {
			@Override
			public void run() {
				try {
					handleMessage(myQueue.take());
				} catch (InterruptedException e) {
					// TODO handle error message;
				}
			}
		}.start();
	}
	
	public abstract void handleMessage(Message message);
	
}
