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

    private final AtomicInteger freeThreads;
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final List<Worker> workers = new LinkedList<>();
    private final int maxThreadCount;
    private boolean isStop;

    public SimpleExecutor(int maxThreadCount) {
        freeThreads = new AtomicInteger();
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
        if ((freeThreads.get() != 0) || !addWorker(command)) {
            tasks.add(command);
        }
    }

    private boolean addWorker(Runnable command) {
        if (workers.size() == maxThreadCount) {
            return false;
        }
        Worker worker = new Worker(command);
        workers.add(worker);
        worker.thread.start();
        return true;
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
    public void shutdownNow() {
        isStop = true;
        for (Worker worker : workers) {
            worker.thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workers.size();
    }

    private class Worker implements Runnable {

        private final Thread thread;
        private final Runnable command;

        public Worker(Runnable command) {
            this.command = command;
            thread = new Thread(this, "Worker");
        }

        @Override
        public void run() {
            command.run();
            while (!isStop) {
                try {
                    freeThreads.incrementAndGet();
                    Runnable task = tasks.take();
                    freeThreads.decrementAndGet();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
