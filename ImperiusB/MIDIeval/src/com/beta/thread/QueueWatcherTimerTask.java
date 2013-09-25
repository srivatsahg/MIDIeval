package com.beta.thread;

import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

import com.beta.Controllability.ControlValuePacket;
import com.beta.Controllability.IController;

public class QueueWatcherTimerTask extends TimerTask{
	private IController controllerRef_m;
	private Queue<ControlValuePacket> queueObj_m;
	private static QueueWatcherTimerTask timerTaskInstance_m;
	private IQueueWatcherListener queueWatcherListenerRef_m;
	private static Timer timerObj_m;
	private final int TIMER_DELAY = 20;//in ms
	private final static  String s_Tag_m = "QUEUE_WATCHER_TIMER_TASK";
	public static Object lockObject_m = new Object();
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if ( !this.queueObj_m.isEmpty() ){
			//Ask Activity to notify the Write thread to start write operation
			if ( getQueueWatcherListenerRef() != null ){
				//Log.i(s_Tag_m, "Queue is filling up");
				this.getQueueWatcherListenerRef().fn_QueueIsNowFilling();
			}
			
		}
		else{
			if ( getQueueWatcherListenerRef() != null ){
				//Log.i(s_Tag_m, "Queue is empty");
				this.getQueueWatcherListenerRef().fn_QueueIsNowEmpty();
			}
		}
	}
	
	//Non-parametric Constructor
	private QueueWatcherTimerTask(){
		this.queueObj_m = IController.queueObj_m;
		QueueWatcherTimerTask.timerTaskInstance_m = this;
	}
	
	
	/*Function: To create only one instance of the QueueWatcher task during the lifecycle of application
	 *Author: Hrishik Mishra
	 *Special notes: Singleton implementation using private constructor and special method to fetch instance
	 *Usage of synchronized operation to prevent cross-thread instance creation. 
	 */
	public static QueueWatcherTimerTask fn_FetchTimerTaskInstance(){
		if ( QueueWatcherTimerTask.timerTaskInstance_m != null ){
			return  QueueWatcherTimerTask.timerTaskInstance_m;
		}
		else{
			synchronized(lockObject_m){
				if ( QueueWatcherTimerTask.timerTaskInstance_m == null ){
					QueueWatcherTimerTask.timerTaskInstance_m = new QueueWatcherTimerTask();
					QueueWatcherTimerTask.timerObj_m = new Timer();
					Log.i(s_Tag_m, "TIMER TASK and TIMER object created");
				}
				return QueueWatcherTimerTask.timerTaskInstance_m;
			}
		}
	}
	
	public void fn_StartTimerTask(){
		this.timerObj_m.schedule(this, 100, this.TIMER_DELAY);
	}

	/**
	 * @return the queueWatcherListenerRef_m
	 */
	public IQueueWatcherListener getQueueWatcherListenerRef() {
		return queueWatcherListenerRef_m;
	}

	/**
	 * @param queueWatcherListenerRef_m the queueWatcherListenerRef_m to set
	 */
	public void setQueueWatcherListenerRef(IQueueWatcherListener queueWatcherListenerRef_m) {
		this.queueWatcherListenerRef_m = queueWatcherListenerRef_m;
	}

}
