package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * Задачи должны выполняться в порядке FIFO
 *
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
    private final Thread thread = new Thread(new Worker());
    private final AtomicBoolean shutdownCalled = new AtomicBoolean();

    public SingleExecutor() {
        thread.start();
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

        workQueue.add(command);
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
        thread.interrupt();
    }


    class Worker implements Runnable {
        @Override
        public void run() {
            while (!shutdownCalled.get() || !workQueue.isEmpty()) {
                Runnable task = workQueue.poll();
                if (task != null) {
                    task.run();
                }
            }
        }
    }
}
