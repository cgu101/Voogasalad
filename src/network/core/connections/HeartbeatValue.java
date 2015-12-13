package network.core.connections;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public class HeartbeatValue {
	
	private Long updateTime = System.currentTimeMillis()/1000L;
	
	public Long getUpdateTime() {
		return updateTime;
	}
	
	public void update() {
		updateTime = System.currentTimeMillis()/1000L;
	}
}
