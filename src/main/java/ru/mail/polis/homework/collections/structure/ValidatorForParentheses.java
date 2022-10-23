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
        if (value == null || value.length() == 0) {
            return false;
        }
        Deque<Character> brackets = new ArrayDeque<>();
        int bracketCount = 0;
        for (char literal : value.toCharArray()) {
            switch (literal) {
                case '[':
                case '(':
                case '<':
                case '{':
                    brackets.add(literal);
                    break;
                case ')':
                    if (brackets.isEmpty() || brackets.pollLast() != literal - 1) {
                        return false;
                    }
                    bracketCount++;
                    break;
                case ']':
                case '>':
                case '}':
                    if (brackets.isEmpty() || brackets.pollLast() != literal - 2) {
                        return false;
                    }
                    bracketCount++;
                    break;
            }
        }
        return brackets.isEmpty() && bracketCount != 0;
    }
}
