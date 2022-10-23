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
        Deque<Character> deque = new ArrayDeque<>();
        boolean checkWorkDeque = false;
        for (int i = 0; i < value.length(); i++) {
            char charInValue = value.charAt(i);
            if (charInValue == '[' || charInValue == '(' || charInValue == '{' || charInValue == '<') {
                deque.addFirst(charInValue);
            } else if (deque.size() == 0) {
                deque.addLast('0');
            } else if (charInValue == ']' && deque.getFirst() == '[' || charInValue == ')' && deque.getFirst() == '(' || charInValue == '}' && deque.getFirst() == '{' || charInValue == '>' && deque.getFirst() == '<') {
                deque.removeFirst();
                checkWorkDeque = true;
            }
        }
        return !deque.contains('[') && !deque.contains('(') && !deque.contains('{') && !deque.contains('<') && checkWorkDeque;
    }
}
