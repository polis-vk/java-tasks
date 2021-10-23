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
        if ((value == null) || (value.equals(""))) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (char el : value.toCharArray()) {
            switch (el) {
                case '[':
                case '{':
                case '<':
                case '(':
                    stack.push(el);
                    break;
                case ']':
                    if (!stack.pop().equals('[')) {
                        return false;
                    }
                    break;
                case '}':
                    if (!stack.pop().equals('{')) {
                        return false;
                    }
                    break;
                case '>':
                    if (!stack.pop().equals('<')) {
                        return false;
                    }
                    break;
                case ')':
                    if (!stack.pop().equals('(')) {
                        return false;
                    }
                    break;
                default:
            }
        }
        if (stack.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}