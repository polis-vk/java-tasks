package ru.mail.polis.homework.concurrency.executor;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
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
    private final Queue<Thread> workers = new ConcurrentLinkedQueue<>();
    private final BlockingQueue<Runnable> commands = new LinkedBlockingQueue<>();
    private final int maxThreadCount;
    private final AtomicInteger freeThreadCount = new AtomicInteger(0);
    private volatile boolean running = true;

    public SimpleExecutor(int maxThreadCount) {
        if (maxThreadCount < 1) {
            throw new IllegalArgumentException();
        }
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (!running) {
            throw new RejectedExecutionException();
        }
        try {
            commands.put(command);
        } catch (InterruptedException ignored) {
        }
        if (workers.size() < maxThreadCount && freeThreadCount.get() == 0) {
            synchronized (this) {
                if (workers.size() < maxThreadCount && freeThreadCount.get() == 0) {
                    Thread worker = new Thread(new Execution());
                    worker.start();
                    workers.add(worker);
                }
            }
        }
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
     * 1 балла за метод
     */
    public synchronized void shutdownNow() {
        shutdown();
        workers.forEach(Thread::interrupt);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workers.size();
    }

    private class Execution implements Runnable {
        @Override
        public void run() {
            try {
                while (running || !commands.isEmpty()) {
                    freeThreadCount.incrementAndGet();
                    Runnable command = commands.take();
                    freeThreadCount.decrementAndGet();
                    command.run();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

}
