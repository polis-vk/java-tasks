package ru.mail.polis.homework.concurrency.executor;

import java.util.HashSet;
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
    private final BlockingQueue<Runnable> taskQueue;
    private final int maxThreads;
    private final HashSet<Thread> threadSet;
    private final AtomicInteger freeThreads;
    private volatile boolean running;
    private volatile int existingThreads;

    public SimpleExecutor(int maxThreadCount) {
        taskQueue = new LinkedBlockingQueue<>();
        maxThreads = maxThreadCount;
        threadSet = new HashSet<>();
        freeThreads = new AtomicInteger(0);
        running = true;
        existingThreads = 0;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }

        if (!running) {
            throw new RejectedExecutionException();
        }

        if (freeThreads.get() == 0 && existingThreads < maxThreads) {
            synchronized (freeThreads) {
                if (running && freeThreads.get() == 0 && existingThreads < maxThreads) {
                    threadSet.add(new WorkThread());
                    existingThreads++;
                }
            }
        }
        taskQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        running = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        synchronized (freeThreads) {
            shutdown();
            for (Thread thread : threadSet) {
                thread.interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return existingThreads;
    }

    class WorkThread extends Thread {
        public WorkThread() {
            start();
        }

        @Override
        public void run() {
            while (!this.isInterrupted() && (running || !taskQueue.isEmpty())) {
                freeThreads.incrementAndGet();
                try {
                    Runnable task = taskQueue.take();
                    freeThreads.decrementAndGet();
                    task.run();
                } catch (InterruptedException exc) {
                    throw new RuntimeException(exc);
                }
            }
        }
    }
}
