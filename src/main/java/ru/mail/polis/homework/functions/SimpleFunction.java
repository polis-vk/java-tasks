package ru.mail.polis.homework.functions;

import java.util.*;
import java.util.function.*;

public class SimpleFunction {

    /**
     * Функция от трех аргументов. Не забудьте добавить дженерики.
     * Функция должна походить на {@link java.util.function.BiFunction}
     * 1 балл
     */
    @FunctionalInterface
    interface TerFunction<T, U, K, R> {
        R apply(T t, U u, K k);
        default <V> TerFunction<T, U, K, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (T t, U u, K k) -> after.apply(apply(t, u, k));
        }
    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static <T, U, K, R> Function<T, Function<U, Function<K, R>>> curring(TerFunction<T, U, K, R> terFunction) {
        return t -> u -> k -> terFunction.apply(t, u, k);
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            operators -> numbers -> {
                List<Integer> output = new ArrayList<>();
                for (Integer number : numbers) {
                    int res = number;
                    for (IntUnaryOperator operator : operators) {
                        res = operator.applyAsInt(res);
                        output.add(res);
                    }
                }
                return output;
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
    public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator =
            (a, b) -> (c, d) -> {
                int res = a;
                for (int i = c; i <= d; i++) {
                    res = b.applyAsInt(res, i);
                }
                return res;
            };

    public static void main(String[] args) {
        TerFunction<Integer, Integer, Integer, Integer> func = (t, u, k) -> t + u + k;
        System.out.println(func.apply(1, 2 , 3));
        System.out.println(multifunctionalMapper.apply(Arrays.asList(x -> x, x -> x + 1, x -> x * x)).apply(Arrays.asList(1, 2)));
        System.out.println(reduceIntOperator.apply(0, (x, y) -> x + y).applyAsInt(2, 10));

    }
}
