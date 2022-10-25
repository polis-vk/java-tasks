package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;

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

    private static boolean assertBrackets(char a, char b) {
        return a == '[' && b == ']' || a == '{' && b == '}' || a == '<' && b == '>' || a == '(' && b == ')';
    }

    private static boolean isBracket(char a) {
        return a == '[' || a == ']' || a == '{' || a == '}' || a == '<' || a == '>' || a == '(' || a == ')';
    }

    public static boolean validate(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        ArrayDeque<Character> deque = new ArrayDeque<>();
        boolean wasBracketFlag = false;
        for (int i = 0; i < value.length(); i++) {
            char character = value.charAt(i);
            if (isBracket(character)) {
                if (!deque.isEmpty() && assertBrackets(deque.peekFirst(), character)) {
                    deque.pollFirst();
                } else {
                    deque.addFirst(character);
                }
                wasBracketFlag = true;
            }
        }
        return deque.isEmpty() && wasBracketFlag;
    }
}
