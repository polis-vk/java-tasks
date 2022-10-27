package ru.mail.polis.homework.collections.structure;

import java.lang.reflect.Array;
import java.util.*;

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
    private static final Set<Character> OPEN_PARENTHESES = new HashSet<>(Arrays.asList('[', '{', '<', '('));
    private static final Set<Character> CLOSE_PARENTHESES = new HashSet<>(Arrays.asList(']', '}', '>', ')'));

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        Stack<Character> parenthesesStack = new Stack<>();
        boolean hasParentheses = false;

        for (int i = 0; i < value.length(); i++) {
            char symbol = value.charAt(i);

            if (OPEN_PARENTHESES.contains(symbol)) {
                parenthesesStack.push(symbol);
                hasParentheses = true;
            } else if (CLOSE_PARENTHESES.contains(symbol)) {
                if (parenthesesStack.isEmpty()) {
                    return false;
                }

                int asciiDifference = symbol - parenthesesStack.pop();
                if (!(asciiDifference > 0 && asciiDifference < 3)) {
                    return false;
                }
            }
        }

        return parenthesesStack.isEmpty() && hasParentheses;
    }
}
