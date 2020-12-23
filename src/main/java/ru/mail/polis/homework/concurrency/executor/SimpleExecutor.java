package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Нужно сделать свой экзекьютор с линивой инициализацией потоков до какого-то заданного предела.
 * Линивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 * <p>
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 * <p>
 * Max 10 баллов
 * <p>
 * Напишите 3 теста (2 балла за тест)
 * 1) запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
 * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
 * примерно одновременно
 * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
 * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
 * Max 6 баллов
 */

//DOESN'T WORK YET
public class SimpleExecutor implements Executor {
    private static Object lock = new Object();

    private final int threadAmount;
    private final Queue<Runnable> tasks;
    private final List<SimpleWorker> workers;

    private static class SimpleWorker extends Thread {
        private final Queue<Runnable> tasks;
        private boolean works;

        public SimpleWorker(Queue<Runnable> tasks) {
            this.tasks = tasks;
            this.works = true;
        }

        @Override
        public void run() {
            while (true) {
                works = false;

                Runnable task = this.tasks.poll();
                works = true;
                task.run();
            }
        }

        public boolean isWorks() {
            return works;
        }
    }

    public SimpleExecutor(int amount) {
        this.threadAmount = amount;
        this.tasks = new LinkedBlockingQueue();
        this.workers = new ArrayList<>(amount);
        this.workers.add(new SimpleWorker(tasks));
    }

    @Override
    public void execute(Runnable command) {
        if (!haveFreeWorker() && workers.size() < threadAmount) {
            workers.add(new SimpleWorker(this.tasks));
        }

        tasks.add(command);
    }

    private boolean haveFreeWorker() {
        return this.workers.stream().anyMatch(w -> !w.isWorks());
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public long getLiveThreadsCount() {
        return this.workers.size();
    }
}
