package network.core.connections;

public class Heartbeat {
	
	private Long updateTime = System.currentTimeMillis()/1000L;
	
	public Long getUpdateTime() {
		return updateTime;
	}
	
	public void update() {
		updateTime = System.currentTimeMillis()/1000L;
	}
}
