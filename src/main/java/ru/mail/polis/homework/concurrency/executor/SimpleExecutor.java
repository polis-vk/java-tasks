package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

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

    private volatile boolean isShutdown;
    private final BlockingQueue<Runnable> taskQueue;
    private final List<Thread> threads;
    private final AtomicInteger liveThreadsCount;
    private final AtomicInteger availableThreads;
    private final int maxThreadCount;


    public SimpleExecutor(int maxThreadCount) {
        taskQueue = new LinkedBlockingQueue<>();
        threads = new ArrayList<>(maxThreadCount);
        liveThreadsCount = new AtomicInteger();
        availableThreads = new AtomicInteger();
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown) {
            throw new RejectedExecutionException();
        }

        if (isAllowedCreatedThreads()) {
            synchronized (this) {
                if (availableThreads.get() == 0 && isAllowedCreatedThreads()) {
                    Thread thread = new Thread(new Task());
                    threads.add(thread);
                    thread.start();
                    liveThreadsCount.incrementAndGet();
                }
            }
        }

        taskQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutdown = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        synchronized (this) {
            threads.forEach(Thread::interrupt);
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return liveThreadsCount.get();
    }

    private class Task implements Runnable {
        @Override
        public void run() {
            while (!taskQueue.isEmpty() || !isShutdown) {
                try {
                    availableThreads.incrementAndGet();
                    Runnable task = taskQueue.take();
                    availableThreads.decrementAndGet();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isAllowedCreatedThreads() {
        return liveThreadsCount.get() < maxThreadCount;
    }
}
