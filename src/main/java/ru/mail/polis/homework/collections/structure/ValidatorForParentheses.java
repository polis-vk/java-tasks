package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.LinkedList;

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
        }

        Deque<Character> deque = null;
        for (char ch : value.toCharArray()) {
            if (ch == '{' || ch == '[' || ch == '(' || ch == '<') {
                if (deque == null) {
                    deque = new LinkedList<>();
                }
                deque.addFirst(ch);
            } else {
                if ((deque != null && !deque.isEmpty())
                        && ((deque.peekFirst() == '{' && ch == '}')
                        || (deque.peekFirst() == '[' && ch == ']')
                        || (deque.peekFirst() == '(' && ch == ')')
                        || (deque.peekFirst() == '<' && ch == '>'))) {
                    deque.removeFirst();
                }
            }
        }
        return deque != null && deque.isEmpty();
    }
}
