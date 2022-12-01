package ru.mail.polis.homework.concurrency.executor;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class si__leThreadExecutor {
    private final AtomicInteger ctl = new AtomicInteger();
    private final AtomicInteger status = new AtomicInteger();

    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private final HashSet<Worker> workers = new HashSet<>();
    private final int largestPoolSize;

    private static final int STOP = 1;

    si__leThreadExecutor(int maxThreadCount) {
        largestPoolSize = maxThreadCount;
    }

    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        if (getPoolSize() < largestPoolSize) {
            addWorker();
        }
        if (status.get() == STOP) {
            throw new RejectedExecutionException("Task " + command + " rejected");
        }
        workQueue.add(command);
    }

    private void addWorker() {
        synchronized (this) {
            if (status.get() != STOP && getPoolSize() < largestPoolSize && ctl.get() == 0) {
                Worker worker = new Worker();
                workers.add(worker);
                worker.start();
            }
        }
    }

    public void shutdown() {
        status.set(STOP);
    }

    public void shutdownNow() {
        status.set(STOP);
        synchronized (this) {
            for (Worker w : workers) {
                w.interrupt();
            }
        }
    }

    public int getPoolSize() {
        synchronized (workers) {
            return workers.size();
        }
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            try {
                while (status.get() != STOP || !workQueue.isEmpty()) {
                    ctl.incrementAndGet();
                    Runnable task = workQueue.take();
                    ctl.decrementAndGet();
                    task.run();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }
}
