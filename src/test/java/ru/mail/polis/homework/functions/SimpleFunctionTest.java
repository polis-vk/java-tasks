package ru.mail.polis.homework.functions;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;


public class SimpleFunctionTest {

    @Test
    public void testMultifunctionalMapper(){
        List<IntUnaryOperator> unary = Arrays.asList(x -> x, x -> x+1, x->x*x);
        List<Integer> counts = Arrays.asList(1,2);

        List<Integer> obtained = SimpleFunction.multifunctionalMapper.apply(unary).apply(counts);

        List<Integer> expected = Arrays.asList(1,2,4,2,3,9);
        Assert.assertArrayEquals(expected.toArray(), obtained.toArray());
    }
}
