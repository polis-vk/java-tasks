package ru.mail.polis.homework.concurrency.executor;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

public class si__leThreadExecutor {
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(-1 << COUNT_BITS, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int COUNT_MASK = (1 << COUNT_BITS) - 1;
    private static final int SHUTDOWN = 0;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;

    private volatile int liveThreadCount;
    private volatile boolean terminated;

    private static int workerCount(int c) {
        return c & COUNT_MASK;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    private static boolean runStateLessThan(int c) {
        return c < STOP;
    }

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    private void decrementWorkerCount() {
        ctl.addAndGet(-1);
    }

    private final BlockingQueue<Runnable> workQueue;
    private final ReentrantLock mainLock = new ReentrantLock();
    private final HashSet<Worker> workers = new HashSet<>();
    private int largestPoolSize;
    private final ThreadFactory threadFactory;
    private final AbortPolicy handler;
    private final long keepAliveTime;
    private final int corePoolSize;

    private final class Worker extends AbstractQueuedSynchronizer implements Runnable {
        final Thread thread;
        Runnable firstTask;
        volatile long completedTasks;

        Worker(Runnable firstTask) {
            setState(-1);
            this.firstTask = firstTask;
            this.thread = getThreadFactory().newThread(this);
        }

        public void run() {
            Worker w = this;
            Runnable task = w.firstTask;
            w.firstTask = null;
            w.unlock();
            boolean completedAbruptly = true;
            try {
                while (task != null || (task = getTask()) != null) {
                    w.lock();
                    try {
                        task.run();
                    } finally {
                        task = null;
                        w.completedTasks++;
                        w.unlock();
                    }
                }
                completedAbruptly = false;
            } finally {
                processWorkerExit(w, completedAbruptly);
            }
        }

        protected boolean tryAcquire(int unused) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        protected boolean tryRelease(int unused) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        public void lock() {
            acquire(1);
        }

        public boolean tryLock() {
            return tryAcquire(1);
        }

        public void unlock() {
            release(1);
        }

        void interruptIfStarted() {
            Thread t;
            if (getState() >= 0 && (t = thread) != null && !t.isInterrupted()) {
                try {
                    t.interrupt();
                } catch (SecurityException ignore) {

                }
            }
        }
    }

    private void advanceRunState(int targetState) {
        while (true) {
            int c = ctl.get();
            if (runStateAtLeast(c, targetState) || ctl.compareAndSet(c, ctlOf(targetState, workerCount(c)))) break;
        }
    }

    final void tryTerminate() {
        while (true) {
            int c = ctl.get();
            if (isRunning(c) || runStateAtLeast(c, TIDYING) || (runStateLessThan(c) && !workQueue.isEmpty())) return;
            if (workerCount(c) != 0) {
                interruptIdleWorkers(true);
                return;
            }
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
                    ctl.set(ctlOf(3 << COUNT_BITS, 0));
                    mainLock.newCondition().signalAll();
                    return;
                }
            } finally {
                mainLock.unlock();
            }
        }
    }

    private void interruptIdleWorkers(boolean onlyOne) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for (Worker w : workers) {
                Thread t = w.thread;
                if (!t.isInterrupted() && w.tryLock()) {
                    try {
                        t.interrupt();
                    } catch (SecurityException ignore) {
                    } finally {
                        w.unlock();
                    }
                }
                if (onlyOne) break;
            }
        } finally {
            mainLock.unlock();
        }
    }

    final void reject(Runnable command) {
        handler.rejectedExecution(command, this);
    }

    private boolean addWorker(Runnable firstTask) {
        int c = ctl.get();
        do {
            if (runStateAtLeast(c, SHUTDOWN) && (runStateAtLeast(c, STOP) || firstTask != null || workQueue.isEmpty()))
                return false;
        } while (!ctl.compareAndSet(c, c + 1));
        boolean workerStarted = false;
        boolean workerAdded = false;
        Worker w = new Worker(firstTask);
        final Thread t = w.thread;
        if (t != null) {
            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try {
                int c1 = ctl.get();
                if (isRunning(c1) || (runStateLessThan(c1) && firstTask == null)) {
                    if (t.getState() != Thread.State.NEW) throw new IllegalThreadStateException();
                    workers.add(w);
                    workerAdded = true;
                    int s = workers.size();
                    if (s > largestPoolSize) largestPoolSize = s;
                }
            } finally {
                mainLock.unlock();
            }
            if (workerAdded) {
                t.start();
                workerStarted = true;
            }
        }
        return workerStarted;
    }

    private void processWorkerExit(Worker w, boolean completedAbruptly) {
        if (completedAbruptly) decrementWorkerCount();
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            workers.remove(w);
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
    }

    private Runnable getTask() {
        while (true) {
            int c = ctl.get();
            if (runStateAtLeast(c, SHUTDOWN) && (runStateAtLeast(c, STOP) || workQueue.isEmpty())) {
                decrementWorkerCount();
                return null;
            }
            boolean timed = workerCount(c) > corePoolSize;
            try {
                Runnable r = timed ? workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) : workQueue.take();
                if (r != null) return r;
            } catch (InterruptedException ignored) {
            }
        }
    }

    private static final AbortPolicy defaultHandler = new AbortPolicy();

    public static class AbortPolicy {
        public AbortPolicy() {
        }

        public void rejectedExecution(Runnable r, si__leThreadExecutor e) {
            throw new RejectedExecutionException();
        }
    }

    public si__leThreadExecutor(int maxSize) {
        this.corePoolSize = maxSize;
        this.keepAliveTime = TimeUnit.MILLISECONDS.toNanos(0L);
        this.workQueue = new LinkedBlockingQueue<>();
        this.threadFactory = Executors.defaultThreadFactory();
        this.handler = defaultHandler;
    }

    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        int c = ctl.get();
        if (workerCount(c) < corePoolSize) {
            if (addWorker(command)) {
                return;
            }
            c = ctl.get();
        }
        if (isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if (!isRunning(recheck)) {
                reject(command);
            }
        } else if (!addWorker(command)) {
            reject(command);
        }
    }

    public void shutdown() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            advanceRunState(SHUTDOWN);
            interruptIdleWorkers(false);
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
    }

    public void shutdownNow() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            advanceRunState(STOP);
            for (Worker w : workers)
                w.interruptIfStarted();
        } finally {
            mainLock.unlock();
        }
        tryTerminate();
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    public int getPoolSize() {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            return workers.size();
        } finally {
            mainLock.unlock();
        }
    }
}
