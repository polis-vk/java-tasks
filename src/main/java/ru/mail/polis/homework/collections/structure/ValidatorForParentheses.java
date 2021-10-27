package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Задание оценивается в 2 балла.
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

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Deque<Character> list = new LinkedList<>();
        for (int i = 0; i < value.length(); i++) {
            char temp = value.charAt(i);
            if (isOpenBrace(temp)) {
                list.push(temp);
            } else if (isCloseBrace(temp)) {
                Character last = list.poll();
                if (last == null) {
                    return false;
                }
                if (!isPairedBraces(last, temp) && !isPairedBraces(temp, last)) {
                    return false;
                }
            }
        }
        return list.isEmpty();
    }

    private static boolean isPairedBraces(char a, char b) {
        return a == '(' && b == ')' || a == '[' && b == ']' || a == '{' && b == '}' || a == '<' && b == '>';
    }

    private static boolean isCloseBrace(char c) {
        return c == ')' || c == ']' || c == '}' || c == '>';
    }

    private static boolean isOpenBrace(char c) {
        return c == '(' || c == '[' || c == '{' || c == '<';
    }

}
