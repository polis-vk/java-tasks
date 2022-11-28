package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * Задачи должны выполняться в порядке FIFO
 *
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {

    private final LinkedBlockingQueue<Runnable> tasks;
    private final AtomicBoolean isStopped;
    private final Thread thread;

    public SingleExecutor() {
        this.tasks = new LinkedBlockingQueue<>();
        this.isStopped = new AtomicBoolean(false);
        this.thread = new Thread(() -> {
            try {
                while (!isStopped.get() || !tasks.isEmpty()) {
                    tasks.take().run();
                }
            } catch (InterruptedException ignored) {

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
        if (isStopped.get()) {
            throw new RejectedExecutionException();
        }
        if (command != null) {
            tasks.add(command);
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isStopped.set(true);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        isStopped.set(true);
        thread.interrupt();
    }

}
