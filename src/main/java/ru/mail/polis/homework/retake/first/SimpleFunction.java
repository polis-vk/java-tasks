package ru.mail.polis.homework.retake.first;

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

/**
 * 22 балла
 */
public class SimpleFunction {

    /**
     * Опишите функцию от трех аргументов. Не забудьте добавить дженерики.
     * Функция должна походить на {@link BiFunction}
     * 2 балла
     */
    interface TerFunction<T, U, V, R> {
        R apply(T first, U second, V third);

        default <S> TerFunction<T, U, V, S> andThen(Function<? super R, ? extends S> after) {
            Objects.requireNonNull(after);
            return (first, second, third) -> {
                return after.apply(this.apply(first, second, third));
            };
        }
    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Каррирование это преобразование функции от многих аргументов в набор функций,
     * каждая из которых является функцией от одного аргумента.
     *
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static <T, U, V, R> curring(TerFunction<T, U, V, R> terFunction) {
        return arg1 -> arg2 -> arg3 -> terFunction.apply(arg1, arg2, arg3);
    }

    /**
     * Надо вернуть функцию, которая из строки делает квадратное уравнение от квадратного уравнения от g(str):
     * f(str) -> square(square(g(str)))
     * square(x) -> a * x * x + b * x + c
     * doubleStringEquation(1, 1, 1, 1, 0, 2, String::length).apply("cat") = (9 + 2) * (9 + 2) + (9 + 2) + 1 = 133
     * 6 балла
     */
    static Function<String, Double> doubleStringEquation(double a1, double b1, double c1,
                                                         double a2, double b2, double c2,
                                                         Function<String, Double> g) {
        return null;
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 10 балла
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            a -> ArrayList::new;
    public static final UnaryOperator<List<Integer>> allOp = multifunctionalMapper.apply(Arrays.asList(x -> x, x -> x + 1, x -> x * x));


    /**
     * Функция должна принимать начальное значение и преобразователь двух чисел в одно, а так же возвращать функцию,
     * которая на заданном интервале (входящие аргументы результирующей функции) считает преобразование всех целых чисел
     * на заданном интервале.
     *
     * Пример хотим просуммировать числа от 2 до 10:
     * reduceIntOperator.apply(начальное значение, (x,y) -> ...).apply(2, 10) = 54
     * 2 балла
     */
    public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator =
            (initial, v) -> (l, r) -> {
                int res = initial;
                for (int i = l; i <= r; i++) {
                    res = v.applyAsInt(res, i);
                }
                return res;
            };

    public static final IntBinaryOperator sum = reduceIntOperator.apply(0, Integer::sum);
}
