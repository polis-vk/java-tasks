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


    public static void main(String[] args) {
        System.out.println(reduceIntOperator.apply(2, Integer::sum));
    }
    /**
     * Функция от трех аргументов. Не забудьте добавить дженерики.
     * Функция должна походить на {@link java.util.function.BiFunction}
     * 1 балл
     */
    interface TerFunction<A, B, C, R> {
        R apply(A a, B b, C c);

        default <V> TerFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after) {
            Objects.requireNonNull(after);
            return (A a, B b, C c) -> after.apply(apply(a, b, c));
        }
    }

    /**
     * Реализуйте каррирование для функции от трех аргументов.
     * Вам нужно правильно определить тип возращаемого значения и реализовать метод.
     * Не забывайте использовать дженерики.
     * 2 балла
     */
    static <A, B, C, R> Function<A, Function<B, Function<C, R>>> curring(TerFunction<A, B, C, R> terFunction) {
        return a -> b -> c -> terFunction.apply(a, b, c);
    }


    /**
     * Превращает список унарных операторов в один унарный оператор для списка чисел. Получившийся оператор
     * берет каждый элемент из списка чисел и последовательно применяет все входящие операторы.
     * Пример: multifunctionalMapper.apply([x -> x, x -> x + 1, x -> x * x]).apply([1, 2]) = [1, 2, 4, 2, 3, 9]
     * 4 балла
     */
    public static final Function<List<IntUnaryOperator>, UnaryOperator<List<Integer>>> multifunctionalMapper =
            listOfOperators -> numbers -> numbers.stream()
                    .map(number -> IntStream.range(1, listOfOperators.size() + 1)
                            .mapToObj(countOfOperators -> listOfOperators.stream()
                                    .limit(countOfOperators)
                                    .reduce(operator -> operator, IntUnaryOperator::andThen))
                            .mapToInt(operator -> operator.applyAsInt(number))
                            .boxed()
                            .collect(Collectors.toList()))
                    .flatMap(List::stream)
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
    public static final BiFunction<Integer, IntBinaryOperator, IntBinaryOperator> reduceIntOperator = ((integer, intBinaryOperator) ->
            (start, end) -> IntStream.range(start, end).reduce(integer, intBinaryOperator));
}
//10