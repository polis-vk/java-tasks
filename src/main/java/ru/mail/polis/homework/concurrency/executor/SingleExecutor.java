package ru.mail.polis.homework.concurrency.executor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 *
 * Задачи должны выполняться в порядке FIFO
 *
 * Max 6 баллов
 */
public class SingleExecutor implements Executor {
    private final List<Runnable> queue = new LinkedList<>();
    private final Thread thread = new Thread(this::run);
    private boolean isWorking = true;

    public SingleExecutor() {
        this.thread.start();
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (!isWorking) {
            throw new RejectedExecutionException();
        }
        queue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isWorking = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        thread.interrupt();
        isWorking = false;
    }

    private void run() {
        while (isWorking) {
            while (!queue.isEmpty()) {
                queue.remove(0).run();
            }
        }
    }
}
