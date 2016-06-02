package threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by arjuna on 31/05/16.
 */
public class ThreadPool {
    private final Queue<Runnable> jobs = new LinkedList<>();
    private final Object jobs_monitor = new Object();
    private final int nbMaxJob;
    private final int nbWorkers;
    private final List<Worker> workers;
    private boolean running = false;


    public ThreadPool(int nbWorkers, int nbMaxJob) {
        this.nbMaxJob = nbMaxJob;
        this.nbWorkers = nbWorkers;
        workers = new ArrayList<>(nbWorkers);
    }


    public void start() {
        if (running)
            return;

        running = true;
        for (int i = 0; i< nbWorkers; ++i) {
            workers.add(new Worker());
        }
    }

                                                                                                                                                     
    public void stop() {
        running = false;
        synchronized (jobs_monitor) {
            jobs_monitor.notifyAll();
            workers.clear();
        }
    }


    public boolean addJob(Runnable job) {
        synchronized (jobs_monitor) {
            if (jobs.size() >= nbMaxJob)
                return false;

            jobs.add(job);
            jobs_monitor.notify();
        }
        return true;
    }


    private Runnable popJob() throws InterruptedException {
        synchronized (jobs_monitor) {
            while (jobs.isEmpty()) {
                if (! running)
                    return null;

                jobs_monitor.wait();
            }

            return jobs.poll();
        }
    }

    private final class Worker extends Thread {
        public Worker() {
            start();
        }

        @Override
        public void run() {
            try {
                while (ThreadPool.this.running) {
                    Runnable job = ThreadPool.this.popJob();

                    if(job == null)
                        break;

                    job.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
