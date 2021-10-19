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

    public static boolean validate(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        Stack<Character> parenthesesStack = new Stack<>();
        for (char sym : value.toCharArray()) {
            switch (sym) {
                case '(':
                case '[':
                case '{':
                case '<':
                    parenthesesStack.push(sym);
                    break;
                case ')':
                    if (parenthesesStack.peek() != '(') {
                        return false;
                    }
                    parenthesesStack.pop();
                    break;
                case ']':
                    if (parenthesesStack.peek() != '[') {
                        return false;
                    }
                    parenthesesStack.pop();
                    break;
                case '}':
                    if (parenthesesStack.peek() != '{') {
                        return false;
                    }
                    parenthesesStack.pop();
                    break;
                case '>':
                    if (parenthesesStack.peek() != '<') {
                        return false;
                    }
                    parenthesesStack.pop();
                    break;
                default:
                    break;
            }
        }
        return parenthesesStack.empty();
    }
}
