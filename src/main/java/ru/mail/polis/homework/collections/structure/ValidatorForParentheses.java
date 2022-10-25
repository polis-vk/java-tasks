package ru.mail.polis.homework.collections.structure;

import java.util.*;

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

    public static boolean validate(String value) {
        if (value == null) {
            return false;
        }

        Stack<Character> stackOfParentheses = new Stack<>();

        Character[] opening = {'(', '[', '<', '{'};
        Character[] closing = {')', ']', '>', '}'};
        int modStackCount = 0;

        for (int i = 0; i < value.length(); i++) {
            char currentSymbol = value.charAt(i);
            if (Arrays.asList(opening).contains(currentSymbol)) {
                stackOfParentheses.add(currentSymbol);
                modStackCount++;
            } else if (Arrays.asList(closing).contains(currentSymbol) && !stackOfParentheses.isEmpty() &&
                    (stackOfParentheses.peek() == '(' && currentSymbol == ')' ||
                            stackOfParentheses.peek() == '{' && currentSymbol == '}' ||
                            stackOfParentheses.peek() == '[' && currentSymbol == ']' ||
                            stackOfParentheses.peek() == '<' && currentSymbol == '>')) {
                stackOfParentheses.pop();
            }
        }

        return modStackCount != 0 && stackOfParentheses.isEmpty();
    }
}
