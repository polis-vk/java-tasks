package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingDeque;
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

    private final BlockingDeque<Runnable> tasksQueue;
    private final BlockingDeque<Worker> workersQueue;
    private int maxThreadCount;
    private AtomicInteger waitingWorkersCount;
    private boolean isShutdown;

    public SimpleExecutor(int maxThreadCount) {
        tasksQueue = new LinkedBlockingDeque<>();
        workersQueue = new LinkedBlockingDeque<>();
        this.maxThreadCount = maxThreadCount;
        waitingWorkersCount = new AtomicInteger(0);
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new IllegalArgumentException("NULL command");
        }
        if (isShutdown) {
            throw new RejectedExecutionException();
        }
        synchronized (tasksQueue) {
            tasksQueue.offer(command);
            if (waitingWorkersCount.get() == 0 && workersQueue.size() < maxThreadCount) {
                Worker worker = new Worker();
                worker.start();
                workersQueue.offer(worker);
            }
        }

    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        isShutdown = true;
        for (Worker worker : workersQueue) {
            worker.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workersQueue.size();
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            waitingWorkersCount.incrementAndGet();
            try {
                while (!isShutdown || !tasksQueue.isEmpty() || !isInterrupted()) {
                    Runnable task = tasksQueue.take();
                    waitingWorkersCount.decrementAndGet();
                    task.run();
                    waitingWorkersCount.incrementAndGet();
                }
            } catch (InterruptedException e) {
                waitingWorkersCount.decrementAndGet();
            }
            waitingWorkersCount.decrementAndGet();
        }
    }
}
