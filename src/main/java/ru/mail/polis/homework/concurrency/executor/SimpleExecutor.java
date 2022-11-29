package ru.mail.polis.homework.concurrency.executor;

import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {

    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private final ReentrantReadWriteLock mainLock = new ReentrantReadWriteLock();
    private final HashSet<Worker> workers = new HashSet<>();
    private int maximumPoolSize;
    private volatile boolean acceptingNew = true;
    private static final int MAX_THREADS_AMOUNT = Integer.MAX_VALUE;

    public SimpleExecutor(int maxThreadCount) {
        maximumPoolSize = maximumPoolSize < 0 ? MAX_THREADS_AMOUNT : maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо — создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        if (!acceptingNew) {
            throw new RejectedExecutionException();
        }

        mainLock.writeLock().lock();
        try {
            if (amountOfNotTerminatedWorkers() < maximumPoolSize
                    && amountOfIdleWorkers() == 0) {
                final Worker newWorker = new Worker(command);
                workers.add(newWorker);
                newWorker.thread.start();
            } else {
                workQueue.add(command);
            }
        } finally {
            mainLock.writeLock().unlock();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        acceptingNew = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        acceptingNew = false;
        mainLock.readLock().lock();
        try {
            for (Worker worker : workers) {
                worker.thread.interrupt();
            }
        } finally {
            mainLock.readLock().unlock();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        mainLock.writeLock().lock();
        try {
            return amountOfNotTerminatedWorkers();
        } finally {
            mainLock.writeLock().unlock();
        }
    }

    // only after mainLock.writelock!
    private int amountOfNotTerminatedWorkers() {
        int answer = 0;
        for (Worker worker : workers) {
            if (!worker.thread.isInterrupted()) {
                answer++;
            }
        }
        return answer;
    }

    // only after mainLock.writelock!
    private int amountOfIdleWorkers() {
        int answer = 0;
        for (Worker worker : workers) {
            if (worker.task == null) {
                answer++;
            }
        }
        return answer;
    }

    // Should be executed only by Worker w
    final void runWorker(Worker worker) {
        Thread wt = Thread.currentThread();
        try {
            while ((acceptingNew || worker.task != null || (worker.task = workQueue.poll()) != null)
                    && (worker.task != null || (worker.task = getTask()) != null)) {
                try {
                    worker.task.run();
                } finally {
                    mainLock.readLock().lock();
                    try {
                        worker.task = null;
                    } finally {
                        mainLock.readLock().unlock();
                    }
                }
            }
            worker.thread.interrupt();
        } catch (InterruptedException e) {
            worker.thread.interrupt();
        }
    }

    // Here may be realization with workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS)
    private Runnable getTask() throws InterruptedException {
        return workQueue.take();
    }

    private final class Worker implements Runnable {
        final Thread thread;
        Runnable task;

        private Worker(Runnable r) {
            this.thread = Executors.defaultThreadFactory().newThread(this);
            task = r;
        }

        @Override
        public void run() {
            runWorker(this);
        }
    }
}