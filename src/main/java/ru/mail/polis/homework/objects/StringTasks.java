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
        if (str == null || str.isEmpty()) {
            return null;
        }
        String string = toClear(str);

        if (string != null) {
            if (string.indexOf('e') == -1) {
                if (string.indexOf('.') == -1) {
                    long num = strToLong(string);
                    if (num < Integer.MIN_VALUE || num > Integer.MAX_VALUE) {
                        return num;
                    }
                    return (int) num;
                }
            }
            return strToDouble(string);
        }
        return null;
    }

    private static String toClear(String string) {
        boolean flagMinus = false;
        boolean flagE = false;
        boolean flagDot = false;
        int countMinus = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            switch (c) {
                case ('-'):
                    countMinus++;
                    if (flagMinus || flagDot) {
                        return null;
                    }
                    flagMinus = true;
                    stringBuilder.append(c);
                    flagDot = false;
                    flagE = false;
                    continue;
                case ('.'):
                    if (flagDot || flagMinus || flagE) {
                        return null;
                    }
                    flagDot = true;
                    stringBuilder.append(c);
                    flagE = false;
                    flagMinus = false;
                    continue;
                case ('e'):
                    if (flagE || flagDot || flagMinus) {
                        return null;
                    }
                    flagE = true;
                    stringBuilder.append(c);
                    flagDot = false;
                    flagMinus = false;
                    continue;
                case ('0'):
                case ('1'):
                case ('2'):
                case ('3'):
                case ('4'):
                case ('5'):
                case ('6'):
                case ('7'):
                case ('8'):
                case ('9'):
                    stringBuilder.append(c);
                    flagDot = false;
                    flagE = false;
                    flagMinus = false;

            }
        }
        String s = stringBuilder.toString();
        if (countMinus > 2 || s.charAt(s.length() - 1) == '-') {
            return null;
        }
        return s;
    }

    private static long strToLong(String s) {
        long temp = 0L; // число
        int i = 0;
        int sign = 0; // знак числа 0- положительное, 1 — отрицательное
        char[] str = s.toCharArray();
        if (str[i] == '-') {
            sign = 1;
            i++;
        }

        while (i < str.length && str[i] >= '0' && str[i] <= '9') {
            temp = temp + (long) (str[i] - '0');
            temp = temp * 10L;
            i++;
        }
        temp = temp / 10;
        if (sign == 1) {
            temp = -temp;
        }
        return temp;
    }

    private static double strToDouble(String str) {
        int pointIndex = str.indexOf('.');
        int eIndex = str.indexOf('e');

        if (pointIndex == -1) {
            long number = strToLong(str.substring(0, eIndex));
            long mantissa = strToLong(str.substring(eIndex + 1));
            return number * Math.pow(10, mantissa);
        }

        if (eIndex == -1) {
            long beforePoint = strToLong(str.substring(0, pointIndex));
            long afterPoint = strToLong(str.substring(pointIndex + 1));
            return beforePoint + afterPoint / Math.pow(10, str.length() - pointIndex - 1);
        }

        long beforePoint = strToLong(str.substring(0, pointIndex));
        long afterPoint = strToLong(str.substring(pointIndex + 1, eIndex));
        long mantissa = strToLong(str.substring(eIndex + 1));
        return (beforePoint + afterPoint / Math.pow(10, eIndex - pointIndex - 1)) * Math.pow(10, mantissa);
    }
}
