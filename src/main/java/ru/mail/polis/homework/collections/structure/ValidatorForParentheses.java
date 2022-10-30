package ru.mail.polis.homework.collections.structure;

import java.util.*;

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
    private static Map<Character, Character> pairOfBrackets = new HashMap<>();
    private static Set<Character> openBrackets = new HashSet<>();

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        return validator(value);
    }

    private static boolean validator(String string) {
        pairOfBrackets.put('(', ')');
        pairOfBrackets.put('{', '}');
        pairOfBrackets.put('[', ']');
        pairOfBrackets.put('<', '>');
        openBrackets.add('(');
        openBrackets.add('{');
        openBrackets.add('[');
        openBrackets.add('<');
        Stack<Character> stack = new Stack<>();
        boolean isBrackets = false;
        for (Character character : string.toCharArray()) {
            if (pairOfBrackets.containsKey(character)) {
                stack.push(character);
                isBrackets = true;
            } else if (!stack.isEmpty() && character == pairOfBrackets.get(stack.peek())) {
                stack.pop();
            }
        }
        return stack.isEmpty() && isBrackets;
    }
}
