package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

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

    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    private final int maxThreadCount;

    private volatile List<Thread> threads;

    private volatile boolean stop = false;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        this.threads = new CopyOnWriteArrayList();
    }

    public void run() {
        try {
            while (!stop || !tasks.isEmpty()) {
                tasks.take().run();
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (stop) {
            throw new RejectedExecutionException();
        }

        tasks.add(command);

        // double checked locking для оптимизации
        if (canCreateThread()) {
            synchronized (threads) {
                if (canCreateThread()) {
                    Thread t = new Thread(this::run);
                    threads.add(t);
                    t.start();
                }
            }
        }
    }
    
    private boolean canCreateThread() {
        return !stop && !tasks.isEmpty() && threads.size() < maxThreadCount
                && threads.stream().allMatch(thread -> thread.getState() != Thread.State.WAITING);
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
     * 1 балла за метод
     */
    
    public void shutdownNow() {
        stop = true;
        threads.forEach(Thread::interrupt);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }

}
