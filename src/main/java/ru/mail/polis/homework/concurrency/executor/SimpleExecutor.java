package ru.mail.polis.homework.concurrency.executor;

import java.util.HashSet;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {

    private static final int RUNNING = 0;
    private static final int SHUTDOWN = 1;
    private static final int STOP = 2;
    private final int threadsLimit;
    private final LinkedBlockingQueue<Runnable> workQueue;
    private final HashSet<Thread> workers;
    private final ReentrantLock mainLock;

    private int currentState;

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount <= 0) {
            throw new IllegalArgumentException();
        }
        currentState = RUNNING;
        threadsLimit = maxThreadCount;
        workQueue = new LinkedBlockingQueue<>();
        workers = new HashSet<>(threadsLimit);
        mainLock = new ReentrantLock();
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        if (currentState != RUNNING) {
            throw new RejectedExecutionException();
        }

        try {
            mainLock.lock();
            workQueue.add(command);
            mainLock.unlock();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        if (!workQueue.isEmpty() && workers.size() < threadsLimit) {
            mainLock.lock();
            if (!workQueue.isEmpty() && workers.size() < threadsLimit) {
                Thread w = new Thread(new Worker());
                workers.add(w);
                w.start();
            }
            mainLock.unlock();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        currentState = SHUTDOWN;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        interruptWorkers();
        currentState = STOP;
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        mainLock.lock();
        try {
            return workers.size();
        } finally {
            mainLock.unlock();
        }
    }

    private void interruptWorkers() {
        for (Thread t : workers) {
            if (!t.isInterrupted()) {
                try {
                    t.interrupt();
                } catch (SecurityException ignore) {
                }
            }
        }
    }

    private final class Worker implements Runnable {
        @Override
        public void run() {
            while (currentState == RUNNING ||
                    !Thread.currentThread().isInterrupted() && !workQueue.isEmpty()) {
                Runnable nextTask = workQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }

}
