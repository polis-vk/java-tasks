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
    private final WorkerThread worker = new WorkerThread();
    private volatile boolean shutdownFlag = false;

    public SingleExecutor() {
        worker.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (shutdownFlag) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);
    }


    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        shutdownFlag = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        shutdownFlag = true;
        worker.interrupt();
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            try {
                while (!shutdownFlag || !tasks.isEmpty()) {
                    tasks.take().run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
