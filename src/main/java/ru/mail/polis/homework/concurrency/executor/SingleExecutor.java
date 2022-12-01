package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.Executor;

import static java.lang.ref.Reference.reachabilityFence;

/**
 * Нужно сделать свой executor с одним вечным потоком. Пока не вызовут shutdown или shutdownNow
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * <p>
 * Max 6 тугриков
 */
public class SingleExecutor implements Executor {
    private final si__leThreadExecutor e;

    SingleExecutor() {
        e = new si__leThreadExecutor(1);
    }

    /**
     * Метод ставит задачу в очередь на исполнение.
     * 3 тугрика за метод
     */
    @Override
    public void execute(Runnable command) {
        try {
            e.execute(command);
        } finally {
            reachabilityFence(this);
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        e.shutdown();
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 2 тугрика за метод
     */
    public void shutdownNow() {
        try {
            e.shutdownNow();
        } finally {
            reachabilityFence(this);
        }
    }

}
