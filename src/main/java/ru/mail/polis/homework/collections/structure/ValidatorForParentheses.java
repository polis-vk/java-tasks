package ru.mail.polis.homework.collections.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private static final Map<Character, Character> brackets = Map.of('(', ')', '[', ']', '{', '}');

    public static boolean validate(String value) {
        if (value == null || value.equals("")) {
            return false;
        }
        List<Character> list = new ArrayList<>(); // ArrayList так как Stack deprecated
        for (char c : value.toCharArray()) {
            if (brackets.containsKey(c)) {
                list.add(c);
            } else if (!list.isEmpty() && c == brackets.get(list.get(list.size() - 1))) {
                list.remove(list.size() - 1);
            } else if (brackets.containsValue(c)) { //Если это несоответствующая закрывающая, то преждевременно выходим, если не скобка, то просто идем дальше
                return false;
            }
        }
        return list.isEmpty();
    }
}
