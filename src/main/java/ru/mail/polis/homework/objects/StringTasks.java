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
        int length = str.length();
        StringBuilder newStr = new StringBuilder();
        boolean minus = false;
        boolean minusE = false;
        boolean e = false;
        boolean digits = false;
        boolean digitsE = false;
        boolean dot = false;
        char currentChar;

        for (int i = 0; i < length; i++) {
            currentChar = str.charAt(i);
            if (Character.isDigit(currentChar)) {
                if (e) {
                    digitsE = true;
                } else {
                    digits = true;
                }
                newStr.append(currentChar);
                continue;
            }
            if (currentChar == '-') {
                if (e && !minusE && !digitsE) {
                    minusE = true;
                    continue;
                }
                if (!digits && !minus) {
                    minus = true;
                    continue;
                }
                return null;
            }
            if (currentChar == '.') {
                if (!dot && i != length - 1) {
                    dot = true;
                    newStr.append(currentChar);
                    continue;
                }
                return null;
            }
            if (currentChar == 'e') {
                if (!e && i != length - 1) {
                    e = true;
                    newStr.append(currentChar);
                    continue;
                }
                return null;
            }
        }

        int sign = 1;
        if (minus) {
            sign = -1;
        }
        if (!e && dot) {
            int dotIndx = newStr.indexOf(".");
            double first = toIntOrLong(newStr.substring(0, dotIndx));
            double second = toIntOrLong(newStr.substring(dotIndx + 1));
            return sign * (first + second / Math.pow(10, newStr.length() - dotIndx - 1));
        }

        if (e && !dot) {
            int eIndx = newStr.indexOf("e");
            int signE = 1;
            if (minusE) {
                signE = -1;
            }
            double first = toIntOrLong(newStr.substring(0, eIndx));
            double second = toIntOrLong(newStr.substring(eIndx + 1));

            return sign * first * Math.pow(10, second * signE);
        }

        if (e && dot) {

            int dotIndx = newStr.indexOf(".");
            int eIndx = newStr.indexOf("e");
            int signE = 1;
            if (minusE) {
                signE = -1;
            }
            double first = toIntOrLong(newStr.substring(0, dotIndx));
            double second = toIntOrLong(newStr.substring(dotIndx + 1, eIndx));
            double third = toIntOrLong(newStr.substring(eIndx + 1));
            return sign * (first + second / Math.pow(10, eIndx - dotIndx - 1)) * Math.pow(10, third * signE);
        }

        double result = sign * toIntOrLong(newStr.toString());
        if (result <= Integer.MAX_VALUE && result >= Integer.MIN_VALUE) {
            return (int) result;
        }
        return (long) result;

    }

    public static double toIntOrLong(String str) {
        double result = Character.getNumericValue(str.charAt(0));
        for (int i = 1; i < str.length(); i++) {
            result = result * 10 + Character.getNumericValue(str.charAt(i));
        }
        return result;
    }
}
