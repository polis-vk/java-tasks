package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
    private boolean isShuttingDown;
    private final Worker worker = new Worker();

    public SingleExecutor() {
        worker.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public synchronized void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }
        if (isShuttingDown) {
            throw new RejectedExecutionException();
        }

        workQueue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public synchronized void shutdown() {
        isShuttingDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public synchronized void shutdownNow() {
        isShuttingDown = true;
        worker.interrupt();
    }

    class Worker extends Thread {

        @Override
        public void run() {
            while (!isShuttingDown || !workQueue.isEmpty()) {
                Runnable r;
                try {
                    r = workQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                r.run();
            }
        }
    }
}
