package ru.mail.polis.homework.concurrency.executor;

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

    private final BlockingQueue<Runnable> scheduler = new LinkedBlockingDeque<>();
    private final Thread[] workers;
    private boolean isShutdown = false;
    private final AtomicInteger activeWorkers = new AtomicInteger(0);
    private final AtomicInteger numberAliveWorkers = new AtomicInteger(0);
    private final int maxNumberWorkers;


    private class Worker extends Thread {
        public final int id;

        private Worker(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Runnable task = scheduler.take();
                    activeWorkers.incrementAndGet();
                    task.run();
                    activeWorkers.decrementAndGet();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public SimpleExecutor(int maxThreadCount) {
        this.maxNumberWorkers = maxThreadCount;
        this.workers = new Worker[maxThreadCount];
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown) {
            throw new RejectedExecutionException();
        }
        synchronized (activeWorkers) {
            if (numberAliveWorkers.get() == activeWorkers.get()) {
                if (numberAliveWorkers.get() < maxNumberWorkers) {
                    Worker w = new Worker(numberAliveWorkers.get());
                    w.start();
                    workers[w.id] = w;
                    numberAliveWorkers.incrementAndGet();
                }
            }

            try {
                scheduler.put(command);
            } catch (InterruptedException er) {
                er.printStackTrace();
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutdown = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        isShutdown = false;
        for (Thread thread : workers) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return numberAliveWorkers.get();
    }
}
