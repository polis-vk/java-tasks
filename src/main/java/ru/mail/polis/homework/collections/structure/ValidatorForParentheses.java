package ru.mail.polis.homework.collections.structure;

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

    static Node head;
    static int size = 0;

    private static class Node {
        private Node next;
        private char value;

        public Node(char value) {
            this.value = value;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private static void push(char value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
        } else {
            node.next = head;
            head = node;
        }
        size++;
    }

    private static char pop() {
        if (size == 0) {
            return 'e';
        }
        char res = head.value;
        head = head.next;
        size--;
        return res;
    }

    private static boolean solve(String sequence) {
        if (sequence == null || sequence.isEmpty()) {
            return false;
        }
        for (int i = 0; i < sequence.length(); i++) {
            switch (sequence.charAt(i)) {
                case '(': {
                    push('(');
                    break;
                }
                case '{': {
                    push('{');
                    break;
                }
                case '[': {
                    push('[');
                    break;
                }
                case '<': {
                    push('<');
                    break;
                }
                case ')': {
                    if (pop() != '(') {
                        return false;
                    }
                    break;
                }
                case '}': {
                    if (pop() != '{') {
                        return false;
                    }
                    break;
                }
                case ']': {
                    if (pop() != '[') {
                        return false;
                    }
                    break;
                }
                case '>': {
                    if (pop() != '<') {
                        return false;
                    }
                    break;
                }
            }
        }
        return size == 0;
    }

    public static boolean validate(String value) {
        return solve(value);
    }
}
