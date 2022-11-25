package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayDeque;
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

    private final LinkedBlockingQueue<Runnable> tasks;
    private boolean isStopped;
    private final Thread thread;

    public SingleExecutor() {
        this.tasks = new LinkedBlockingQueue<>();
        this.isStopped = false;
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted() && (!isStopped || !tasks.isEmpty())) {
                        if (!tasks.isEmpty()) {
                            tasks.take().run();
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
    }
    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isStopped) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isStopped = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        isStopped = true;
        thread.interrupt();
    }
}
