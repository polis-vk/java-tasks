package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {
    private final Deque<Runnable> commandsQueue = new ArrayDeque<>();
    private final Thread everlastingThread;
    private boolean isShuttingDown;

    public SingleExecutor() {
        everlastingThread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    if (!commandsQueue.isEmpty()) {
                        Thread currentCommand = new Thread(commandsQueue.pollFirst());
                        currentCommand.run();
                    }
                }
            }
        };
        everlastingThread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isShuttingDown) {
            throw new RejectedExecutionException();
        }
        commandsQueue.offerLast(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        isShuttingDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        shutdown();
        everlastingThread.interrupt();
    }
}
