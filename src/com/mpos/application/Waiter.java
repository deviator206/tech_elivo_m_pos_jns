package com.mpos.application;

import android.content.Context;
import android.widget.Toast;

import com.mpos.framework.common.Logger;

public class Waiter extends Thread {
	private static final String TAG = Waiter.class.getName();
	private long lastUsed;
	private long period;
	private boolean stop;
	public Waiter(long period) {
		this.period = period;
		stop = false;
	}
	
	public void run() {
		long idle = 0;
		this.touch();
		do {
			idle = System.currentTimeMillis() - lastUsed;
			Logger.d(TAG, "Application is idle for " + idle + " ms " + period);
			try {
				Thread.sleep(5000); // check every 5 seconds
			} catch (InterruptedException e) {
				Logger.d(TAG, "Waiter interrupted!");
			}
			if (idle > period) {
				idle = 0;
				// do something here - e.g. call popup or so
				if(MPOSApplication.baseActivity!=null){
					MPOSApplication.baseActivity.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Logger.d(TAG, "System is idle" );
							MPOSApplication.baseActivity.sessionTimeOut();
							//Toast.makeText(MPOSApplication.baseActivity, "System is idle" + MPOSApplication.isProcessingForMasterDb, Toast.LENGTH_SHORT).show();
							touch();
						}
					});
				
				}
			}
		} while (!stop);
		Logger.d(TAG, "Finishing Waiter thread");
	}

	public synchronized void touch() {
		lastUsed = System.currentTimeMillis();
	}

	public synchronized void forceInterrupt() {
		this.interrupt();
	}

	public synchronized void setPeriod(long period) {
		this.period = period * 60 * 1000;
	}

}