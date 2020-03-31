package ru.mail.polis.homework.objects;

import java.util.regex.Pattern;

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

        StringBuilder strBuilder = new StringBuilder(str.length());
        int dotCounter = 0;
        int eCounter = 0;
        int minusCounter = 0;
        for (int i = 0; i < str.length(); i++) {
            char temp = str.charAt(i);

            if (Character.isDigit(temp)) {
                strBuilder.append(temp);
                continue;
            }
            switch (temp) {
                case 'e':
                    strBuilder.append(temp);
                    eCounter++;
                    continue;
                case '.':
                    strBuilder.append(temp);
                    dotCounter++;
                    continue;
                case '-':
                    strBuilder.append(temp);
                    minusCounter++;
                    continue;
            }
        }
        if (dotCounter > 1 || eCounter > 1 || minusCounter > 2) {       // если точек, е и минусов больше, чем нужно
            return null;
        }

        String result = strBuilder.toString();      // строчка без букв, теперь надо проверить на корректность постановки минусов
        String[] splitStr = result.split("e");     // разделить result на две части, до е и после е

        for (String s : splitStr) {
            if (validMinus(s)) {
                return null;
            }
        }

        if (dotCounter != 0 || eCounter != 0) {
            double beforeE = convertToDouble(splitStr[0]);
            long afterE = 0;
            if (splitStr.length > 1) {
                afterE = convertToLong(splitStr[1]);
            }
            return beforeE * Math.pow(10, afterE);
        }
        long beforeE = convertToLong(splitStr[0]);
        if (beforeE >= Integer.MIN_VALUE && beforeE <= Integer.MAX_VALUE) {
            return (int) beforeE;
        }
        return beforeE;
    }

    private static boolean validMinus(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-' && i != 0) {
                return true;
            }
        }
        return false;
    }

    private static long convertToLong(String number) {
        long result = 0;
        int isNegative = 1;
        if (number.charAt(0) == '-') {
            isNegative = -1;
            number = number.substring(1);
        }
        for (int i = 0; i < number.length(); i++) {
            result += number.charAt(i) - '0';
            result *= 10;
        }
        return result / 10 * isNegative;
    }

    private static double convertToDouble(String number) {
        StringBuilder strBuilder = new StringBuilder(number.length());
        int indexOfDot = -1;

        for (int i = 0; i < number.length(); i++) {
            if (Character.isDigit(number.charAt(i)) || number.charAt(i) == '-') {
                strBuilder.append(number.charAt(i));
            } else {
                indexOfDot = i;
            }
        }
        number = strBuilder.toString();
        if (indexOfDot != -1) {
            return (double) convertToLong(number) / (Math.pow(10, (number.length() - indexOfDot)));
        } else {
            return (double) convertToLong(number);
        }
    }
}
