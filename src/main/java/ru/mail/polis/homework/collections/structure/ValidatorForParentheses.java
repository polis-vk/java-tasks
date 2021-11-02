package ru.mail.polis.homework.collections.structure;

import java.util.Map;
import java.util.Stack;

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

    private static final Map<Character, Character> mapOfBrackets = Map.of(
            ')', '(',
            ']', '[',
            '}', '{',
            '>', '<');

    public static boolean validate(String value) {
        if (value == null || value.equals("") || !containsBrackets(value)) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        char currentSymbol;
        for (int i = 0; i < value.length(); i++) {
            currentSymbol = value.charAt(i);
            if (mapOfBrackets.containsValue(currentSymbol)) {
                stack.push(currentSymbol);
            } else if (mapOfBrackets.containsKey(currentSymbol) && (stack.size() == 0
                    || !mapOfBrackets.get(currentSymbol).equals(stack.pop()))) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    private static boolean containsBrackets(String value) {
        for (int i = 0; i < value.length(); i++) {
            char currentChar = value.charAt(i);
            if (mapOfBrackets.containsKey(currentChar) || mapOfBrackets.containsValue(currentChar)) {
                return true;
            }
        }
        return false;
    }
}
