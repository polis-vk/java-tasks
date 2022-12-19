package ru.mail.polis.homework.concurrency.executor;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import sun.security.provider.NativePRNG;

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

    BlockingQueue<Runnable> commands = new LinkedBlockingQueue<>();
    List<Thread> threads = new CopyOnWriteArrayList<>();
    private final int maxThreadCount;

    AtomicReference<ThreadsState> state = new AtomicReference<>(new ThreadsState(0, 0, true));

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 тугриков
     */
    @Override
    public void execute(Runnable command) {
        if (command == null) {
            return;
        }

        if (!getIsActive()) {
            throw new RejectedExecutionException();
        }

        while (true) {
            ThreadsState tmpState = state.get();
            if (tmpState.isActive && tmpState.threadsSize < maxThreadCount && tmpState.freeThreads == 0) {
                if (state.compareAndSet(tmpState, new ThreadsState(tmpState.freeThreads + 1, tmpState.threadsSize + 1, true))) {
                    Thread t = new CustomThread();
                    threads.add(t);
                    t.start();
                    break;
                }
            } else {
                break;
            }
        }

        if (getIsActive()) {
            commands.add(command);
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdown() {
        while (true) {
            ThreadsState tmp = state.get();
            if (state.compareAndSet(tmp, new ThreadsState(tmp.freeThreads, tmp.threadsSize, false))) {
                return;
            }
        }
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 тугрик за метод
     */
    public void shutdownNow() {
        shutdown();
        threads.forEach(Thread::interrupt);
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return state.get().threadsSize;
    }

    private class CustomThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() && (!commands.isEmpty() || getIsActive())) {
                try {
                    Runnable command = commands.take();
                    decrementFreeThreads();
                    command.run();
                    incrementFreeThreads();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ThreadsState {
        private final int freeThreads;
        private final int threadsSize;
        private final boolean isActive;

        public ThreadsState(int freeThreads, int threadsSize, boolean isActive) {
            this.freeThreads = freeThreads;
            this.threadsSize = threadsSize;
            this.isActive = isActive;
        }
    }

    private boolean getIsActive() {
        return state.get().isActive;
    }

    private void decrementFreeThreads() {
        while (true) {
            ThreadsState tmpState = state.get();
            if (state.compareAndSet(tmpState, new ThreadsState(tmpState.freeThreads - 1, tmpState.threadsSize, tmpState.isActive))) {
                return;
            }
        }
    }

    private void incrementFreeThreads() {
        while (true) {
            ThreadsState tmpState = state.get();
            if (state.compareAndSet(tmpState, new ThreadsState(tmpState.freeThreads + 1, tmpState.threadsSize, tmpState.isActive))) {
                return;
            }
        }
    }
}

