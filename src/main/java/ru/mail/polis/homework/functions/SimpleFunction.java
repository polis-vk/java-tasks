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
    interface TerFunction<F, S, T, R> {
        R apply(F first, S second, T third);

        default <V> TerFunction<F, S, T, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (F first, S second, T third) -> after.apply(apply(first, second, third));
        }
    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static <F, S, T, R> Function<F, Function<S, Function<T, R>>> curring(TerFunction<F, S, T, R> terFunction) {
        return first -> second -> third -> terFunction.apply(first, second, third);
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла
     */

    // Должно ли оно выводить все стадии выполнения в списке? (правильный ли пример?)
    // Данное решение формирует функцию, которая применяет все операции к каждому числу, и выводит список
    // multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [4, 9]
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            operators -> numbers -> numbers.stream()
                    .map(value -> operators.stream()
                            .reduce(operand -> operand, IntUnaryOperator::andThen)
                            .applyAsInt(value))
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
            (startValue, function) -> (start, end) -> IntStream.rangeClosed(start, end).reduce(startValue, function);
}
