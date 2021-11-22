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
 *
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 *
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {
    private final List<SimpleWorker> workers = new LinkedList<>();
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final int maxThreadCount;
    private final AtomicInteger freeWorkers;
    private boolean isActive = true;

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount <= 0) {
            throw new IllegalArgumentException();
        }
        freeWorkers = new AtomicInteger(0);
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new IllegalArgumentException();
        }
        if (!isActive()) {
            throw new RejectedExecutionException();
        }
        if (freeWorkers.get() != 0 || workers.size() == maxThreadCount) {
            tasks.add(command);
        } else {
            SimpleWorker worker = new SimpleWorker(command);
            workers.add(worker);
            worker.start();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        setIsActive(false);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        for (SimpleWorker worker : workers) {
            worker.interrupt();
        }
    }

    private synchronized void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    private synchronized boolean isActive() {
        return isActive;
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workers.size();
    }

    private class SimpleWorker extends Thread {
        private Runnable firstTask;

        public SimpleWorker(Runnable runnable) {
            firstTask = runnable;
        }

        @Override
        public void run() {
            firstTask.run();
            while (isActive() || !tasks.isEmpty()) {
                freeWorkers.incrementAndGet();
                try {
                    Runnable task = tasks.take();
                    freeWorkers.decrementAndGet();
                    task.run();
                } catch (InterruptedException e) {
                    break;
                }
            }
            firstTask = null;
        }
    }
}
