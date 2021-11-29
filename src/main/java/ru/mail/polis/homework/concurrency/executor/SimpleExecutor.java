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
    private final int maxThreadCount;
    private final Worker[] workers;
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final AtomicInteger activeWorkers = new AtomicInteger(0);

    private volatile boolean inProcess = true;


    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        this.workers = new Worker[maxThreadCount];
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public synchronized void execute(Runnable command) {
        if (!inProcess) {
            throw new RejectedExecutionException();
        }
        if (activeWorkers.get() < maxThreadCount && !isAvailableWorker()) {
            Worker worker = new Worker();
            worker.start();
            workers[activeWorkers.getAndIncrement()] = worker;
        }
        try {
            tasks.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        inProcess = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        inProcess = false;
        for (int i = 0; i < activeWorkers.get(); i++) {
            workers[i].interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return activeWorkers.get();
    }

    private boolean isAvailableWorker() {
        for (int i = 0; i < activeWorkers.get(); i++) {
            if (workers[i].isAvailable()) {
                return true;
            }
        }
        return false;
    }

    private class Worker extends Thread {
        private volatile boolean available = false;

        @Override
        public void run() {
            try {
                while (inProcess || !tasks.isEmpty()) {
                    Runnable task = tasks.take();
                    available = false;
                    task.run();
                    available = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public boolean isAvailable() {
            return available;
        }
    }

}
