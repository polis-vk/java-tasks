package ru.mail.polis.homework.concurrency.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Нужно сделать свой экзекьютор с линивой инициализацией потоков до какого-то заданного предела.
 * Линивая инициализация означает, что если вам приходит раз в 5 секунд задача, которую вы выполняете 2 секунды,
 * то вы создаете только один поток. Если приходит сразу 2 задачи - то два потока.  То есть, если приходит задача
 * и есть свободный запущенный поток - он берет задачу, если такого нет, то создается новый поток.
 *
 * Задачи должны выполняться в порядке FIFO
 * Потоки после завершения выполнения задачи НЕ умирают, а ждут.
 *
 * Max 10 баллов
 *
 * Напишите 3 теста (2 балла за тест)
 * 1) запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
 * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
 * примерно одновременно
 * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
 * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
 * Max 6 баллов
 */


public class SimpleExecutor implements Executor {

    static int maxCapacity;
    static LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>();
    static List<CustomThread> threadList = new ArrayList<>();
    static boolean isAlive = true;


    public SimpleExecutor(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public SimpleExecutor() {
        maxCapacity = 10;
    }


    @Override
    public void execute(Runnable command) {
        linkedBlockingQueue.offer(command);

        for (CustomThread threadInList : threadList) {
            if (threadInList.getState() == Thread.State.WAITING) {
                threadInList.customNotify();
                return;
            }
        }

        if (threadList.size() < maxCapacity) {
            threadList.add(new CustomThread());
            threadList.get(threadList.size() - 1).start();
        }
        else {
            while (true) {
                for (CustomThread temp : threadList) {
                    if (temp.getState() == Thread.State.WAITING) {
                        temp.customNotify();
                        return;
                    }
                }
            }
        }
    }

    public void shutDown() {
        isAlive = false;
        for (CustomThread thread : threadList) {
            thread.customNotify();
        }
    }

    /**
     * Должен возвращать количество созданных потоков.
     */
    public int getLiveThreadsCount() {
        return threadList.size();
    }

    class CustomThread extends Thread {

        @Override
        public void run() {
            synchronized (this) {
                while (isAlive) {
                    try {
                        Runnable runnable = linkedBlockingQueue.poll();
                        if (runnable != null) {
                            runnable.run();
                        }
                        if (runnable == null) {
                            wait();
                        }

                    } catch(Exception e){}
                }
            }
        }

        public void customNotify() {
            synchronized (this) {
                this.notify();
            }
        }
    }
}
