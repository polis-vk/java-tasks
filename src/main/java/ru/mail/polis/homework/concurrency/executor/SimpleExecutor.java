package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.Collections;
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
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final List<Thread> pool;
    private final int maximumPoolSize;
    private final AtomicInteger waitingThreads = new AtomicInteger();
    private volatile boolean isShutDown;

    public SimpleExecutor(int maxThreadCount) {
        maximumPoolSize = maxThreadCount;
        pool = Collections.synchronizedList(new ArrayList<>(maxThreadCount));
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (isShutDown) {
            throw new RejectedExecutionException();
        }
        synchronized (this) {
            tasks.add(command);
            if (canAddThread()) {
                Thread thread = new Worker();
                pool.add(thread);
                thread.start();
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        synchronized (pool) {
            for (Thread thread : pool) {
                thread.interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return pool.size();
    }

    private boolean canAddThread() {
        return getLiveThreadsCount() < maximumPoolSize && waitingThreads.get() == 0;
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (!isShutDown || !tasks.isEmpty()) {
                Runnable task;
                try {
                    waitingThreads.incrementAndGet();
                    task = tasks.take();
                } catch (InterruptedException ignored) {
                    return;
                }
                waitingThreads.decrementAndGet();
                task.run();
            }
        }
    }
}
