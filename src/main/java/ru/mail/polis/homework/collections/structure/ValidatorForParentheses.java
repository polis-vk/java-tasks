package ru.mail.polis.homework.collections.structure;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

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
    private static final Map<Character, Character> MATCHING_BRACKETS_MAP;
    private static final Set<Character> OPEN_BRACKETS_SET;
    private static final Set<Character> CLOSE_BRACKETS_SET;

    static {
        MATCHING_BRACKETS_MAP = new HashMap<>();
        MATCHING_BRACKETS_MAP.put(')', '(');
        MATCHING_BRACKETS_MAP.put(']', '[');
        MATCHING_BRACKETS_MAP.put('}', '{');
        MATCHING_BRACKETS_MAP.put('>', '<');

        OPEN_BRACKETS_SET = new HashSet<>(Arrays.asList('(', '[', '{', '<'));
        CLOSE_BRACKETS_SET = new HashSet<>(Arrays.asList(')', ']', '}', '>'));
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        boolean withBrackets = false;
        char[] bracketsArr = value.toCharArray();
        Deque<Character> deque = new LinkedList<>();

        for (char c : bracketsArr) {
            if (!(OPEN_BRACKETS_SET.contains(c) || CLOSE_BRACKETS_SET.contains(c))) {
                continue;
            }
            withBrackets = true;
            if (OPEN_BRACKETS_SET.contains(c)) {
                deque.addLast(c);
            } else if (deque.isEmpty() || MATCHING_BRACKETS_MAP.get(c) != deque.pollLast()) {
                return false;
            }
        }
        return deque.isEmpty() && withBrackets;
    }
}
