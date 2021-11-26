package ru.mail.polis.homework.concurrency.executor;

import java.util.LinkedList;
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
    private final int maxThreadCount;
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final List<Thread> threads = new LinkedList<>();
    private final AtomicInteger countFreeThreads = new AtomicInteger();
    private volatile boolean shutdownFlag = false;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (shutdownFlag) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);
        synchronized (threads) {
            if (threads.size() < maxThreadCount && !tasks.isEmpty() && countFreeThreads.get() == 0) {
                WorkerThread worker = new WorkerThread();
                worker.start();
                threads.add(worker);
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        shutdownFlag = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdownFlag = true;
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

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            try {
                while (!shutdownFlag) {
                    countFreeThreads.incrementAndGet();
                    final Runnable task = tasks.take();
                    countFreeThreads.decrementAndGet();
                    task.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


