package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {

    private final BlockingDeque<Runnable> queue;
    private final Thread thread;
    private final AtomicInteger threadStatus;

    public SingleExecutor() {
        queue = new LinkedBlockingDeque<>();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (threadStatus.get() == 1 || !queue.isEmpty()) {
                        queue.takeFirst().run();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        threadStatus = new AtomicInteger(1);
    }


    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (threadStatus.get() == 0) {
            throw new RejectedExecutionException();
        }
        queue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        threadStatus.set(0);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        shutdown();
        thread.interrupt();
    }
}
