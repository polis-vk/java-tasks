package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.BlockingQueue;
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
    private final BlockingQueue<Runnable> instructions = new LinkedBlockingQueue<>();
    private final AtomicInteger counterOfThreads = new AtomicInteger(0);
    private final AtomicInteger counterOfFreeThreads = new AtomicInteger(0);
    private final CustomThread[] arrayOfThreads;
    private final int counterOfMaxThreads;
    private volatile boolean isShutDown;

    public SimpleExecutor(int maxThreadCount) {
        arrayOfThreads = new CustomThread[maxThreadCount];
        this.counterOfMaxThreads = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (isShutDown) {
            throw new RejectedExecutionException();
        }
        instructions.add(command);

        synchronized (counterOfThreads) {
            if (!instructions.isEmpty() && counterOfFreeThreads.get() == 0 && counterOfThreads.get() < counterOfMaxThreads) {
                counterOfThreads.getAndIncrement();
                CustomThread singleThread = new CustomThread();
                singleThread.start();
                arrayOfThreads[counterOfThreads.get() - 1] = singleThread;
            }
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShutDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        isShutDown = true;
        for (int i = 0; i < counterOfThreads.get(); ++i) {
            arrayOfThreads[i].interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return counterOfThreads.get();
    }

    private class CustomThread extends Thread {
        @Override
        public void run() {
            Runnable thread;
            while (!isShutDown) {
                try {
                    counterOfFreeThreads.incrementAndGet();
                    thread = instructions.take();
                    counterOfFreeThreads.decrementAndGet();
                    thread.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
