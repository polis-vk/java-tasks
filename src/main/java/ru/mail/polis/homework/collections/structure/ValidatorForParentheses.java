package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Queue;

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

    private static final String accessibleSymbols = "[]{}<>()";

    private static boolean isOpposite(char a, char b) {
        return (a == '[' && b == ']') || (a == '{' && b == '}') || (a == '<' && b == '>') || (a == '(' && b == ')');
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Queue<Character> stack = Collections.asLifoQueue(new ArrayDeque<>());
        for (int i = 0; i < value.length(); ++i) {
            int inx = accessibleSymbols.indexOf(value.charAt(i));
            if (inx != -1) {
                if (inx % 2 == 0) {
                    stack.add(value.charAt(i));
                } else {
                    if (!stack.isEmpty() && isOpposite(stack.peek(), value.charAt(i))) {
                        stack.remove();
                    } else {
                        return false;
                    }
                }
            }
        }
        return stack.size() == 0;
    }
}
