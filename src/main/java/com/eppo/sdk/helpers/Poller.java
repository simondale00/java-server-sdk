package com.eppo.sdk.helpers;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Poller Class
 */
public class Poller extends TimerTask {
    private IPollerTask task;
    private static Poller instance = null;
    private long intervalInMillis = 0;
    private long jitterInMillis = 0;

    private boolean isStopped = false;

    private Poller(IPollerTask task, long intervalInMillis, long jitterInMillis) {
        this.task = task;
        this.intervalInMillis = intervalInMillis;
        this.jitterInMillis = jitterInMillis;
    }

    /**
     * This function is used to stop polling
     */
    public void stop() {
        this.isStopped = true;
    }

    /**
     * This function is called when instance is scheduled
     */
    @Override
    public void run() {
        try {
            boolean shouldRunNextTime = this.task.run();
            if (shouldRunNextTime && !this.isStopped) {
                Timer timer = new Timer();
                timer.schedule(
                        Poller.getInstance(),
                        this.intervalInMillis - ((long) Math.random() * this.jitterInMillis)
                );
            }
        } catch (Exception e) {
            // do not run next time;
        }
    }

    /**
     * This function is used to initialize the Poller with polling task, interval and jitter
     *
     * @param task
     * @param interval
     * @param jitter
     * @return
     */
    public static Poller init(IPollerTask task, long interval, long jitter) {
        if (Poller.instance == null) {
            Poller.instance = new Poller(task, interval, jitter);
        }

        return Poller.instance;
    }

    /**
     * This function is used to get Poller instance
     * @return
     */
    public static Poller getInstance() {
        return Poller.instance;
    }

}