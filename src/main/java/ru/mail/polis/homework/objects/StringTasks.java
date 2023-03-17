package ru.mail.polis.homework.objects;

import java.util.ArrayList;
import java.util.List;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Если вы используете функции типа Double.valueOf() -- получите только половину тугриков.
     * Для полного количества тугриков надо парсить в ручную.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */

    private static final List<Character> list = new ArrayList<>();

    static {
        list.add('0');
        list.add('1');
        list.add('2');
        list.add('3');
        list.add('4');
        list.add('5');
        list.add('6');
        list.add('7');
        list.add('8');
        list.add('9');
        list.add('-');
        list.add('.');
        list.add('e');
    }

    public static Number valueOf(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        int countDots = 0;
        int countE = 0;
        int countMinus = 0;
        for (int i = 0; i < str.length(); i++) {
            char tempChar = str.charAt(i);
            if (list.contains(tempChar)) {
                builder.append(tempChar);
                countDots = tempChar == '.' ? countDots + 1 : countDots;
                countE = tempChar == 'e' ? countE + 1 : countE;
                countMinus = tempChar == '-' ? countMinus + 1 : countMinus;

                if (countDots > 1 || countE > 1 || countMinus > 2) {
                    return null;
                }

                if (i == str.length() - 1 && str.charAt(i) == 'e') {
                    return null;
                }
            }
        }
        String cleanStr = builder.toString();
        if (countMinus == 2 && (!cleanStr.contains("e-") || cleanStr.charAt(0) != '-')) {
            return null;
        }
        if (countMinus == 1 && (!cleanStr.contains("e-") && cleanStr.charAt(0) != '-')) {
            return null;
        }
        return handParse(cleanStr, countE == 1, countDots == 1);
    }

    private static Number handParse(String str, boolean isE, boolean isDot) {
        if (isE || isDot) {
            return Double.valueOf(str);
        }
        long digitLong = Long.parseLong(str);
        if (digitLong > Integer.MAX_VALUE || digitLong < Integer.MIN_VALUE) {
            return digitLong;
        }
        return Integer.parseInt(str);
    }
}
