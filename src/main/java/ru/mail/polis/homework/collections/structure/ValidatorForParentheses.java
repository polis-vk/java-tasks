package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;

/**
 * Задание оценивается в 2 балла.
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

        ArrayDeque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < value.length(); ++i) {
            if (isOpenBracket(value.charAt(i))) {
                stack.push(value.charAt(i));
            } else if (isCloseBracket(value.charAt(i))) {
                if (!isPairedBrackets(stack.pop(), value.charAt(i))) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    private static boolean isOpenBracket(char ch) {
        return ch == '(' || ch == '[' || ch == '{' || ch == '<';
    }

    private static boolean isCloseBracket(char ch) {
        return ch == ')' || ch == ']' || ch == '}' || ch == '>';
    }

    private static boolean isPairedBrackets(char ch1, char ch2) {
        return (ch1 == '(' && ch2 == ')') || (ch1 == '[' && ch2 == ']')
                || (ch1 == '{' && ch2 == '}') || (ch1 == '<' && ch2 == '>');
    }

}
