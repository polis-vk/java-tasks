package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * Задачи должны выполняться в порядке FIFO
 *
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {

    private final BlockingQueue<Runnable> workQueue;
    private Thread workerThread;
    private volatile boolean isShutdown;

    public SingleExecutor() {
        workQueue = new LinkedBlockingQueue<>();
        workerThread = new Worker();
        workerThread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown) {
            throw new RejectedExecutionException();
        }
        workQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        workerThread.interrupt();
        isShutdown = true;
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while ((!isShutdown || !workQueue.isEmpty()) && !isInterrupted()) {
                try {
                    workQueue.take().run();
                } catch (InterruptedException e) {
                    workerThread = null;
                    return;
                }
            }
            workerThread = null;
        }
    }
}
