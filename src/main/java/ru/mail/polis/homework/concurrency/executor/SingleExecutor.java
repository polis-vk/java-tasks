package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * Задачи должны выполняться в порядке FIFO
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {

    private final BlockingQueue<Runnable> tasksQueue;
    private final Thread singleThread;
    private volatile boolean isAlive;

    public SingleExecutor() {
        tasksQueue = new LinkedBlockingDeque<>();
        singleThread = new Thread(() -> {
            try {
                while (isAlive || !tasksQueue.isEmpty()) {
                    tasksQueue.take().run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        isAlive = true;
        singleThread.start();
    }


    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (!isAlive) {
            throw new RejectedExecutionException();
        }
        tasksQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isAlive = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        isAlive = false;
        singleThread.interrupt();
    }
}
