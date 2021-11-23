package ru.mail.polis.homework.concurrency.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

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
    private final Thread[] workers;
    private final BlockingQueue<Runnable> commands;
    private int startedThreadCount = 0;
    private boolean finished = false;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        workers = new Thread[maxThreadCount];
        commands = new LinkedBlockingQueue<>();
    }

    /**
     * Ставит задачу в очередь на исполнение, если надо -- создает новый поток.
     * 8 баллов
     */
    @Override
    public void execute(Runnable command) {
        if (finished) {
            throw new RejectedExecutionException();
        }
        createThreadIfBusy();
        commands.add(command);
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        finished = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        finished = true;
        for (int i = 0; i < startedThreadCount; i++) {
            workers[i].interrupt();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return startedThreadCount;
    }

    private Thread createThread() {
        return new Thread(() -> {
            while (true) {
                try {
                    commands.take().run();
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
    }

    private synchronized void createThreadIfBusy() {
        if (startedThreadCount == 0 || (isAllWorkersBusy() && startedThreadCount < maxThreadCount)) {
            workers[startedThreadCount] = createThread();
            workers[startedThreadCount].start();
            startedThreadCount++;
        }
    }

    private boolean isAllWorkersBusy() {
        for (int i = 0; i < startedThreadCount; i++) {
            if (workers[i].getState() == Thread.State.WAITING) {
                return false;
            }
        }
        return true;
    }
}
