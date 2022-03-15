package ru.mail.polis.homework.objects;

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
    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        String cleanString = str.replaceAll("[^0-9.e-]", "");
        if (!cleanString.matches("-?[0-9]+(\\.[0-9]+)?(e-?[0-9]+)?")) {
            return null;
        }
        boolean thereDot = cleanString.contains(".");
        boolean thereE = cleanString.contains("e");
        if (thereE || thereDot) {
            return getValue(cleanString, thereDot, thereE);
        }
        long number = (long) convertToNumber(cleanString);
        if ((int) number == number) {
            return (int) number;
        }
        return number;
    }

    private static Number convertToNumber(String str) {
        int startIndex;
        boolean negative = false;
        long number = 0L;
        if (str.charAt(0) == '-') {
            startIndex = 1;
            negative = true;
        } else {
            startIndex = 0;
        }
        for (int i = startIndex; i < str.length(); i++) {
            number += getDigit(str.charAt(i));
            number *= 10;
        }
        number = (negative ? -1 : 1) * number / 10;
        return number;
    }

    private static double getValue(String str, boolean thereDot, boolean thereE) {
        String beforeDot;
        String afterDot = "";
        String beforeE;
        String afterE = "";
        long afterDotLength = 0L;
        if (thereE) {
            beforeE = str.split("e")[0];
            afterE = str.split("e")[1];
        } else {
            beforeE = str;
        }
        if (thereDot) {
            beforeDot = beforeE.split("\\.")[0];
            afterDot = beforeE.split("\\.")[1];
            afterDotLength = afterDot.length();

        } else {
            beforeDot = beforeE;
        }
        double number = (long) convertToNumber(beforeDot);
        for (int i = 0; i < afterDotLength; i++) {
            number *= 10;
            number += getDigit(afterDot.charAt(i));
        }
        number /= Math.pow(10, afterDotLength);
        if (!(afterE.isEmpty())) {
            number *= Math.pow(10, (long) convertToNumber(afterE));
        }
        return number;
    }

    private static int getDigit(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            default:
                return -1;
        }
    }
}
