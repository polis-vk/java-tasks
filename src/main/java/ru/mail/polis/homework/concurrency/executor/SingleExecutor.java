package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

import org.graalvm.compiler.hotspot.replacements.Log;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {

    private final Thread worker = new InnerWorker();
    private final BlockingQueue<Runnable> scheduler = new LinkedBlockingQueue<>();
    private volatile boolean isShutdown = false;

    public SingleExecutor() {
        worker.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (isShutdown) {
            throw new RejectedExecutionException();
        }
        try {
            scheduler.put(command);
        } catch (InterruptedException e) {
            Log.println(Thread.currentThread().getName() + " is dead");
            e.printStackTrace();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutdown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        isShutdown = true;
        worker.interrupt();
    }

    private class InnerWorker extends Thread {
        @Override
        public void run() {
            while (!isShutdown || !scheduler.isEmpty()) {
                try {
                    scheduler.take().run();
                } catch (InterruptedException ignored) {
                }
            }
        }
    }
}