package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;

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
        if (value == null) {
            return false;
        }

        Deque<Character> deq = new ArrayDeque<>();

        boolean wasAtLeastOnePair = false;
        for (char c : value.toCharArray()) {
            if (isOpeningBracket(c)) {
                deq.addLast(c);
            } else if (isClosingBracket(c)) {
                if (deq.peekLast() == null) {
                    return false;
                }

                if (!checkBracketsMatch(deq.peekLast(), c)) {
                    return false;
                }

                deq.pollLast();
                wasAtLeastOnePair = true;
            }
        }

        return deq.size() == 0 && wasAtLeastOnePair;
    }

    private static boolean isOpeningBracket(char c) {
        return c == '[' || c == '{' || c == '<' || c == '(';
    }

    private static boolean isClosingBracket(char c) {
        return c == ']' || c == '}' || c == '>' || c == ')';
    }

    private static boolean checkBracketsMatch(char b1, char b2) {
        return (b1 == '(' && b2 == ')') || (b1 == '[' && b2 == ']') ||
                (b1 == '<' && b2 == '>') || (b1 == '{' && b2 == '}');
    }
}