package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {

    private final BlockingQueue<Runnable> queueOfTasks;
    private final List<Worker> poolOfThreads;
    private final AtomicBoolean canAdd = new AtomicBoolean(true);
    private final int maxCountOfThreads;

    public SimpleExecutor(int maxCountThreads) {
        if (maxCountThreads < 1) {
            throw new IllegalArgumentException();
        }
        this.maxCountOfThreads = maxCountThreads;
        this.queueOfTasks = new ArrayBlockingQueue<>(maxCountThreads);
        poolOfThreads = new ArrayList<>();
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (!canAdd.get()) {
            throw new RejectedExecutionException();
        }

        try {
            queueOfTasks.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (poolOfThreads.size() < maxCountOfThreads && !queueOfTasks.isEmpty()) {
            poolOfThreads.add(new Worker());
            poolOfThreads.get(poolOfThreads.size() - 1).start();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        canAdd.set(false);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        canAdd.set(false);
        poolOfThreads.forEach(Thread::interrupt);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return poolOfThreads.size();
    }

    private class Worker extends Thread {
        @Override
        public void run() {
            while (canAdd.get()) {
                try {
                    queueOfTasks.take().run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
