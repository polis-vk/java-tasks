package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 *
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 *
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {

    private final int maxThreadCount;
    private final List<Thread> threads;
    private final Lock lock = new ReentrantLock();
    private final AtomicInteger threadsSize = new AtomicInteger(0);
    private final AtomicInteger nFreeThreads = new AtomicInteger(0);
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    private volatile boolean isShutdown = false;

    private class Task implements Runnable {
        @Override
        public void run() {
            Runnable activeTask;
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    nFreeThreads.incrementAndGet();
                    activeTask = getTask();
                    if (activeTask == null) {
                        break;
                    }
                    nFreeThreads.decrementAndGet();
                    activeTask.run();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount <= 0) {
            throw new IllegalArgumentException("maxThreadCount must be a positive number");
        }
        this.maxThreadCount = maxThreadCount;
        threads = new ArrayList<>(maxThreadCount);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown) {
            throw new RejectedExecutionException("Unable to add new task when shutdown");
        }

        if (command == null) {
            throw new IllegalArgumentException("Illegal null command");
        }

        addTask(command);
        if (unableToCreateNewThread()) {
            return;
        }

        try {
            lock.lock();
            if (unableToCreateNewThread()) {
                return;
            }
            threadsSize.incrementAndGet();
            Thread newThread = new Thread(new Task());
            threads.add(newThread);
            newThread.start();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public synchronized void shutdownNow() {
        if (isShutdown) {
            return;
        }
        shutdown();
        for (Thread thread: threads) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsSize.get();
    }

    private void addTask(Runnable task) {
        try {
            tasks.put(task);
        } catch (InterruptedException ignored) {
        }
    }

    private Runnable getTask() throws InterruptedException {
        return isShutdown ? tasks.poll() : tasks.take();
    }

    private boolean unableToCreateNewThread() {
        return nFreeThreads.get() != 0 || threadsSize.get() >= maxThreadCount;
    }

}
