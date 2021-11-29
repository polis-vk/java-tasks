package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
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

    private final LinkedBlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    private final AtomicInteger freeThreads = new AtomicInteger();

    private final int maxThreadCount;

    private volatile List<Thread> threads;

    private volatile boolean stop = false;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        this.threads = Collections.synchronizedList(new ArrayList<Thread>(maxThreadCount));
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

        // double checked locking для оптимизации
        if (canCreateThread()) {
            synchronized (threads) {
                if (canCreateThread()) {
                    Thread t = new LiveThread(command);
                    threads.add(t);
                    t.start();
                    return;
                }
            }
        }
        tasks.offer(command);
    }

    private boolean canCreateThread() {
        return !stop && threads.size() < maxThreadCount && freeThreads.get() == 0;
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
        shutdown();
        synchronized (threads) {
            threads.forEach(Thread::interrupt);
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }
    
    private class LiveThread extends Thread {

        private Runnable target;

        public LiveThread(Runnable target) {
            this.target = target;
        }

        @Override
        public void run() {
            try {
                while (!stop || !tasks.isEmpty()) {
                    target.run();

                    freeThreads.incrementAndGet();
                    target = tasks.take();
                    freeThreads.decrementAndGet();
                }
            } catch (InterruptedException e) {
                return;
            }
        }

    }
}
