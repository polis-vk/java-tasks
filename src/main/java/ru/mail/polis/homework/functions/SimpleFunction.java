package ru.mail.polis.homework.functions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

public class SimpleFunction {

    /**
     * Функция от трех аргументов. Не забудьте добавить дженерики.
     * Функция должна походить на {@link java.util.function.BiFunction}
     * 1 балл
     */
    interface TerFunction {

    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static Object curring(TerFunction terFunction) {
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
        return (String str) -> {
            double x = (a2 * Math.pow(g.apply(str), 2) + b2 * g.apply(str) + c2);
            return a1 * Math.pow(x, 2) + b1 * x + c1;
        };
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла (доп задание)
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            (unaryOperatorList) -> (integerList) -> {
                List<Integer> resultList = new ArrayList<>(unaryOperatorList.size() * integerList.size());
                for (int number : integerList) {
                    int current = number;
                    for (IntUnaryOperator operator : unaryOperatorList) {
                        current = operator.applyAsInt(current);
                        resultList.add(current);
                    }
                }
                return resultList;
            };


    /**
     * Написать функцию, которая принимает начальное значение и преобразователь двух чисел в одно, возвращает функцию,
     * которая на заданном интервале (входящие аргументы результирующей функции) считает преобразование всех целых чисел
     * на заданном интервале.
     * <p>
     * Пример хотим просуммировать числа от 2 до 10:
     * reduceIntOperator.apply(начальное значение, (x,y) -> ...).apply(2, 10) = 54
     * 2 балла
     */
    public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator =
            (start, biOperator) -> (left, right) -> {
                int result = start;
                for (int i = left; i <= right; ++i) {
                    result = biOperator.applyAsInt(result, i);
                }
                return result;
            };
}
