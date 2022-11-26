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
    private final HashSet<WorkThread> threadSet;
    private volatile boolean isRunning;
    private final AtomicInteger freeThreads;
    private final int maxThreadCount;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        taskQueue = new LinkedBlockingQueue<>();
        threadSet = new HashSet<>();
        freeThreads = new AtomicInteger(0);
        isRunning = true;
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

        if (!isRunning) {
            throw new RejectedExecutionException();
        }

        try {
            taskQueue.put(command);
        } catch (InterruptedException exc) {
            throw new RuntimeException(exc);
        }

        synchronized (freeThreads) {
            if (isRunning && freeThreads.get() == 0 && threadSet.size() < maxThreadCount) {
                threadSet.add(new WorkThread());
            }
        }
    }


    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isRunning = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        for (WorkThread obj : threadSet) {
            obj.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadSet.size();
    }

    class WorkThread extends Thread {

        public WorkThread() {
            start();
        }

        @Override
        public void run() {
            Runnable task;
            while (!this.isInterrupted() && (isRunning || !taskQueue.isEmpty())) {
                freeThreads.incrementAndGet();
                try {
                    task = taskQueue.take();
                    freeThreads.decrementAndGet();
                    task.run();
                } catch (InterruptedException exc) {
                    throw new RuntimeException(exc);
                }
            }
        }
    }
}
