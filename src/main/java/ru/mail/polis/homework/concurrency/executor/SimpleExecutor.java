package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

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

    private final List<CustomThread> threads = new CopyOnWriteArrayList<>();
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final AtomicInteger available = new AtomicInteger(0);
    private final int MAX_COUNT;
    private boolean ending = true;

    public SimpleExecutor(int maxThreadCount) {
        MAX_COUNT = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public synchronized void execute(Runnable command) {
        if (command == null) {
            throw new NullPointerException();
        }

        if (ending) {
            queue.add(command);
            if (available.get() == threads.size() && threads.size() < MAX_COUNT) {
                threads.add(new CustomThread());
            }
        } else {
            throw new RejectedExecutionException();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        ending = false;
        for (CustomThread thread : threads) {
            thread.kill();
        }
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        ending = false;
        for (CustomThread thread : threads) {
            thread.kill();
            thread.interrupt();
        }

    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threads.size();
    }

    private class CustomThread extends Thread {

        private boolean alive = true;
        private boolean start = false;

        public CustomThread() {
            available.incrementAndGet();
            start();
        }

        @Override
        public void run() {
            Runnable check;

            while (alive) {
                if (!queue.isEmpty()) {
                    try {
                        check = queue.take();
                        if (start) {
                            available.incrementAndGet();
                        }

                        check.run();
                        available.decrementAndGet();
                        start = true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void kill() {
            alive = false;
        }

    }
}
