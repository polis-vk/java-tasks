package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
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
    private final BlockingQueue<Runnable> commandQueue = new LinkedBlockingQueue<>();
    private volatile boolean shutdown = false;
    private final Thread thread = new SingleThread();
    private boolean threadRun = false;

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (shutdown) {
            throw new RejectedExecutionException();
        }
        commandQueue.add(command);
        if (!thread.isAlive() && !threadRun) {
            thread.start();
            threadRun = true;
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        shutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        shutdown = true;
        thread.interrupt();
    }

    private class SingleThread extends Thread {
        @Override
        public void run() {
            while (!shutdown || !commandQueue.isEmpty()) {
                Runnable command;
                try {
                    command = commandQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                command.run();
            }
        }
    }
}
