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
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(5);
    private boolean isShutdown = false;
    private final Thread thread = new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (isShutdown) {
                    Runnable active;
                    if ((active = blockingQueue.poll()) == null) {
                        break;
                    }
                    active.run();
                } else {
                    blockingQueue.take().run();
                }
            } catch (InterruptedException ignored) {
            }
        }
    });

    public SingleExecutor() {
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown) {
            throw new RejectedExecutionException("Adding new tasks while running others");
        }
        if (command == null) {
            throw new IllegalArgumentException("Illegal null-command");
        }
        try {
            blockingQueue.put(command);
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        thread.interrupt();
    }

}
