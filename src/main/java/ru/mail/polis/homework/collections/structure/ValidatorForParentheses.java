package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Задание оценивается в 2 тугрика.
 * Одна из самых популярных задач.
 * Реализовать метод, который проверяет правильность написания скобок в строке.
 * В строке помимо скобок могут содержаться и другие символы.
 * Скобки могут быть: [],{},<>,()
 * Примеры:
 *      "(-b + (x)^2)/(2+4)" - true
 *      "Понедельники меня угнетают ((" - false
 *
 * Отрабатывать метод должен за О(n)
 */
public class ValidatorForParentheses {

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        Stack<Character> stackOfParentheses = new Stack<>();

        final List<Character> opening = Arrays.asList('(', '[', '<', '{');
        final List<Character> closing = Arrays.asList(')', ']', '>', '}');
        int modStackCount = 0;

        for (int i = 0; i < value.length(); i++) {
            char currentSymbol = value.charAt(i);
            if (opening.contains(currentSymbol)) {
                stackOfParentheses.add(currentSymbol);
                modStackCount++;
            } else if (closing.contains(currentSymbol)
                    && !stackOfParentheses.isEmpty()
                    && (currentSymbol - stackOfParentheses.peek() <= 2)) {
                stackOfParentheses.pop();
            }
        }

        return modStackCount != 0 && stackOfParentheses.isEmpty();
    }
}
