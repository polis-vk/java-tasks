package ru.mail.polis.homework.functions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleFunction {

    /**
     * Функция от трех аргументов. Не забудьте добавить дженерики.
     * Функция должна походить на {@link java.util.function.BiFunction}
     * 1 балл
     */

    @FunctionalInterface
    interface TerFunction<T, S, U, R> {
        R apply(T t, S s, U u);

        default <V> TerFunction<T, S, U, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (T t, S s, U u) -> after.apply(apply(t, s, u));
        }
    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static <T, S, U, R> Function<T, Function<S, Function<U, R>>> curring(TerFunction<T, S, U, R> terFunction) {
        return t -> s -> u -> terFunction.apply(t, s, u);
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            operators -> numbers -> numbers.stream()
                    .flatMap(number -> IntStream.range(1, operators.size() + 1)
                            .mapToObj(index -> operators.stream()
                                    .limit(index)
                                    .reduce(operator -> operator, IntUnaryOperator::andThen))
                            .map(operator -> operator.applyAsInt(number)))
                    .collect(Collectors.toList());

    /**
     * Написать функцию, которая принимает начальное значение и преобразователь двух чисел в одно, возвращает функцию,
     * которая на заданном интервале (входящие аргументы результирующей функции) считает преобразование всех целых чисел
     * на заданном интервале.
     * <p>
     * Пример хотим просуммировать числа от 2 до 10:
     * reduceIntOperator.apply(начальное значение, (x,y) -> ...).apply(2, 10) = 54
     * 3 балла
     */
    public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator =
            (initValue, function) -> (intervalStart, intervalEnd) -> IntStream.rangeClosed(intervalStart, intervalEnd).reduce(initValue, function);

    public static void main(String[] args) {
        multifunctionalMapper.apply(Arrays.asList(x -> x, x -> x + 1, x -> x * x)).apply(Arrays.asList(1, 2)).forEach(System.out::println);
    }
}
