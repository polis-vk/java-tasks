package ru.mail.polis.homework.objects;

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
        if (str == null || str.length() == 0) {
            return null;
        }

        int pointCounts = getSymbolCounts(str, ".");
        int eCounts = getSymbolCounts(str, "e");
        int minusCounts = getSymbolCounts(str, "-");

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

        if (pointCounts + eCounts == 0) {
            long longResult = signOfLong(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return (int) longResult;
            }
            return longResult;
        }

        return doubleResult(digitString);
    }

    private static long signOfLong(String str) {
        String dublicate = str;
        int sign = 1;
        if (dublicate.charAt(0) == '-') {
            sign = -1;
        }
        if (sign == -1) {
            dublicate = dublicate.replace("-", "");
        }
        long number = 0;
        for (char digit : dublicate.toCharArray()) {
            number = (number + (digit - 48)) * 10;
        }
        return number * sign / 10;
    }

    private static int getSymbolCounts(String str, String s) {
        return str.length() - str.replace(s, "").length();
    }

    private static double doubleResult(String str) {
//прошу прощения, немного не успел
        return 10;
    }


}
