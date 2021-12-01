package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
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

    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private final List<Thread> threads = new ArrayList<>();

    private final AtomicInteger freeWorkers = new AtomicInteger();
    private final ReentrantLock lock = new ReentrantLock();

    private final int maxThreadCount;
    private volatile boolean shutdownCalled = false;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (shutdownCalled) {
            throw new RejectedExecutionException();
        }
        addCommand(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        shutdownCalled = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdownCalled = true;
        threads.forEach(Thread::interrupt);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }


    private void addCommand(Runnable command) {
        try {
            lock.lock();
            tryToAddThread();
        } finally {
            lock.unlock();
        }
        workQueue.add(command);
    }

    private void tryToAddThread() {
        if (!(threads.size() < maxThreadCount && freeWorkers.get() == 0)) {
            return;
        }

        Thread thread = new Thread(new Worker());
        thread.start();
        threads.add(thread);
    }

    class Worker implements Runnable {
        @Override
        public void run() {
            try {
                while (!shutdownCalled || !workQueue.isEmpty()) {
                    freeWorkers.incrementAndGet();
                    Runnable task = workQueue.take();
                    freeWorkers.decrementAndGet();
                    task.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
