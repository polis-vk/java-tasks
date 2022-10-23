package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;

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

    public static boolean validate(String value) {
        if (value == null) {
            return false;
        }
        Deque<Character> stack = new ArrayDeque<>();
        int parentheses = 0, brackets = 0, braces = 0, angleBr = 0;
        boolean gotBrackets = false; // for strings without any brackets

        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '(') {
                parentheses++;
            } else if (c == ')') {
                parentheses--;
            } else if (c == '{') {
                braces++;
            } else if (c == '}') {
                braces--;
            } else if (c == '[') {
                brackets++;
            } else if (c == ']') {
                brackets--;
            } else if (c == '<') {
                angleBr++;
            } else if (c == '>') {
                angleBr--;
            }

            if (c == '(' || c == '{' || c == '[' || c == '<') {
                stack.push(c);
                gotBrackets = true;
            }
            if (parentheses < 0 || braces < 0 || brackets < 0 || angleBr < 0) {
                return false;
            }
            if (!stack.isEmpty()) {
                char temp = stack.peek();
                if (c == ')' && temp != '('
                        || c == ']' && temp != '['
                        || c == '}' && temp != '{'
                        || c == '>' && temp != '<') {

                    return false;
                }
                if (c == ')' && temp == '(' || c == ']' && temp == '['
                        || c == '}' && temp == '{' || c == '>' && temp == '<') {
                    stack.pop();
                }
            }
        }
        return parentheses == 0 && braces == 0 && brackets == 0 && angleBr == 0 && gotBrackets;
    }
}
