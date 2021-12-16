package ru.mail.polis.homework.concurrency.nio;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class ClientTest {

    @Test
    public void testBasicFunctionality() {
        int[] serverPorts = new int[]{8081, 8082};

        System.out.println("Starting server...");
        Server server = new Server(serverPorts, 3);

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Starting client...");
        Client client = new Client(serverPorts, 8070, 3, 1);


        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Result result = client.calculate(Arrays.asList(
                new Operand(10, OperandType.PLUS),
                new Operand(20, OperandType.PLUS),
                new Operand(2, OperandType.EQUALS)
        ));

        double resultValue = 0;
        try {
            resultValue = result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Got result: " + resultValue);
        Assert.assertEquals(resultValue, 32, 0.001);
    }
}