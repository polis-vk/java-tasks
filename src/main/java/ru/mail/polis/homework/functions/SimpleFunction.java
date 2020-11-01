package ru.mail.polis.homework.functions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    interface TerFunction<T, U, V, R> {
        R apply(T arg1, U arg2, V arg3);

        default <K> TerFunction<T, U, V, K> andThen(Function<? super R, ? extends K> after) {
            return (t, u, v) -> after.apply(this.apply(t, u, v));
        }

    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static <T, U, V, R> Function<T, Function<U, Function<V, R>>> curring(TerFunction<T, U, V, R> terFunction) {
        return  (T var1) -> (U var2) -> (V var3) -> terFunction.apply(var1, var2, var3);
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            (operatorList) -> (intList) -> {
                List<Integer> result = new ArrayList<>();
                for (Integer number : intList) {
                    for (IntUnaryOperator operator : operatorList) {
                        number = operator.applyAsInt(number);
                        result.add(number);
                    }
                }
                return result;
            };

    /**
     * Написать функцию, которая принимает начальное значение и преобразователь двух чисел в одно, возвращает функцию,
     * которая на заданном интервале (входящие аргументы результирующей функции) считает преобразование всех целых чисел
     * на заданном интервале.
     *
     * Пример хотим просуммировать числа от 2 до 10:
     * reduceIntOperator.apply(начальное значение, (x,y) -> ...).apply(2, 10) = 54
     * 3 балла
     */
    public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator = (start, function) ->
            (a, b) -> Stream.iterate(a, x -> x + 1)
                    .limit(b - a + 1)
                    .reduce(start, function::applyAsInt);
}
