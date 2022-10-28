package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Character, Character> bracketsMap = new HashMap<>();

    static {
        bracketsMap.put(']', '[');
        bracketsMap.put('}', '{');
        bracketsMap.put('>', '<');
        bracketsMap.put(')', '(');
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        ArrayList<Character> stack = null;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            if (bracketsMap.containsValue(ch)) {
                if (stack == null) {
                    stack = new ArrayList<>();
                }
                stack.add(ch);
            } else if (bracketsMap.containsKey(ch)) {
                if (stack == null || stack.isEmpty()) {
                    return false;
                }
                char bracket = stack.remove(stack.size() - 1);
                if (bracket != bracketsMap.get(ch)) {
                    return false;
                }
            }
        }
        return stack != null && stack.isEmpty();
    }
}
