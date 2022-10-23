package ru.mail.polis.homework.collections.structure;

import java.util.Stack;

/**
 * Задание оценивается в 2 тугрика.
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

    public static boolean validate(String value) {
        Stack<Integer> stack = new Stack<>();
        char a;
        int i, k;
        boolean flag = true;
        if (value == null || value.equals("")) {
            return false;
        } else if (!(value.contains("{") || value.contains("}") || value.contains("[") || value.contains("]")
                || value.contains("<") || value.contains(">") || value.contains("(") || value.contains(")"))) {
            return false;
        }
        for (i = 0; i < value.length(); i++) {
            a = value.charAt(i);
            switch (a) {
                case '(':
                    stack.push(1);
                    break;
                case '[':
                    stack.push(2);
                    break;
                case '{':
                    stack.push(3);
                    break;
                case '<':
                    stack.push(4);
                    break;
                case ')':
                    if (stack.isEmpty()) {
                        flag = false;
                        break;
                    }
                    k = stack.peek();
                    stack.pop();
                    if (k != 1) flag = false;
                    break;
                case ']':
                    if (stack.isEmpty()) {
                        flag = false;
                        break;
                    }
                    k = stack.peek();
                    stack.pop();
                    if (k != 2) flag = false;
                    break;
                case '}':
                    if (stack.isEmpty()) {
                        flag = false;
                        break;
                    }
                    k = stack.peek();
                    stack.pop();
                    if (k != 3) flag = false;
                    break;
                case '>':
                    if (stack.isEmpty()) {
                        flag = false;
                        break;
                    }
                    k = stack.peek();
                    stack.pop();
                    if (k != 4) flag = false;
                    break;
                default:
                    break;
            }
            if (!flag) {
                break;
            }
        }
        return flag && stack.isEmpty();
    }
}
