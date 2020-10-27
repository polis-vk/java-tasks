package ru.mail.polis.homework.functions;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimpleFunction {

    /**
     * Функция от трех аргументов. Не забудьте добавить дженерики.
     * Функция должна походить на {@link java.util.function.BiFunction}
     * 1 балл
     */
    @FunctionalInterface
    interface TerFunction<T, U, F, R> {
        R apply(T t, U u, F f);
    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static <T, U, F, R> Function<T, Function<U, Function<F, R>>> curring(TerFunction<T, U, F, R> terFunction) {
        return a -> b -> c -> terFunction.apply(a,b,c);
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            intUnaryOperators -> integers -> integers.stream()
                                //.peek(System.out::println)
                                .map(integer -> IntStream.rangeClosed(1, intUnaryOperators.size())
                                                        .mapToObj(value -> intUnaryOperators.stream()
                                                                .limit(value)
                                                                //.peek(System.out::println)
                                                                .reduce(operator -> operator, IntUnaryOperator::andThen))
                                                                .map(intUnaryOperator -> intUnaryOperator.applyAsInt(integer))
                                                        //.peek(System.out::println)
                                                        .collect(Collectors.toList()))
                                                        //.peek(integers1 -> System.out.println(Arrays.toString(integers1.toArray())))
                                .flatMap(Collection::stream)
                                //.peek(System.out::println)
                                .collect(Collectors.toList());



    /**
     * Написать функцию, которая принимает начальное значение и преобразователь двух чисел в одно, возвращает функцию,
     * которая на заданном интервале (входящие аргументы результирующей функции) считает преобразование всех целых чисел
     * на заданном интервале.
     *
     * Пример хотим просуммировать числа от 2 до 10:
     * reduceIntOperator.apply(начальное значение, (x,y) -> ...).apply(2, 10) = 54
     * 3 балла
     */
    public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator =
            (a, b) -> (left, right) -> IntStream.rangeClosed(left,right).reduce(a, b);
}
