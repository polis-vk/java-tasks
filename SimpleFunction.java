package ru.mail.polis.homework.functions;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleFunction {

    /**
     * Функция от трех аргументов. Не забудьте добавить дженерики.
     * Функция должна походить на {@link java.util.function.BiFunction}
     * 1 балл
     */
    @FunctionalInterface
    interface TerFunction<U, E, T, R> {
        R apply(U var1, E var2, T var3);

        default <V> TerFunction<U, E, T, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (var1, var2, var3) -> {
                return after.apply(this.apply(var1, var2, var3));
            };
        }

        /**
         * Реализуйте каррирование для функции от трех аргументов.
         * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
         * Не забывайте использовать дженерики.
         * 2 балла
         */
        static <U, E, T, R> Function<U, Function<E, Function<T, R>>> curring(TerFunction<U, E, T, R> terFunction) {
            return u -> e -> t -> terFunction.apply(u, e, t);
        }


        /**
         * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
         * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
         * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
         * 4 балла
         */
        public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
                opers -> numbers -> numbers.stream()
                        .map(x -> opers.stream()
                                        .reduce(current -> current, IntUnaryOperator::andThen)
                                .applyAsInt(x))
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
        public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator = (start, operator) -> (first, last) ->
                IntStream.rangeClosed(first, last)
                .reduce(start, operator);
    }

;

}
