package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {

    private final Thread singleThread;
    private final ConcurrentLinkedDeque<Runnable> commandDeque = new ConcurrentLinkedDeque<>();
    private volatile boolean isShutDown = false;

    public SingleExecutor() {
        this.singleThread = new Thread(() -> {
            while (true) {
                if (!commandDeque.isEmpty()) {
                    commandDeque.pollLast().run();
                }
            }
        });
        singleThread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isShutDown) {
            throw new RejectedExecutionException();
        }
        commandDeque.addFirst(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShutDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        isShutDown = true;
        singleThread.interrupt();
    }
}
