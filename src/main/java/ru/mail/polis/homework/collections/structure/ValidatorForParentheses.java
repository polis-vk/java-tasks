package ru.mail.polis.homework.collections.structure;

import java.util.*;
import java.util.stream.Collectors;

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

    private static final Map<Character, Character> brackets = mapOf(
            entry('(', ')'),
            entry('[', ']'),
            entry('{', '}'),
            entry('<', '>')
    );

    public static boolean validate(String value) {
        boolean hasParentheses = false;
        if (value == null) {
            return false;
        }
        Deque<Character> stack = new ArrayDeque<>();
        for (char token : value.toCharArray()) {
            if (brackets.containsKey(token)) {
                stack.push(token);
                hasParentheses = true;
                continue;
            }

            if (brackets.containsValue(token) && (stack.isEmpty() || brackets.get(stack.pop()) != token)) {
                return false;
            }
        }
        return stack.isEmpty() && hasParentheses;
    }

    private static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleImmutableEntry<>(key, value);
    }

    @SafeVarargs
    private static <K, V> Map<K, V> mapOf(Map.Entry<K, V>... entries) {
        return Collections.unmodifiableMap(
                Arrays.stream(entries)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
    }
}
