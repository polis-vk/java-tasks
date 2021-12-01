package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.*;
import java.util.concurrent.Executor;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * <p>
 * Задачи должны выполняться в порядке FIFO
 *
 * <p>
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private volatile boolean isStopped = false;
    private final Thread thread = new Thread("executor") {
        @Override
        public void run() {
            try {
                while (!queue.isEmpty() || isStopped) {
                    queue.take().run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public SingleExecutor() {
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isStopped) {
            throw new RejectedExecutionException();
        }
        try {
            queue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isStopped = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        isStopped = true;
        thread.interrupt();
    }
}