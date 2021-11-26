package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
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
    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
    private final List<Worker> workers;
    private boolean isShuttingDown;
    private final AtomicInteger ctl = new AtomicInteger(0);

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        workers = new ArrayList<>(maxThreadCount);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public synchronized void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        if (isShuttingDown) {
            throw new RejectedExecutionException();
        }

        if (ctl.get() == 0 && getLiveThreadsCount() < maxThreadCount) {
            Worker worker = new Worker();
            worker.start();
            workers.add(worker);
        }
        workQueue.add(command);
    }

    public void interruptAllWorkers() {
        for (Worker worker : workers) {
            worker.interrupt();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public synchronized void shutdown() {
        isShuttingDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public synchronized void shutdownNow() {
        isShuttingDown = true;
        interruptAllWorkers();
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public synchronized int getLiveThreadsCount() {
        return workers.size();
    }

    class Worker extends Thread {

        @Override
        public void run() {
            while (!isShuttingDown || !workQueue.isEmpty()) {
                Runnable r;
                ctl.incrementAndGet();
                try {
                    r = workQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                ctl.decrementAndGet();
                r.run();
            }
        }
    }
}
