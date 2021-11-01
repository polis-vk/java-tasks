package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.LinkedList;

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
    static {
        actions.put(')', '(');
        actions.put(']', '[');
        actions.put('}', '{');
        actions.put('>', '<');
    }

    public static boolean validate(String value) {
        LinkedList<Character> stack = new LinkedList<>();
        if ((value == null) || (value.length() < 2)) {
            return false;
        }
        boolean containsParentheses = false;
        for (int i = 0; i < value.length(); i++) {
            if (actions.containsValue(value.charAt(i))) {
                stack.addLast(value.charAt(i));
                containsParentheses = true;
            }
            if (actions.containsKey(value.charAt(i))) {
                if (stack.peekLast() == actions.get(value.charAt(i))) {
                    stack.removeLast();
                } else {
                    break;
                }
            }
        }
        return stack.isEmpty() && containsParentheses;
    }
}
