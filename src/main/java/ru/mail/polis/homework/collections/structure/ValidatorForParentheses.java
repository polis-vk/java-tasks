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

        // node of queue
        private class Node {
            char data;
            Node next;

            // c-tor
            Node(char data) {
                this.data = data;
            }
        }

        // number of el-s in stack
        private int size;
        private int modCount;
        // head of stack
        private Node head;

        //ctor
        public MyStack() {
            this.head = null;
            this.modCount = 0;
            this.size = 0;
        }

        // push elem into queue
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

        // delete head node from queue
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

        // get elem from head of queue
        public char back() {
            if (this.size == 0) {
                throw new RuntimeException();
            }

            return this.head.data;
        }

        // get number of elements of queue
        public int size() {
            return this.size;
        }

        public int getModCount() {
            return this.modCount;
        }

        // delete elements from queue
        public void clear() {
            this.head = null;
            this.size = 0;
            this.modCount++;
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
            if (sym == '(' || sym == '{' || sym == '[' || sym == '<') {
                bracketStack.push(sym);
            } else if (bracketStack.size() != 0 && sym == bracketMap.get(bracketStack.back())) {
                bracketStack.pop();
            } else if (sym == ')' || sym == '}' || sym == ']' || sym == '>') {
                return false;
            }

        }

        return bracketStack.size() == 0 && bracketStack.getModCount() != 0;
    }
}
