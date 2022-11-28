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
    private final BlockingQueue<Runnable> tasksQueue;
    private final Thread thread;
    private volatile boolean running;

    public SingleExecutor() {
        tasksQueue = new LinkedBlockingQueue<>();
        thread = new SingleThread();
        thread.start();
        running = true;
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (!running) {
            throw new RejectedExecutionException();
        }
        tasksQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        running = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        running = false;
        thread.interrupt();
    }

    private class SingleThread extends Thread {
        @Override
        public void run() {
            try {
                while (running || !tasksQueue.isEmpty()) {
                    Runnable task = tasksQueue.take();
                    task.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
