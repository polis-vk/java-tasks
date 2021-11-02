package ru.mail.polis.homework.collections.structure;

import java.util.*;

import static java.util.Map.entry;

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

    private static final Map<Character, Character> PAIR_BRACKETS = Map.ofEntries(
            entry(')', '('),
            entry(']', '['),
            entry('>', '<'),
            entry('}', '{')
    );

    private static final Set<Character> SET_OF_OPEN_BRACKETS = Set.of('(', '[', '<', '{');

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        boolean hasBrackets = false;
        Deque<Character> stack = new ArrayDeque<>();
        Character chekBracket;
        for (char ch : value.toCharArray()) {
            if (PAIR_BRACKETS.containsKey(ch)) {
                hasBrackets = true;
                if (stack.size() == 0) {
                    return false;
                }
                chekBracket = stack.peek();
                if (chekBracket.equals(PAIR_BRACKETS.get(ch))) {
                    stack.pop();
                } else {
                    return false;
                }
            }
            if (SET_OF_OPEN_BRACKETS.contains(ch)) {
                stack.push(ch);
            }
        }
        return stack.size() == 0 && hasBrackets;
    }
}