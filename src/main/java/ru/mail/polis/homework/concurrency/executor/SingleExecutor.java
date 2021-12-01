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
    private final Thread executorThread = new Thread(new Execution());
    private final BlockingQueue<Runnable> commands = new LinkedBlockingQueue<>();
    private volatile boolean running = true;

    public SingleExecutor() {
        executorThread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        synchronized (this) {
            if (!running) {
                throw new RejectedExecutionException();
            }
        }
        commands.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public synchronized void shutdown() {
        running = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        executorThread.interrupt();
    }

    private class Execution implements Runnable {
        @Override
        public void run() {
            try {
                while (running || !commands.isEmpty()) {
                    commands.take().run();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }
}
