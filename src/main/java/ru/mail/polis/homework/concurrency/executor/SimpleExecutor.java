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

    private final int maxThreadCount;
    private final List<CustomThread> readyThreads = new CopyOnWriteArrayList<>();
    private final AtomicInteger busyCountThreads = new AtomicInteger(0);
    private final BlockingQueue<Runnable> commands = new LinkedBlockingQueue<>();
    private volatile boolean addMode = true;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (addMode) {
            commands.add(command);
            synchronized (this) {
                if (readyThreads.size() == busyCountThreads.get() && readyThreads.size() < maxThreadCount) {
                    CustomThread newThread = new CustomThread();
                    readyThreads.add(newThread);
                    newThread.start();
                }
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
        addMode = false;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        addMode = false;
        for (CustomThread readyThread : readyThreads) {
            readyThread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return readyThreads.size();
    }

    private class CustomThread extends Thread {

        public CustomThread() {
            busyCountThreads.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                while (addMode || !commands.isEmpty()) {
                    commands.take().run();
                    busyCountThreads.decrementAndGet();
                }
            } catch (InterruptedException e) {
                System.out.println("shutdownNow");
            }
        }
    }
}
