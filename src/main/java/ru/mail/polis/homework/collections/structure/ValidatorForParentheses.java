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
        if (value == null || value.isEmpty()) {
            return false;
        }
        Stack<Character> stack = new Stack<>(); //Подойдет ли тут ArrayDeque?
        char[] valueCharArray = value.toCharArray();
        for (char c : valueCharArray) {
            switch (c) {
                case '[': {
                    stack.push(']');
                    break;
                }
                case '{': {
                    stack.push('}');
                    break;
                }
                case '<': {
                    stack.push('>');
                    break;
                }
                case '(': {
                    stack.push(')');
                    break;
                }
                default: {
                    if (c == ')' || c == ']' || c == '>' || c == '}') {
                        if (stack.isEmpty() || stack.pop() != c) {
                            return false;
                        }
                    }
                    break;
                }
            }
        }
        return stack.isEmpty();
    }
}
