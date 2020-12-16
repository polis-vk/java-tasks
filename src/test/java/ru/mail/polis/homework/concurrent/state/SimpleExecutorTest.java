package ru.mail.polis.homework.concurrent.state;


import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;

public class SimpleExecutorTest {

    @Test
    public void TestOneTask() {

        SimpleExecutor simpleExecutor = new SimpleExecutor(4);
        Runnable run = () -> {
            System.out.println("Start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End");
        };
        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.execute(run);
        Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());


        simpleExecutor.execute(run);
        Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.shutDown();
    }

    @Test
    public void TestLimitCountOfTask() {
        SimpleExecutor simpleExecutor = new SimpleExecutor(4);
        Runnable run = () -> {
            System.out.println("Start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End");
        };
        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        Assert.assertEquals(4, simpleExecutor.getLiveThreadsCount());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(4, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(4, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.shutDown();
    }

    @Test
    public void TestUnLimitCountOfTask() {
        SimpleExecutor simpleExecutor = new SimpleExecutor(4);
        Runnable run = () -> {
            System.out.println("Start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End");
        };
        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        Assert.assertEquals(4, simpleExecutor.getLiveThreadsCount());
        try {
            Thread.sleep(6500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(4, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        simpleExecutor.execute(run);
        Assert.assertEquals(4, simpleExecutor.getLiveThreadsCount());
        try {
            Thread.sleep(6500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(4, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.shutDown();
    }
}
