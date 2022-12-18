package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Нужно сделать свой executor с ленивой инициализацией потоков до какого-то заданного предела.
 * Ленивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 *
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 *
 * Max 10 тугриков
 */
public class SimpleExecutor implements Executor {

    private final BlockingQueue<Runnable> commands = new LinkedBlockingDeque<>();
    private final int maxThreadCount;
    private final List<Thread> threads = new CopyOnWriteArrayList<>();
    private AtomicReference<State> state = new AtomicReference<State>(new State());


    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (!state.get().getRunning().get()) {
            throw new RejectedExecutionException();
        }

        if (state.get().getFreeThreadCount().get() == 0 && state.get().getThreadsSize().get() < maxThreadCount) {
            state.get().getThreadsSize().incrementAndGet();
            Thread thread = new SimpleThread();
            threads.add(thread);
            thread.start();
        }

        commands.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        state.get().getRunning().compareAndSet(true, false);
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        state.get().getRunning().compareAndSet(true, false);
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return state.get().getThreadsSize().get();
    }

    private class SimpleThread extends Thread {

        @Override
        public void run() {
            state.get().getFreeThreadCount().incrementAndGet();
            while (!Thread.currentThread().isInterrupted() &&
                    (!commands.isEmpty() || state.get().getRunning().get())) {
                try {
                    Runnable command = commands.take();
                    state.get().getFreeThreadCount().decrementAndGet();
                    command.run();
                    state.get().getFreeThreadCount().incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private class State {

        private final AtomicInteger freeThreadCount = new AtomicInteger();
        private final AtomicBoolean running = new AtomicBoolean(true);
        private final AtomicInteger threadsSize = new AtomicInteger();

        public AtomicBoolean getRunning() {
           return running;
        }

        public AtomicInteger getFreeThreadCount() {
            return freeThreadCount;
        }

        public AtomicInteger getThreadsSize() {
            return threadsSize;
        }

        public State() {

        }

    }
}
