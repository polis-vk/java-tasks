package ru.mail.polis.homework.functions;


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
    interface TerFunction<F, S, T, R> {
        R apply(F f, S s, T t);

        default <V> TerFunction<F, S, T, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (f, s, t) -> after.apply(this.apply(f, s, t));
        }
    }


    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */

    static <F, S, V, R> Function<F, Function<S, Function<V, R>>> curring(TerFunction<F, S, V, R> terFunction) {
        return f -> s -> v -> terFunction.apply(f, s, v);
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            list -> numbers -> numbers.stream()
                    .flatMap(number -> IntStream.rangeClosed(1, list.size())
                            .map(count -> list.stream()
                                    .limit(count)
                                    .reduce(x -> x, (op1, op2) -> x -> op2.applyAsInt(op1.applyAsInt(x)))
                                    .applyAsInt(number))
                            .boxed())
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
            (integer, intBinaryOperator) -> (start, end) -> IntStream
                    .range(start, end)
                    .reduce(integer, intBinaryOperator);
}
