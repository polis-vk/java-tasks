package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Задание оценивается в 2 тугрика.
 * Одна из самых популярных задач.
 * Реализовать метод, который проверяет правильность написания скобок в строке.
 * В строке помимо скобок могут содержаться и другие символы.
 * Скобки могут быть: [],{},<>,()
 * Примеры:
 * "(-b + (x)^2)/(2+4)" - true
 * "Понедельники меня угнетают ((" - false
 * <p>
 * Отрабатывать метод должен за О(n)
 */
public class ValidatorForParentheses {
    private static final String brackets = "{}[]()<>";

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        boolean isContainingBracket = false;
        Deque<Character> deque = new LinkedList<>();
        for (char ch : value.toCharArray()) {
            if (brackets.indexOf(ch) != -1) {
                if (isOpeningBracket(ch)) {
                    deque.push(ch);
                    isContainingBracket = true;
                } else if (deque.isEmpty() || !isMatchingBrackets(deque.pop(), ch)) {
                    return false;
                }
            }
        }
        return isContainingBracket && deque.isEmpty();
    }

    public static boolean isOpeningBracket(char ch) {
        return "{[(<".indexOf(ch) != -1;
    }

    public static boolean isMatchingBrackets(char openingBracket, char closingBracket) {
        return '(' == openingBracket && ')' == closingBracket
                || '[' == openingBracket && ']' == closingBracket
                || '{' == openingBracket && '}' == closingBracket
                || '<' == openingBracket && '>' == closingBracket;
    }
}
