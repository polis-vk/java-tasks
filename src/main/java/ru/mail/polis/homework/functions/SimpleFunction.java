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
    interface TerFunction<T, U, Y, R> {

        R apply(T t, U u, Y y);

        default <V> TerFunction<T, U, Y, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (T t, U u, Y y) -> after.apply(apply(t, u, y));
        }
    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static <T, U, V, R> Function<T, Function<U, Function<V, R>>> curring(TerFunction<T, U, V, R> terFunction) {
        return t -> u -> v -> terFunction.apply(t, u, v);
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            list -> numbers -> numbers.stream()
                    .flatMap(number -> IntStream.rangeClosed(1, list.size() + 1)
                                    .mapToObj(count -> list.stream()
                                            .limit(count)
                                            .reduce(x -> x, IntUnaryOperator::andThen))
                                            .map(x -> x.applyAsInt(number)))
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
            (a, b) -> (start, end) -> IntStream.rangeClosed(start, end).reduce(a, b);
}
