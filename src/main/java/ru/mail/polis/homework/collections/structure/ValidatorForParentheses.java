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
        if (value == null || value.isEmpty()) {
            return false;
        } else if (!(value.contains("{") || value.contains("}") || value.contains("[") || value.contains("]")
                || value.contains("<") || value.contains(">") || value.contains("(") || value.contains(")"))) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        int indexOfCharacter, popElement;
        boolean flag = true;
        for (indexOfCharacter = 0; indexOfCharacter < value.length(); indexOfCharacter++) {
            char characterString = value.charAt(indexOfCharacter);
            switch (characterString) {
                case '(':
                    stack.push('(');
                    break;
                case '[':
                    stack.push('[');
                    break;
                case '{':
                    stack.push('{');
                    break;
                case '<':
                    stack.push('<');
                    break;
                case ')':
                    if (stack.isEmpty()) {
                        flag = false;
                        break;
                    }
                    popElement = stack.pop();
                    if (popElement != '(') flag = false;
                    break;
                case ']':
                    if (stack.isEmpty()) {
                        flag = false;
                        break;
                    }
                    popElement = stack.pop();
                    if (popElement != '[') flag = false;
                    break;
                case '}':
                    if (stack.isEmpty()) {
                        flag = false;
                        break;
                    }
                    popElement = stack.pop();
                    if (popElement != '{') flag = false;
                    break;
                case '>':
                    if (stack.isEmpty()) {
                        flag = false;
                        break;
                    }
                    popElement = stack.pop();
                    if (popElement != '<') flag = false;
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
