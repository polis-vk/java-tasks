package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {

    private final List<Thread> threadList = new CopyOnWriteArrayList<>();
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final AtomicReference<StatesContainer> statesContainer = new AtomicReference<>(new StatesContainer(1, 0,
            0));
    private final int maxThreadCount;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (statesContainer.get().isActive.get() == 0) {
            throw new RejectedExecutionException();
        }
        int oldVal1 = statesContainer.get().freeThreads.get();
        int oldVal2 = statesContainer.get().isActive.get();
        if (statesContainer.get().holdOn.get() == 0 && statesContainer.get().freeThreads.get() == 0 && threadList.size() < maxThreadCount && statesContainer.get().isActive.get() == 1) {
            statesContainer.set(new StatesContainer(oldVal2, oldVal1, 1));
            Thread thread = new CustomThread();
            threadList.add(thread);
            thread.start();

        }
        oldVal1 = statesContainer.get().freeThreads.get();
        oldVal2 = statesContainer.get().isActive.get();
        statesContainer.set(new StatesContainer(oldVal2, oldVal1, 0));
        queue.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        statesContainer.set(new StatesContainer(0, statesContainer.get().freeThreads.get(),
                statesContainer.get().holdOn.get()));
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        threadList.forEach(Thread::interrupt);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadList.size();
    }

    private class CustomThread extends Thread {
        @Override
        public void run() {
            int oldVal = statesContainer.get().freeThreads.get();
            statesContainer.set(new StatesContainer(statesContainer.get().isActive.get(), ++oldVal,
                    statesContainer.get().holdOn.get()));
            while (!Thread.currentThread().isInterrupted() && (!queue.isEmpty() || statesContainer.get().isActive.get() == 1)) {
                try {
                    Runnable deal = queue.take();
                    oldVal = statesContainer.get().freeThreads.get();
                    statesContainer.set(new StatesContainer(statesContainer.get().isActive.get(), --oldVal,
                            statesContainer.get().holdOn.get()));
                    deal.run();
                    oldVal = statesContainer.get().freeThreads.get();
                    statesContainer.set(new StatesContainer(statesContainer.get().isActive.get(), ++oldVal,
                            statesContainer.get().holdOn.get()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class StatesContainer {
        public StatesContainer(int isActive, int freeThreads, int holdOn) {
            this.isActive = new AtomicInteger(isActive);
            this.freeThreads = new AtomicInteger(freeThreads);
            this.holdOn = new AtomicInteger(holdOn);
        }

        private final AtomicInteger isActive;
        private final AtomicInteger freeThreads;
        private final AtomicInteger holdOn;
    }
}
