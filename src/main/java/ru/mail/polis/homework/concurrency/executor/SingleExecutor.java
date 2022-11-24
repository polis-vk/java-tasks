package ru.mail.polis.homework.concurrency.executor;

import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {
    private final Worker worker;
    private final Queue<Runnable> queue = new LinkedBlockingQueue<>();

    public SingleExecutor() {
        this.worker = new Worker();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        if (!worker.isWorking()) {
            throw new RejectedExecutionException();
        }
        if (command == null) {
            throw new NullPointerException();
        }
        if (!worker.isAlive()) {
            worker.start();
        }
        queue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        if (worker.isWorking()) {
            worker.shutdown();
        }
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        if (worker.isWorking()) {
            worker.shutdown();
            worker.interrupt();
        }
    }

    private class Worker extends Thread {
        private boolean isWorking = true;

        @Override
        public void run() {
            while (isWorking || !queue.isEmpty()) {
                if (!queue.isEmpty()) {
                    if (isInterrupted()) {
                        return;
                    }
                    queue.poll().run();
                }
            }
        }

        public void shutdown() {
            isWorking = false;
        }

        private boolean isWorking() {
            return isWorking;
        }
    }
}
