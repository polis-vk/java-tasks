package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
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

    private static final Map<Character, Character> parentheses = new HashMap<>();

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        parentheses.put(']', '[');
        parentheses.put('}', '{');
        parentheses.put('>', '<');
        parentheses.put(')', '(');
        boolean flag = false;
        Stack stack = new Stack();
        for (int i = 0; i < value.length(); i++) {
            char brace = value.charAt(i);
            if (parentheses.containsValue(brace)) {
                flag = true;
                stack.push(brace);
            } else if (parentheses.containsKey(brace)) {
                if (stack.size() == 0 || stack.pop() != parentheses.get(brace)) {
                    return false;
                }
            }
        }
        return (flag && stack.size() == 0);
    }

    private static class Node {
        private final char value;
        private Node prev;

        public Node(char value) {
            this.value = value;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }
    }

    private static class Stack {
        private int size;
        private Node last;

        public void push(char n) {
            if (last == null) {
                last = new Node(n);
                size++;
                return;
            }
            Node node = new Node(n);
            node.setPrev(last);
            last = node;
            size++;
        }

        public char pop() {
            char value = last.value;
            last = last.prev;
            size--;
            return value;
        }

        public int size() {
            return size;
        }
    }
}


