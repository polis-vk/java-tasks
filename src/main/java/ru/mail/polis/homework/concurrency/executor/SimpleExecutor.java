package ru.mail.polis.homework.concurrency.executor;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 *
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 *
 * Max 10 баллов
 */
public class SimpleExecutor implements Executor {
    private final List<Thread> threads = new LinkedList<>();
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final AtomicInteger freeThreadCount = new AtomicInteger(0);
    private final AtomicBoolean isWorking = new AtomicBoolean(true);
    private final int maxThreadCount;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (!isWorking.get()) {
            throw new RejectedExecutionException();
        }
        queue.add(command);
        if (threads.size() < maxThreadCount && freeThreadCount.get() == 0 && !queue.isEmpty()) {
            synchronized (this) {
                if (threads.size() < maxThreadCount && freeThreadCount.get() == 0 && !queue.isEmpty()) {
                    Thread thread = new Thread(this::run);
                    thread.start();
                    threads.add(thread);
                }
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isWorking.set(false);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        isWorking.set(false);
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }

    public void run() {
        while (isWorking.get()) {
            try {
                freeThreadCount.incrementAndGet();
                Runnable command = queue.take();
                freeThreadCount.decrementAndGet();
                command.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
