package ru.mail.polis.homework.collections.structure;

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

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');
        pairs.put('>', '<');

        Deque<Character> brackets = new ArrayDeque<>();
        boolean flag = false;
        for (char ch : value.toCharArray()) {
            switch (ch) {
                case '(':
                case '[':
                case '{':
                case '<':
                    brackets.push(ch);
                    flag = true;
                    break;
                case ')':
                case ']':
                case '}':
                case '>':
                    if (brackets.isEmpty()) {
                        return false;
                    }
                    if (!brackets.pop().equals(pairs.get(ch))) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return flag && brackets.isEmpty();
    }
}
