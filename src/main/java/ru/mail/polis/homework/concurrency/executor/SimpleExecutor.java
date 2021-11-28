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
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {

    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final AtomicInteger workingOrWaitingThreadsCount = new AtomicInteger(0);
    private final AtomicInteger threadsCount = new AtomicInteger(0);
    private volatile boolean shutdown = false;
    private int maxThreadCount;
    private final List<Worker> threadPool = new ArrayList<>(maxThreadCount);


    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount < 1) {
            throw new IllegalArgumentException();
        }
        this.maxThreadCount = maxThreadCount;
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
        if (shutdown) {
            throw new RejectedExecutionException();
        }
        try {
            queue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (threadsCount.get() < maxThreadCount && (workingOrWaitingThreadsCount.get() != threadsCount.get() || threadPool.isEmpty())) {
            threadsCount.incrementAndGet();
            Worker newWorker = new Worker();
            newWorker.start();
            threadPool.add(newWorker);
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        shutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown = true;
        synchronized (threadPool) {
            for (Worker worker : threadPool) {
                worker.interrupt();
            }
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadsCount.get();
    }

    private class Worker extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    workingOrWaitingThreadsCount.incrementAndGet();
                    queue.take().run();
                    workingOrWaitingThreadsCount.decrementAndGet();
                }
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
