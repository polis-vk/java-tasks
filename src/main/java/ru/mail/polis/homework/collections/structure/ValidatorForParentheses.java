package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

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

    private static final Map<Character, Character> BRACKETS = new HashMap<Character, Character>();
    
    static {
        BRACKETS.put(')', '(');
        BRACKETS.put(']', '[');
        BRACKETS.put('}', '{');
        BRACKETS.put('>', '<');
    }
    
    public static boolean validate(String value) {
        if (value == null || value.length() < 2) {
            return false;
        }

        boolean valid = false;
        Deque<Character> stack = new ArrayDeque<Character>();

        for (int i = 0; i < value.length(); ++i) {
            final char ch = value.charAt(i);

            if (BRACKETS.containsKey(ch)) {
                valid = true;
                if (stack.size() != 0) {
                    if (stack.pop() == BRACKETS.get(ch)) {
                        continue;
                    }
                }
                return false;
            }

            if (BRACKETS.containsValue(ch)) {
                stack.push(ch);
            }

        }
        return valid && stack.isEmpty();
    }
}
