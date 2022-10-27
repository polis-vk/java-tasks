package ru.mail.polis.homework.collections.structure;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

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

    private static final Map<Character, Character> brackets = new HashMap<Character, Character>() {{
        put('[', ']');
        put('{', '}');
        put('<', '>');
        put('(', ')');
    }};

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        Deque<Character> deque = null;
        for (char ch : value.toCharArray()) {
            if (brackets.containsKey(ch)) {
                if (deque == null) {
                    deque = new LinkedList<>();
                }
                deque.addFirst(ch);
            } else {
                if (deque != null && !deque.isEmpty() && ch == brackets.get(deque.peekFirst())) {
                    deque.removeFirst();
                }
            }
        }
        return deque != null && deque.isEmpty();
    }
}
