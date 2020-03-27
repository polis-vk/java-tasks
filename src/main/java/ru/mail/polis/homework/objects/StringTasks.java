package ru.mail.polis.homework.objects;

import java.net.Inet4Address;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Нельзя использовать функции Double.valueOf() и другие такие же.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        int pointCounts = getSymbolCounts(str, ".");
        int eCounts = getSymbolCounts(str, "e");
        int minusCounts = getSymbolCounts(str, "-");
        boolean isReal = pointCounts + eCounts == 0;
        if (pointCounts > 1 || eCounts > 1 || minusCounts > 2) {
            return null;
        }

        StringBuilder digitStringBuilder = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c) || c == 'e' || c == '-' || c == '.') {
                digitStringBuilder.append(c);
            }
        }
        String digitString = digitStringBuilder.toString();

        if (getSymbolCounts(digitString, "--") != 0
                || getSymbolCounts(digitString, "-e") != 0
                || getSymbolCounts(digitString, "-.") != 0
                || digitString.endsWith("-")) {
            return null;
        }

        if (!isReal) {
            long longResult = Long.parseLong(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return Integer.valueOf(digitString);
            }
            return longResult;
        }

        return Double.valueOf(digitString);
    }

    private static int getSymbolCounts(String str, String s) {
        return str.length() - str.replace(s, "").length();
    }
}
