package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {

    private static final int RUNNING = 0;
    private static final int SHUTDOWN = 1;
    private static final int STOP = 2;
    LinkedBlockingQueue<Runnable> workQueue;
    private int currentState;
    private final Thread thread;

    public SingleExecutor() {
        workQueue = new LinkedBlockingQueue<>();
        currentState = RUNNING;
        thread = new Thread(new Worker());
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (currentState == RUNNING) {
            workQueue.offer(command);
        } else {
            throw new RejectedExecutionException();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        currentState = SHUTDOWN;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
        currentState = STOP;
    }

    private final class Worker implements Runnable {
        @Override
        public void run() {
            while (currentState == RUNNING || !thread.isInterrupted()) {
                Runnable nextTask = workQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }

}
