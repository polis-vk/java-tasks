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

    private enum State {
        RUNNING, SHUTDOWN, STOP
    }

    private final LinkedBlockingQueue<Runnable> workQueue;
    private volatile State currentState;
    private final Thread thread;

    public SingleExecutor() {
        workQueue = new LinkedBlockingQueue<>();
        currentState = State.RUNNING;
        thread = new Thread(new Worker());
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        if (currentState == State.RUNNING) {
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
        currentState = State.SHUTDOWN;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        currentState = State.STOP;
        if (thread != null && !thread.isInterrupted()) {
            thread.interrupt();
        }
    }

    private final class Worker implements Runnable {
        @Override
        public void run() {
            while (currentState == State.RUNNING || !thread.isInterrupted()) {
                Runnable nextTask = workQueue.poll();
                if (nextTask != null) {
                    nextTask.run();
                }
            }
        }
    }

}
