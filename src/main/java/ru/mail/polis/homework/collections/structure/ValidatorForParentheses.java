package ru.mail.polis.homework.collections.structure;

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
    private static final String OPEN_BRACKETS = "[({<";
    private static final String CLOSE_BRACKETS = "])}>";

    public static boolean validate(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (char c : value.toCharArray()) {
            if (OPEN_BRACKETS.indexOf(c) != -1) {
                stack.push(c);
            }
            else if (CLOSE_BRACKETS.indexOf(c) != -1) {
                if (isPair(stack.peek(), c)) {
                    stack.pop();
                }
            }
        }
        return stack.isEmpty();
    }

    private static boolean isPair(char a, char b) {
        int posA = OPEN_BRACKETS.indexOf(a);
        int posB = CLOSE_BRACKETS.indexOf(b);
        return posA != -1 && posA == posB;
    }
}
