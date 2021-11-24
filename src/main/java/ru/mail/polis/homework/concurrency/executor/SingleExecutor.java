package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {
    private final LinkedBlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private final AtomicBoolean shutdownCalled = new AtomicBoolean(false);
    private final SingleThread singleThread = new SingleThread();

    {
        singleThread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (shutdownCalled.get()) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        shutdownCalled.set(true);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        shutdownCalled.set(true);
        singleThread.interrupt();
    }

    private class SingleThread extends Thread {
        @Override
        public void run() {
            while (!shutdownCalled.get() || !tasks.isEmpty()) {
                try {
                    Runnable task = tasks.take();
                    task.run();
                } catch (InterruptedException e) {
                    System.out.println("I was interrupted");
                    return;
                }
            }
        }
    }

}
