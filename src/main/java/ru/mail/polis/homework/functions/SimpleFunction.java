package ru.mail.polis.homework.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;

public class SimpleFunction {

    /**
     * Функция от трех аргументов. Не забудьте добавить дженерики.
     * Функция должна походить на {@link java.util.function.BiFunction}
     * 1 балл
     */
    interface TerFunction<T, U, O, R> {
        R apply(T var1, U var2, O var3);
    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */

    static <T, U, O, R> BiFunction<U, O, Function<T, R>> curring(TerFunction terFunction) {
        //return (t) -> (u, o) -> terFunction.apply(t, u, o);   :((((( Как?
        return null;
    }

    /**
     * Надо вернуть функцию, которая из строки делает квадратное уравнение от квадратного уравнения от g(str):
     * f(str) -> square(square(g(str)))
     * square(x) -> a * x * x + b * x + c
     * doubleStringEquation(1, 1, 1, 1, 0, 2, String::length).apply("cat") = (9 + 2) * (9 + 2) + (9 + 2) + 1 = 133
     * 2 балла
     */
    static Function<String, Double> doubleStringEquation(double a1, double b1, double c1,
                                                         double a2, double b2, double c2,
                                                         Function<String, Double> g) {
        TerFunction<Double, Double, Double, Function<Double, Double>> square =
                (a, b, c) -> (x) -> (a * x * x + b * x + c);
        return str -> square.apply(a1, b1, c1).apply(square.apply(a2, b2, c2).apply(g.apply(str)));
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла (доп задание)
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            (listIntUnaryOperator) -> (listInteger) -> {
                List<Integer> result = new ArrayList<>(listIntUnaryOperator.size() * listInteger.size());
                for (Integer elem : listInteger) {
                    for (IntUnaryOperator unaryOperator : listIntUnaryOperator) {
                        result.add(unaryOperator.applyAsInt(elem));
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
     * 2 балла
     */
    public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator =
            (initialValue, binaryOperator) -> (leftBound, rightBound) -> {
                int result = initialValue;
                for (int elem = leftBound; elem < rightBound; elem++) {
                    result = binaryOperator.applyAsInt(result, elem);
                }
                return result;
            };
}
