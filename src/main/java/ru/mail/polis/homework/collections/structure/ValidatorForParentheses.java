package ru.mail.polis.homework.collections.structure;

import java.util.Map;
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

    private static final Map<Character, Character> brackets = Map.of('[', ']', '{', '}', '<', '>', '(', ')');

    public static boolean validate(String value) {
        boolean bracketFlag = false;
        Stack stack = new Stack();
        if (value == null || value.isEmpty()) {
            return false;
        }
        for (char ch : value.toCharArray()) {
            if (!isBracket(ch)) {
                continue;
            }
            bracketFlag = true;
            if (brackets.containsKey(ch)) {
                stack.push(ch);
            } else {
                if (stack.isEmpty() || ch != brackets.get(stack.last.value)) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return bracketFlag && stack.isEmpty();
    }

    private static boolean isBracket(char c) {
        return brackets.containsKey(c) || brackets.containsValue(c);
    }

    private static class Stack {
        private Node last;
        private int size;

        public void push(char value) {
            size++;
            Node node = new Node(value);
            if (last != null) {
                node.prev = last;
            }
            last = node;
        }

        public void pop() {
            size--;
            last = last.prev;
        }

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private class Node {
            private char value;
            private Node prev;

            public Node(char value) {
                this.value = value;
            }
        }
    }
}
