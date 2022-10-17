package ru.mail.polis.homework.collections.structure;

import jdk.internal.joptsimple.internal.Strings;

import java.util.Deque;
import java.util.LinkedList;

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
        Deque<Character> initialBrackets = new LinkedList<>();
        boolean hasAdded = false;
        for (int i = 0; i < value.length(); i++) {
            char symbol = value.charAt(i);
            switch (value.charAt(i)) {
                case '(':
                case '[':
                case '<':
                case '{':
                    initialBrackets.addLast(symbol);
                    if (!hasAdded) {
                        hasAdded = true;
                    }
                    break;
                case ')':
                    if (initialBrackets.isEmpty() || initialBrackets.pollLast() != '(') {
                        return false;
                    }
                    break;
                case ']':
                    if (initialBrackets.isEmpty() || initialBrackets.pollLast() != '[') {
                        return false;
                    }
                    break;
                case '>':
                    if (initialBrackets.isEmpty() || initialBrackets.pollLast() != '<') {
                        return false;
                    }
                    break;
                case '}':
                    if (initialBrackets.isEmpty() || initialBrackets.pollLast() != '{') {
                        return false;
                    }
                    break;
            }
        }
        return hasAdded && initialBrackets.isEmpty();
    }
}
