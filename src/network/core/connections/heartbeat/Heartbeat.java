package network.core.connections.heartbeat;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Chris Streiffer (cds33) and Austin Liu (abl17)
 */

public abstract class Heartbeat {
	
	private static final Long MAX_DELAY = 300000l;	
	private Long updateTime = System.currentTimeMillis()/1000L;
		
	private Timer timer;
	private Long delay;
	
	public Heartbeat(Long delay) {
		this.delay = delay;
		initHeartbeatTimer();
	}
	
	private void initHeartbeatTimer() {
		timer = new Timer();
		long initial_delay = (long) ((long) MAX_DELAY*Math.random());
		timer.scheduleAtFixedRate(new TimerTask() {

		    @Override
		    public void run() {
		    	heartbeat();
		    }

		}, initial_delay, delay);
	}
	
	public Long getUpdateTime() {
		return updateTime;
	}
	
	public void update() {
		updateTime = System.currentTimeMillis()/1000L;
	}
	
	public abstract void heartbeat();
	
}
