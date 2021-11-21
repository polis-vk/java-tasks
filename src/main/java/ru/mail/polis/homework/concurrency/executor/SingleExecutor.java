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
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private boolean isActive = true;
    private final Thread worker = new Thread(this::run);

    public SingleExecutor() {
        worker.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (!isActive()) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);
    }


    private void run() {
        while (isActive()) {
            try {
                tasks.take().run();
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        setActive(false);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        worker.interrupt();
    }

    public synchronized boolean isActive() {
        return isActive;
    }

    public synchronized void setActive(boolean active) {
        isActive = active;
    }
}
