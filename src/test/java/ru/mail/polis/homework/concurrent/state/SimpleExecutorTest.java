package ru.mail.polis.homework.concurrent.state;

import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;

public class SimpleExecutorTest {
    private SimpleExecutor simpleExecutorl;

    public SimpleExecutorTest() {
        simpleExecutorl = new SimpleExecutor(5);
    }

    @Test
    public void executorExecutingOnlyOneTask() {
        for (int i = 0; i < 5; i++) {
            int finalI = i;

            Runnable kek = () -> {
                System.out.println("Kek " + finalI);
            };

            simpleExecutorl.execute(kek);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Assert.assertEquals(0, simpleExecutorl.getLiveThreadsCount());
        }
    }

    @Test
    public void executorShould() {
        try {
            for (int i = 0; i < 5; i++) {
                int finalI = i;
                Runnable kek = () -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Kek type1 Try: " + finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };

                Runnable kek2 = () -> {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Kek type2 Try: " + finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
                simpleExecutorl.execute(kek);
                simpleExecutorl.execute(kek);
                simpleExecutorl.execute(kek);
                simpleExecutorl.execute(kek);
                simpleExecutorl.execute(kek);

                simpleExecutorl.execute(kek2);
                simpleExecutorl.execute(kek2);
                simpleExecutorl.execute(kek2);
                simpleExecutorl.execute(kek2);

                Assert.assertEquals(5, simpleExecutorl.getLiveThreadsCount());

                System.out.println(simpleExecutorl.getLiveThreadsCount());
                Thread.sleep(1500);

//                Assert.assertNotEquals(0, simpleExecutorl.getLiveThreadsCount());
                Thread.sleep(2100);
                System.out.println("-----------");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
