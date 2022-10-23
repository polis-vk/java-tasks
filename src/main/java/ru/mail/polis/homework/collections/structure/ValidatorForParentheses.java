package ru.mail.polis.homework.collections.structure;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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

    // Создаем словарь пар (левая-правая) скобок.Инициилизируем их. Объявляем стек.

    private static Map<Character, Character> map = new HashMap<>();

    static {
        map.put('[', ']');
        map.put('(', ')');
        map.put('{', '}');
        map.put('<', '>');
    }

    public static boolean validate(String value) {
        /*
         * Сперва проверяется не пустая ли строка и на null. Далее создается стек и переменная овтечающая за то,
         * добавлялись ли скобки в стек для того, чтобы учесть соообщения, состоящие только не из скобок. Далее
         * проходимся по каждому символу. Если он содержится в словаре ключей (левые скобки), то заносим этот символ
         * в стек. Если этот символ - закрывающаяся скобка - то проверяем не является ли она первой в строке. Если да,
         * то возвращаем false. Если нет, то проверяем является ли эта закрывающаяся скобка парой для открывающейся в
         * вершине стека. Если нет - возвращаем false. Продолжаем до конца строки. Если в стек добавлялись скобки и стек
         * пустой, то скобки валидные, иначе возвращаем false.
         */

        // Проверяем на null и не пустая ли строчка.
        if (value == null || value.isEmpty()) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        boolean isChanged = false;

        for (char obj : value.toCharArray()) {

            // Если это открывающаяся скобка - заносим в стек.
            if (map.containsKey(obj)) {
                stack.push(obj);
                isChanged = true;
            }

            // Если это закрывающаяся скобка и не первая, то проверяем парность. Удаляем откр., с которой совпало.
            if (map.containsValue(obj) && !stack.isEmpty()) {
                if (obj == map.get(stack.peek())) {
                    stack.pop();
                    isChanged = true;
                } else {
                    return false;
                }
            }
        }
        return isChanged && stack.isEmpty();
    }
}
