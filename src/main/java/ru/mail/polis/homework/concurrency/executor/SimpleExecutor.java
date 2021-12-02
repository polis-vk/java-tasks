package ru.mail.polis.homework.concurrency.executor;

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
    private final AtomicInteger threadsCount = new AtomicInteger(0);
    private final int maxThreadsCount;
    private volatile boolean shutdownCalled = false;
    private final AtomicInteger freeThreadsCount = new AtomicInteger(0);
    private final SingleThread[] threads;

    public SimpleExecutor(int maxThreadCount) {
        threads = new SingleThread[maxThreadCount];
        this.maxThreadsCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (shutdownCalled) {
            throw new RejectedExecutionException();
        }


        synchronized (threadsCount) {
            if (threadsCount.get() == 0 || threadsCount.get() < maxThreadsCount && freeThreadsCount.get() == 0) {
                SingleThread singleThread = new SingleThread();
                singleThread.start();
                threads[threadsCount.getAndIncrement()] = singleThread;
            }
        }
        tasks.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        shutdownCalled = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdownCalled = true;
        for (int i = 0; i < threadsCount.get(); i++) {
            while (!threads[i].isInterrupted()) {
                threads[i].interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsCount.get();
    }

    private class SingleThread extends Thread {
        @Override
        public void run() {
            try {
                while (!shutdownCalled) {
                    freeThreadsCount.incrementAndGet();
                    Runnable task = tasks.take();
                    freeThreadsCount.decrementAndGet();
                    task.run();
                }
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + " was interrupted");
            }
        }
    }
}



