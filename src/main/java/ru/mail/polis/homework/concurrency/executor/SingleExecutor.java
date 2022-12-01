package ru.mail.polis.homework.concurrency.executor;

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

    private final Thread thread;
    private final LinkedBlockingQueue<Runnable> runnableQueue;
    private volatile boolean stopped = false;

    public SingleExecutor() {
        runnableQueue = new LinkedBlockingQueue<>();
        thread = new CustomThread();
        thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (stopped) {
            throw new RejectedExecutionException();
        }
        runnableQueue.offer(command);

    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        stopped = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        stopped = true;
        thread.interrupt();
    }


    private class CustomThread extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted() && (!stopped || !runnableQueue.isEmpty())) {
                    SingleExecutor.this.runnableQueue.take().run();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

}
