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
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {
    private final Worker worker = new Worker();
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private volatile boolean isStarted;

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
        if (!isStarted) {
            synchronized (this) {
                if (!isStarted) {
                    isStarted = true;
                    worker.start();
                }
            }
        }
        queue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        worker.shutdown();
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        if (worker.isWorking()) {
            worker.interrupt();
        }
        shutdown();
    }

    private class Worker extends Thread {
        private volatile boolean isWorking = true;

        @Override
        public void run() {
            while (isWorking || !queue.isEmpty()) {
                try {
                    queue.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
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
