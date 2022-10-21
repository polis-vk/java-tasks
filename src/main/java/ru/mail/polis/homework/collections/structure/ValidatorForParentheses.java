package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;

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
    private static final Map<Character, Character> matchingBrackets =
            Map.of('}', '{', ']', '[', ')', '(', '>', '<');

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        boolean isContainingBracket = false;
        Deque<Character> deque = new LinkedList<>();
        for (char ch : value.toCharArray()) {
            if (isOpeningBracket(ch)) {
                deque.push(ch);
                isContainingBracket = true;
            } else if (isClosingBracket(ch) && (deque.isEmpty() || !deque.pop().equals(matchingBrackets.get(ch)))) {
                return false;
            }
        }
        return isContainingBracket && deque.isEmpty();
    }

    private static boolean isOpeningBracket(char bracket) {
        return matchingBrackets.containsValue(bracket);
    }

    private static boolean isClosingBracket(char bracket) {
        return matchingBrackets.containsKey(bracket);
    }
}
