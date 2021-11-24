package ru.mail.polis.homework.concurrency.executor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
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

    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    private Thread thread = null;

    private boolean stop = false;

    private void run() {
        while (!stop || !tasks.isEmpty()) {
            if (!tasks.isEmpty()) {
                tasks.poll().run();
            }
        }
    }
    
    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 балла за метод
     */
    @Override
    public void execute(Runnable command) {
        if (stop) {
            throw new RejectedExecutionException();
        }
        tasks.add(command);

        // запуск
        if (this.thread == null) {
            this.thread = new Thread(this::run);
            thread.start();
        }
    }
        

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        stop = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 балла за метод
     */
    public void shutdownNow() {
        shutdown();
        thread.interrupt();
    }
}
