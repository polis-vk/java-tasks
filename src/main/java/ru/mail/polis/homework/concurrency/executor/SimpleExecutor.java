package ru.mail.polis.homework.concurrency.executor;

import java.util.HashSet;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 *
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 *
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {

    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private final ReentrantReadWriteLock mainLock = new ReentrantReadWriteLock();
    private final HashSet<Worker> workers = new HashSet<>();
    private final int maximumPoolSize;
    private volatile boolean acceptingNew = true;

    public SimpleExecutor(int maxThreadCount) {
        maximumPoolSize = maxThreadCount;
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
        mainLock.readLock().lock();
        try {
            return workers.size();
        } finally {
            mainLock.readLock().unlock();
        }
    }

    // Should be executed only by Worker w
    final void runWorker(Worker w) {
        Thread wt = Thread.currentThread();
        Runnable task = null;
        try {
// если мы не принимает новые, а полл вернул null -> больше элементов не будет
// если мы принимаем новое, то встаем в ожидание пока появится таска или нас интераптнут
            while ((acceptingNew || (task = workQueue.poll()) != null)
                    && (task != null || (task = getTask()) != null)) {
                try {
                    w.state = Worker.RUNNING;
                    task.run();
                } finally {
                    w.state = Worker.IDLE;
                }
            }
            w.state = Worker.TERMINATED;
        } catch (InterruptedException e) {
            w.state = Worker.TERMINATED;
        }
    }

    // Here may be realization with workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS)
    private Runnable getTask() throws InterruptedException {
        return workQueue.take();
    }

    private final class Worker implements Runnable {
        static final int TERMINATED = -1;
        static final int IDLE = 0;
        static final int RUNNING = 1;
        final Thread thread;
        // Should be changed only by this worker
        int state;

        private Worker() {
            this.thread = Executors.defaultThreadFactory().newThread(this);
        }

        @Override
        public void run() {
            runWorker(this);
        }
    }
}