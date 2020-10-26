package ru.mail.polis.homework.functions;

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

    public static void main(String[] args) {
        System.out.println(multifunctionalMapper.apply(List.of(x -> x, x -> x + 1, x -> x * x)).apply(List.of(1, 2)));
    }

    /**
     * Функция от трех аргументов. Не забудьте добавить дженерики.
     * Функция должна походить на {@link java.util.function.BiFunction}
     * 1 балл
     */
    interface TerFunction<T, U, B, R> {

        R apply(T t, U u, B b);

    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static <T, U, B, R> Function<T, Function<U, Function<B, R>>> curring(TerFunction<T, U, B, R> terFunction) {
        return t -> u -> b -> terFunction.apply(t, u, b);
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            list -> numbers -> numbers.stream()
                    .flatMap(number ->
                            Stream.iterate(1, x -> x <= list.size(), x -> x += 1)
                                    .map(count -> list.stream()
                                            .limit(count)
                                            .reduce(x -> x, (op1, op2) -> x -> op2.applyAsInt(op1.applyAsInt(x)))
                                            .applyAsInt(number)))
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
            (seed, bi) -> (start, end) -> IntStream.iterate(start, x -> x <= end, x -> x += 1).reduce(seed, bi);

    public static final IntBinaryOperator sumOperator = reduceIntOperator.apply(0, (a, b) -> a * b);
}
