package ru.mail.polis.homework.collections.structure;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

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
    static Map<Character, Character> map = Map.of('(', ')', '[', ']', '{', '}', '<', '>');

    public static boolean validate(String value) {
        if (value == null || value.isEmpty())
            return false;

        boolean bracketsExist = false;

        Deque<Character> deq = new ArrayDeque<>();
        for (char c : value.toCharArray()) {
            if (map.containsKey(c)) {
                deq.push(c);
                bracketsExist = true;
            }else if (map.containsValue(c)) {
                if (deq.isEmpty() || c!= map.get(deq.getFirst()))
                    return false;
                deq.pollFirst();
            }
        }
        return deq.isEmpty() && bracketsExist;
    }
}
