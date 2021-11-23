package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

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
    private final int maxThreadCount;
    private final LazyThread[] lazyThreads;
    private final BlockingQueue<Runnable> tasks;
    private boolean isWorking = true;
    private int threadsCount = 0;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        lazyThreads = new LazyThread[maxThreadCount];
        tasks = new LinkedBlockingQueue<>();
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public synchronized void execute(Runnable command) {
        if (!isWorking) {
            throw new RejectedExecutionException();
        }
        try {
            tasks.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (threadsCount < maxThreadCount && !isAvailableSomeThread()) {
            LazyThread worker = new LazyThread();
            worker.start();
            lazyThreads[threadsCount++] = worker;
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isWorking = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        isWorking = false;
        for (int i = 0; i < threadsCount; i++) {
            lazyThreads[i].interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsCount;
    }

    private class LazyThread extends Thread {
        private boolean available = false;

        @Override
        public void run() {
            while (isWorking || !tasks.isEmpty()) {
                try {
                    Runnable task = tasks.take();
                    available = false;
                    task.run();
                    available = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            super.run();
        }

        public boolean isAvailable() {
            return available;
        }
    }

    private boolean isAvailableSomeThread() {
        for (int i = 0; i < threadsCount; i++) {
            if (lazyThreads[i].isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
