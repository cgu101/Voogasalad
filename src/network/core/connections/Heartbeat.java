package network.core.connections;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Heartbeat {
	
	private static final Long MAX_DELAY = 300000l;
	
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
	
	public abstract void heartbeat();
	
}
