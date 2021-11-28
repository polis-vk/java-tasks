package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
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

    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private final Thread[] threads;

    private final AtomicInteger freeWorkers = new AtomicInteger();
    private final int maxThreadCount;
    private final AtomicInteger currentThreadCount = new AtomicInteger();
    private final AtomicBoolean shutdownCalled = new AtomicBoolean();

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        threads = new Thread[maxThreadCount];
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (shutdownCalled.get()) {
            throw new RejectedExecutionException();
        }
        addCommand(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        shutdownCalled.set(true);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdownCalled.set(true);
        for (int i = 0; i < currentThreadCount.get(); i++) {
            threads[i].interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return currentThreadCount.get();
    }


    private void addCommand(Runnable command) {
        workQueue.add(command);

        synchronized (currentThreadCount) {
            if (!(currentThreadCount.get() < maxThreadCount && !workQueue.isEmpty() && freeWorkers.get() == 0)) {
                return;
            }

            currentThreadCount.getAndIncrement();
        }

        Thread thread = new Thread(new Worker());
        thread.start();
        threads[currentThreadCount.get() - 1] = thread;
    }


    class Worker implements Runnable {

        @Override
        public void run() {
            try {
                while (!shutdownCalled.get() || !workQueue.isEmpty()) {
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
