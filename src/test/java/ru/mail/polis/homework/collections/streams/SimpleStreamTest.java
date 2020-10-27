package ru.mail.polis.homework.collections.streams;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.function.DoubleUnaryOperator;

public class SimpleStreamTest {

    @Test
    public void isPrimeTest_Get1_ReturnFalse(){
        boolean obtained = SimpleStreams.isPrime(1);

        Assert.assertFalse(obtained);
    }

    @Test
    public void isPrimeTest_ReturnTrue(){
        boolean obtained = SimpleStreams.isPrime(17);

        Assert.assertTrue(obtained);
    }

    @Test
    public void isPrimeTest_ReturnFalse(){
        boolean obtained = SimpleStreams.isPrime(25);

        Assert.assertFalse(obtained);
    }

    @Test
    public void createBadWordsDetectingStreamTest_EasyLvl(){
        String text = "Hello, my name is Saveliy. And I like my name.";
        List<String> badWords = Arrays.asList("my", "name", "is", "straus");

        Map<String, Long> obtained = SimpleStreams.createBadWordsDetectingStream(text, badWords);

        Map<String, Long> expected = new HashMap<>(4);
        expected.put("my", 2L);
        expected.put("name", 2L);
        expected.put("is", 1L);
        expected.put("straus", 0L);
        Assert.assertEquals(obtained, expected);
    }

    @Test
    public void calcDistanceTest(){
        DoubleUnaryOperator velocity = n -> {
            if(n == 1){
                return 2;
            }
            return  2.0/n;
        };

        double obtained = SimpleStreams.calcDistance(0, velocity, 45.0*Math.PI/180, 2);

        Assert.assertEquals(0.51, obtained, 0.001);
    }
}
