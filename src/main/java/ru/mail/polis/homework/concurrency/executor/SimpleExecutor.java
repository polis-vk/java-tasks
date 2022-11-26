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
    private final AtomicInteger counterOfFreeTreads;
    private final int maxThreadCount;
    private volatile boolean isShutdown;

    public SimpleExecutor(int maxThreadCount) {
        this.threads = new ArrayList<>(maxThreadCount);
        this.tasks = new LinkedBlockingQueue<>();
        this.counterOfFreeTreads = new AtomicInteger(0);
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
        try {
            tasks.put(command);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (newThreadShouldBeCreated()) {
            synchronized (this) {
                if (newThreadShouldBeCreated()) {
                    Thread newThread = new Thread(new Runnable() {
                        public void run() {
                            while (!isShutdown) {
                                try {
                                    counterOfFreeTreads.getAndIncrement();
                                    Runnable task = tasks.take();
                                    counterOfFreeTreads.getAndDecrement();
                                    task.run();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                    newThread.start();
                    threads.add(newThread);
                }
            }
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
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }

    private boolean newThreadShouldBeCreated() {
        return !isShutdown && counterOfFreeTreads.get() == 0 && maxThreadCount > threads.size();
    }
}
