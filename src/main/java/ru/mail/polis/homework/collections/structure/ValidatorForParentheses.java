package ru.mail.polis.homework.collections.structure;


import java.util.Stack;

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
    public static char getOtherParentheses(char par) {
        switch (par) {
            case ')':
                return '(';
            case ']':
                return '[';
            case '}':
                return '{';
            case '>':
                return '<';
            default:
                return 0;
        }
    }

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        boolean parenthesesExist = false;
        Stack<Character> parenthesesStack = new Stack<>();
        for (char sym : value.toCharArray()) {
            switch (sym) {
                case '(':
                case '[':
                case '{':
                case '<':
                    parenthesesExist = true;
                    parenthesesStack.push(sym);
                    break;
                case ')':
                case ']':
                case '}':
                case '>':
                    if (parenthesesStack.empty()
                            || parenthesesStack.peek() != getOtherParentheses(sym)) {
                        return false;
                    }
                    parenthesesStack.pop();
                    break;
                default:
                    break;
            }
        }
        return parenthesesStack.empty() && parenthesesExist;
    }
}
