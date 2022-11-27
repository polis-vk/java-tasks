package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.*;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {
    private BlockingQueue<Runnable> tasks;
    private final Thread worker;
    private volatile boolean isShutdownThreads;

    public SingleExecutor() {
        tasks = new LinkedBlockingQueue<>();
        isShutdownThreads = false;

        this.worker = new Thread(() -> {
            while (!isShutdownThreads || !tasks.isEmpty()) {
                try {
                    tasks.take().run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        worker.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            return;
        }

        if (isShutdownThreads) {
            throw new RejectedExecutionException();
        }

        tasks.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutdownThreads = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        isShutdownThreads = true;
        worker.interrupt();
    }
}
