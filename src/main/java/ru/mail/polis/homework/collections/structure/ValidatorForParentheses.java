package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.HashMap;
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

    private static final Map<Character, Character> bracketsMap = new HashMap<Character, Character>() {{
        put(']', '[');
        put('}', '{');
        put('>', '<');
        put(')', '(');
    }};

    public static boolean validate(String value) {
        boolean hasBrackets = false;
        if (!checkStringForValid(value)) {
            return false;
        }

        Deque<Character> bracketsStack = new LinkedList<>();
        for (char c : value.toCharArray()) {
            if (bracketsMap.containsValue(c)) {
                bracketsStack.push(c);
                hasBrackets = true;
            } else if (bracketsMap.containsKey(c)) {
                if (bracketsStack.isEmpty() || bracketsStack.pop() != bracketsMap.get(c)) {
                    return false;
                }
            }
        }

        return bracketsStack.isEmpty() && hasBrackets;
    }

    private static boolean checkStringForValid(String value) {
        return value != null && !value.isEmpty();
    }
}
