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
        return str -> square(a1, b1, c1).compose(square(a2, b2, c2).compose(g)).apply(str);
    }

    private static Function<Double, Double> square(double a, double b, double c) {
        return x -> a * x * x + b * x + c;
    }

    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла (доп задание)
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            (unaryOperatorsList) -> (listOfNumbers) -> {
                List<Integer> result = new ArrayList<>();
                for (int number : listOfNumbers) {
                    int currentNumber = number;
                    for (IntUnaryOperator operator : unaryOperatorsList) {
                        currentNumber = operator.applyAsInt(currentNumber);
                        result.add(currentNumber);
                    }
                }
                return result;
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
            (initialValue, intBinaryOperator) -> (left, right) -> {
                int result = initialValue;
                for (int i = left; i <= right; i++) {
                    result = intBinaryOperator.applyAsInt(result, i);
                }
                return result;
            };
}
