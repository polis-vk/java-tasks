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
 *
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 *
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final AtomicInteger threadsCount = new AtomicInteger(0); // просто ++ работает не так удобно
    private final int maxThreadsCount;
    private final CustomThread[] threads;
    private int freeThreadsCount = 0;
    private boolean inWork = true;

    public SimpleExecutor(int maxThreadCount) {
        threads = new CustomThread[maxThreadCount];
        this.maxThreadsCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (!inWork) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);
        synchronized (threadsCount) {
            if (threadsCount.get() < maxThreadsCount && freeThreadsCount == 0 && !tasks.isEmpty()) {
                CustomThread singleThread = new CustomThread();
                singleThread.start();
                threads[threadsCount.getAndIncrement()] = singleThread;
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        inWork = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        inWork = false;
        for (int i = 0; i < threadsCount.get(); i++) {
            threads[i].interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsCount.get();
    }

    private class CustomThread extends Thread {
        @Override
        public void run() {
            try {
                while (inWork) {
                    freeThreadsCount++;
                    Runnable runnable = tasks.take();
                    freeThreadsCount--;
                    runnable.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}