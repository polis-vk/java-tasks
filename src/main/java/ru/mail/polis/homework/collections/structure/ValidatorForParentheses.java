package ru.mail.polis.homework.collections.structure;

import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private static final Set<Character> opens = Stream.of('(', '{', '[', '<').collect(Collectors.toSet());
    private static final Set<Character> closers = Stream.of(')', '}', ']', '>').collect(Collectors.toSet());

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Character result;
        MyStack<Character> stack = new MyStack<>();
        for (char bracketCandidate : value.toCharArray()) {
            if (opens.contains(bracketCandidate)) {
                stack.push(bracketCandidate);
            } else if (closers.contains(bracketCandidate)) {
                result = stack.pop();
                if (result == null || (result == '[' && bracketCandidate != ']')
                        || (result == '{' && bracketCandidate != '}')
                        || (result == '(' && bracketCandidate != ')')
                        || (result == '<' && bracketCandidate != '>')) {
                    return false;
                }
            }
        }
        return stack.size() == 0;
    }

    private static class MyStack<T> {
        private final LinkedList<T> stack;

        public MyStack() {
            this.stack = new LinkedList<>();
        }

        public void push(T el) {
            stack.addFirst(el);
        }

        public T pop() {
            return !stack.isEmpty() ? stack.removeFirst() : null;
        }

        public T back() {
            return !stack.isEmpty() ? stack.getFirst() : null;
        }

        public int size() {
            return stack.size();
        }

        public void clear() {
            stack.clear();
        }
    }
}
