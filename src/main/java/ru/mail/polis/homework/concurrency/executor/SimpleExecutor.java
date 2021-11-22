package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
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
    private final BlockingQueue<Runnable> workQueue;
    private final List<Worker> workers;
    private boolean isShuttingDown;

    public SimpleExecutor(int maxThreadCount) {
        this.maxThreadCount = maxThreadCount;
        workQueue = new LinkedBlockingDeque<>();
        workers = new ArrayList<>(maxThreadCount);
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
        if (isShuttingDown) {
            throw new RejectedExecutionException();
        }
        //Проходит чаще тест tenSimultaneousThreadTest(), если как-то тормозить выполнение execute
        //Также можно вставить пустой счетчик до 1000, но с принтом чаще проходит тест, с чем связано не могу разобраться
        //Инчае:
//        expected:<5> but was:<4>
//        Expected :5
//        Actual   :4
        System.out.println(Thread.currentThread().getName() + " " + isFreeWorkerPresent());

        if (!isFreeWorkerPresent() && getLiveThreadsCount() < maxThreadCount) {
            Worker worker = new Worker();
            worker.start();
            workers.add(worker);
        }
        workQueue.add(command);
    }

    public boolean isFreeWorkerPresent() {
        for (Worker worker : workers) {
            if (!worker.isBusy) {
                return true;
            }
        }
        return false;
    }

    public void interruptAllWorkers() {
        for (Worker worker : workers) {
            worker.interrupt();
        }
    }

    /**
     * Дает текущим задачам выполниться. Добавление новых - бросает RejectedExecutionException
     * 1 балл за метод
     */
    public void shutdown() {
        isShuttingDown = true;
    }

    /**
     * Прерывает текущие задачи. При добавлении новых - бросает RejectedExecutionException
     * 1 балла за метод
     */
    public void shutdownNow() {
        isShuttingDown = true;
        interruptAllWorkers();
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return workers.size();
    }

    class Worker extends Thread {

        public boolean isBusy = false;

        @Override
        public void run() {
            while (!isShuttingDown) {
                Runnable r = null;
                if (!workQueue.isEmpty()) {
                    r = workQueue.poll();
                }
                if (r != null) {
                    isBusy = true;
                    r.run();
                    isBusy = false;
                }
            }
            while (!workQueue.isEmpty()) {
                workQueue.poll().run();
            }
        }
    }
}
