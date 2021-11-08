package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
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

    private static final HashMap<Character, Character> actions = new HashMap<>();
    private static final Set<Character> openParentheses = new HashSet<>();

    static {
        openParentheses.add('(');
        openParentheses.add('[');
        openParentheses.add('{');
        openParentheses.add('<');
        actions.put(')', '(');
        actions.put(']', '[');
        actions.put('}', '{');
        actions.put('>', '<');
    }

    public static boolean validate(String value) {
        Stack<Character> stack = new Stack<>();
        if ((value == null) || (value.length() < 2)) {
            return false;
        }
        boolean containsParentheses = false;
        for (int i = 0; i < value.length(); i++) {
            if (openParentheses.contains(value.charAt(i))) {
                stack.push(value.charAt(i));
                containsParentheses = true;
            }
            if (actions.containsKey(value.charAt(i))) {
                if (!stack.empty() && (stack.peek() == actions.get(value.charAt(i)))) {
                    stack.pop();
                } else {
                    break;
                }
            }
        }
        return stack.isEmpty() && containsParentheses;
    }
}
