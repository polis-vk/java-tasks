package ru.mail.polis.homework.collections.structure;

import java.util.LinkedList;
import java.util.Map;

/**
 * Задание оценивается в 2 балла.
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
    private static final Map<Character, Character> brackets = Map.of('(', ')', '{', '}', '[', ']');

    public static boolean validate(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < value.length(); i++) {
            final char charElement = value.charAt(i);
            if (brackets.containsKey(charElement)) {
                stack.add(charElement);
            } else if (brackets.containsValue(charElement)) {
                if (stack.isEmpty() || brackets.get(stack.getLast()) != charElement) {
                    return false;
                }
                stack.removeLast();
            }
        }
        return stack.isEmpty();
    }
}
