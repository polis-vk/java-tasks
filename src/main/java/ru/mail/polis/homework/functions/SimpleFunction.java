package ru.mail.polis.homework.functions;

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
    public interface TerFunction<F, S, T, R> {
        R apply(F f, S s, T t);

        default <V> TerFunction<F, S, T, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (F f, S s, T t) -> after.apply(apply(f, s, t));
        }
    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static <F, S, T, R> Function<F, Function<S, Function<T, R>>>
            curring(TerFunction<F, S, T, R> terFunction) {
        return f -> s -> t -> terFunction.apply(f, s, t);
    }

    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            operators -> numbers -> numbers.stream()
                    .map(num -> IntStream.rangeClosed(1, operators.size())
                            .mapToObj(opNum -> operators.stream()
                                    .limit(opNum)
                                    .reduce(IntUnaryOperator.identity(), IntUnaryOperator::andThen))
                            .mapToInt(op -> op.applyAsInt(num))
                            .boxed()
                            .collect(Collectors.toList()))
            .flatMap(list -> list.stream())
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
            (a, b) -> (first, last) -> IntStream.rangeClosed(first, last).reduce(a, b);
}
