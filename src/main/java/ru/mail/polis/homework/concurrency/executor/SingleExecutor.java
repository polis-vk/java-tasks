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

    private final BlockingQueue<Runnable> taskQueue;
    private final SingleThread mainThread;
    private static volatile boolean isRunning;

    public SingleExecutor() {
        taskQueue = new LinkedBlockingQueue<>();
        mainThread = new SingleThread();
        mainThread.start();
        isRunning = true;
    }


    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }

        if (!isRunning) {
            throw new RejectedExecutionException();
        }

        try {
            taskQueue.put(command);
        } catch (InterruptedException exc) {
            throw new RuntimeException(exc);
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isRunning = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        mainThread.interrupt();
    }

    class SingleThread extends Thread {
        @Override
        public void run() {
            try {
                while (!this.isInterrupted() && (isRunning || !taskQueue.isEmpty())) {
                    taskQueue.take().run();
                }
            } catch (InterruptedException exc) {
                throw new RuntimeException(exc);
            }
        }
    }
}
