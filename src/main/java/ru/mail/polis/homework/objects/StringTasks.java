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
            if (isNotContain(string, 'e')) {
                if (isNotContain(string, '.')) {
                    long num = strToLong(string);
                    if (num < -2147483648 || num > 2147483647) {
                        return num;
                    }
                    return (int) num;
                }
            }
            return strToDouble(string);
        }
        return null;
    }

    private static boolean isNotContain(String string, char symbol) {
        int countInside = 0;
        for (char ch : string.toCharArray()) {
            if (ch == symbol) {
                countInside++;
            }
        }
        return countInside == 0;
    }

    private static int howMuchContain(String string) {
        int countInside = 0;
        for (char ch : string.toCharArray()) {
            if (ch == '-') {
                countInside++;
            }
        }
        return countInside;
    }

    private static String toClear(String string) {
        boolean flagMinus = false;
        boolean flagE = false;
        boolean flagDot = false;
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isDigit(c) || c == '-' || c == '.' || c == 'e') {
                if (c == '-') {
                    if (flagMinus || flagDot) {
                        return null;
                    }
                    flagMinus = true;
                    stringBuilder.append(c);
                    flagDot = false;
                    flagE = false;
                    continue;
                }
                if (c == 'e') {
                    if (flagE || flagDot || flagMinus) {
                        return null;
                    }
                    flagE = true;
                    stringBuilder.append(c);
                    flagDot = false;
                    flagMinus = false;
                    continue;
                }
                if (c == '.') {
                    if (flagDot || flagMinus || flagE) {
                        return null;
                    }
                    flagDot = true;
                    stringBuilder.append(c);
                    flagE = false;
                    flagMinus = false;
                    continue;
                }
                stringBuilder.append(c);
                flagDot = false;
                flagE = false;
                flagMinus = false;
            }
        }
        String s = stringBuilder.toString();
        if (howMuchContain(s) > 2 || s.charAt(s.length() - 1) == '-') {
            return null;
        }
        return s;
    }

    private static long strToLong(String s) {
        long temp = 0L; // число
        int i = 0;
        int sign = 0; // знак числа 0- положительное, 1 — отрицательное
        if (s.charAt(i) == '-') {
            sign = 1;
            i++;
        }
        while (i < s.length() && s.charAt(i) >= 0x30 && s.charAt(i) <= 0x39) {
            temp = temp + (long) (s.charAt(i) & 0x0F);
            temp = temp * 10L;
            i++;
        }
        temp = temp / 10;
        if (sign == 1)
            temp = -temp;
        return (temp);
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
