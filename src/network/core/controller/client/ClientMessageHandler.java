// This entire file is part of my masterpiece.
// Austin Liu

package network.core.controller.client;

import java.util.Map;

import network.core.controller.AMessageHandler;
import network.core.messages.format.Request;

public class ClientMessageHandler extends AMessageHandler<IClientExecuteHandler> {
	
	@Override
	protected void init(Map<Request, IClientExecuteHandler> executers) {
		
		executers.put(Request.ERROR, (message, client, queues)-> {
			System.out.println("Received the following ERROR from the server: " + (String) message.getMessage().getPaylad());
		});
		
		executers.put(Request.CONNECTION, (message, client, queues)-> {
			client.setId(message.getClientId());
			System.out.println(Request.CONNECTION.toString());
		});
		
		executers.put(Request.LOADGROUP, (message, clients, queues)-> {
			System.out.println(Request.LOADGROUP.toString());
		});
		
		executers.put(Request.CREATEGROUP, (message, clients, queues)-> {
			System.out.println(Request.CREATEGROUP.toString());
		});
		
		executers.put(Request.GENERALDATA, (message, clients, queues)-> {			
			System.out.println(Request.GENERALDATA.toString());
		});
			
		executers.put(Request.QUEUEDATA, (message, clients, queues)-> {

			if(queues.containsKey(message.getMessage().getID())) {
				queues.get(message.getMessage().getID()).add(message.getMessage());
			} else {
				System.out.println("Invalid queue identifier received from server");
			}

			System.out.println(Request.QUEUEDATA.toString());
		});
		
	}
}
