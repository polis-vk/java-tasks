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
    private final List<Thread> threads;
    private final BlockingQueue<Runnable> tasks;
    private final AtomicInteger freeThreadsCount;
    private final int maxThreadCount;
    private volatile boolean isShutdown;
    private volatile int liveThreadCount;

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount < 1) {
            throw new IllegalArgumentException();
        }
        this.threads = new ArrayList<>(maxThreadCount);
        this.tasks = new LinkedBlockingQueue<>();
        this.freeThreadsCount = new AtomicInteger();
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
        if (command == null) {
            throw new IllegalArgumentException();
        }
        if (threadShouldBeCreated()) {
            synchronized (this) {
                if (threadShouldBeCreated()) {
                    Thread newThread = new Thread(new Runnable() {
                        public void run() {
                            while (!isShutdown || !tasks.isEmpty()) {
                                try {
                                    freeThreadsCount.getAndIncrement();
                                    Runnable task = tasks.take();
                                    freeThreadsCount.getAndDecrement();
                                    task.run();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    newThread.start();
                    liveThreadCount++;
                    threads.add(newThread);
                }
            }
        }
        try {
            tasks.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        synchronized (this) {
            for (Thread thread : threads) {
                thread.interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return liveThreadCount;
    }

    private boolean threadShouldBeCreated() {
        return !isShutdown && freeThreadsCount.get() == 0 && maxThreadCount > liveThreadCount;
    }
}
