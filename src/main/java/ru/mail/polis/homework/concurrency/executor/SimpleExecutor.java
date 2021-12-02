package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    private final AtomicInteger freeThreads = new AtomicInteger();
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final List<Worker> workers;
    private final int maxThreadCount;
    private volatile boolean isStop;

    public SimpleExecutor(int maxThreadCount) {
        workers = new ArrayList<>(maxThreadCount);
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (isStop) {
            throw new RejectedExecutionException();
        }
        addWorker();
        tasks.add(command);
    }

    private synchronized void addWorker() {
        if (!isStop && (freeThreads.get() == 0) && (workers.size() != maxThreadCount)) {
            Worker worker = new Worker();
            workers.add(worker);
            worker.start();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isStop = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public synchronized void shutdownNow() {
        isStop = true;
        for (Worker worker : workers) {
            worker.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workers.size();
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            freeThreads.incrementAndGet();
            try {
                while (!isStop) {
                    Runnable task = tasks.take();
                    freeThreads.decrementAndGet();
                    task.run();
                    freeThreads.incrementAndGet();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
