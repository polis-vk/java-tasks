package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
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

    public static Map<Character, Character> BRACKETS;
    static {
        BRACKETS = new HashMap<>();
        BRACKETS.put('(', ')');
        BRACKETS.put('[', ']');
        BRACKETS.put('<', '>');
        BRACKETS.put('{', '}');
    };

    public static boolean validate(String value) {
        if (value == null) {
            return false;
        }
        boolean useStack = false;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < value.length(); i++) {
            if (BRACKETS.containsKey(value.charAt(i))) {
                stack.push(value.charAt(i));
                if (!useStack) {
                    useStack = true;
                }
            } else if (!stack.empty() && BRACKETS.get(stack.peek()) == value.charAt(i)) {
                stack.pop();
            }
            else if (BRACKETS.containsValue(value.charAt(i)) ) {
                return false;
            }
        }
        return (stack.empty() && useStack);
    }
}

