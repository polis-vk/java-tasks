package ru.mail.polis.homework.collections.structure;

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
    static class MyStack {

        private class Node {
            char data;
            Node next;

            Node(char data) {
                this.data = data;
            }
        }

        private int size;
        private int modCount;
        private Node head;

        //ctor
        public MyStack() {
            this.head = null;
            this.modCount = 0;
            this.size = 0;
        }

        public void push(char element) {
            Node newNode = new Node(element);

            if (this.size == 0) {
                this.head = newNode;
            } else {
                newNode.next = this.head;
                this.head = newNode;
            }
            this.modCount++;
            this.size++;
        }

        public char pop() {
            if (this.size == 0) {
                throw new RuntimeException();
            }

            char value = this.head.data;
            this.head = this.head.next;

            this.modCount++;
            this.size--;
            return value;
        }

        public char back() {
            if (this.size == 0) {
                throw new RuntimeException();
            }

            return this.head.data;
        }

        public int size() {
            return this.size;
        }

        public int getModCount() {
            return this.modCount;
        }
    }

    public static boolean validate(String value) {
        if (value == null) {
            return false;
        }

        if (value.length() < 2) {
            return false;
        }

        Map<Character, Character> bracketMap = Map.of('(', ')', '[', ']', '{', '}', '<', '>');

        MyStack bracketStack = new MyStack();

        for (char sym : value.toCharArray()) {
            if (bracketMap.containsKey(sym)) {
                bracketStack.push(sym);
            } else if (bracketStack.size() != 0 && sym == bracketMap.get(bracketStack.back())) {
                bracketStack.pop();
            } else if (bracketMap.containsValue(sym)) {
                return false;
            }
        }
        return bracketStack.size() == 0 && bracketStack.getModCount() != 0;
    }
}
