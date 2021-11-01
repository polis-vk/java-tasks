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

    public static boolean validate(String value) {
        boolean existingParenthesis = false;
        if (value == null || value.equals("")) return false;
        Stack<Character> parentheses = new Stack<>();
        for (char c : value.toCharArray()) {
            if (openParenthesesEquals(c)) {
                existingParenthesis = true; // указывает на наличие хотя бы одной открывающей скобки
                parentheses.push(c);
            } else if (closeParenthesesEquals(c)) {
                if (parentheses.peek() != getReverseParentheses(c)) {
                    return false;
                }
                if (parentheses.peek() == getReverseParentheses(c)) {
                    parentheses.pop();
                }
            }
        }
        if (existingParenthesis) {
            return (parentheses.size() == 0);
        }
        return false;

    }

    private static boolean openParenthesesEquals(char c) {
        return c == '(' || c == '<' || c == '{' || c == '[';
    }

    private static boolean closeParenthesesEquals(char c) {
        return c == ')' || c == '>' || c == '}' || c == ']';
    }

    private static char getReverseParentheses(char c) {
        return switch (c) {
            case '}' -> '{';
            case ')' -> '(';
            case '>' -> '<';
            case ']' -> '[';
            default -> ';';
        };
    }
}