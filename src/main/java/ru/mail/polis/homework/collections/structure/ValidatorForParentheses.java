package ru.mail.polis.homework.collections.structure;

import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

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
        if (value == null || value.isEmpty()) {
            return false;
        }
        Map<Integer, Integer> brackets = new HashMap<>();
        brackets.put((int) ']', (int) '[');
        brackets.put((int) '}', (int) '{');
        brackets.put((int) '>', (int) '<');
        brackets.put((int) ')', (int) '(');
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        boolean mod = false;
        for (int i :
                value.getBytes(StandardCharsets.UTF_8)) {
            if (brackets.containsValue(i)) {
                arrayDeque.addLast(i);
                mod = true;
            } else if (brackets.containsKey(i)) {
                if (arrayDeque.size() == 0 || !arrayDeque.pollLast().equals(brackets.get(i))) {
                    return false;
                }
            }
        }
        return arrayDeque.size() == 0 && mod;
    }
}
