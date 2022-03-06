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
        StringBuilder strBuilder = new StringBuilder();
        boolean isMinus = false;
        boolean isE = false;
        boolean isPoint = false;
        boolean isDigit = false;
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isDigit(c)) {
                strBuilder.append(c);
                isDigit = true;
            } else {
                if (c == '-') {
                    if (isMinus || isDigit || isPoint) {
                        return null;
                    }
                    strBuilder.append(c);
                    isMinus = true;
                } else if (c == 'e') {
                    if (isE) {
                        return null;
                    }
                    strBuilder.append(c);
                    isE = true;
                    isDigit = false;
                    isMinus = false;
                    isPoint = false;
                } else if (c == '.') {
                    if (isPoint) {
                        return null;
                    }
                    strBuilder.append(c);
                    isPoint = true;
                }
            }
        }
        if (isE && !isDigit) {
            return null;
        }
        return returnAnswer(strBuilder.toString(), isMinus, isE, isPoint);
    }

    public static Number returnAnswer(String str, boolean isMinus, boolean isE, boolean isPoint) {
        if (isE || isPoint) {
            return castDouble(str, isE);
        }
        long number = (Long) castIntOrLong(str, isMinus, false);
        if (number >= Integer.MIN_VALUE && number <= Integer.MAX_VALUE) {
            return castIntOrLong(str, isMinus, true);
        }
        return number;
    }

    public static Number castIntOrLong(String str, boolean isMinus, boolean isInteger) {
        long number = 0L;
        for (int i = isMinus ? 1 : 0; i < str.length(); i++) {
            number += getDigit(str.charAt(i));
            number *= 10;
        }
        number = (isMinus ? -1 : 1) * number / 10;
        if (isInteger) {
            return (int) number;
        }
        return number;
    }

    public static double castDouble(String str, boolean isE) {
        String beforeE;
        String afterE = "0";
        String beforePoint;
        String afterPoint = "0";
        if (isE) {
            beforeE = str.split("e")[0];
            afterE = str.split("e")[1];
        } else {
            beforeE = str;
        }
        if (beforeE.contains(".")) {
            beforePoint = beforeE.split("\\.")[0];
            afterPoint = beforeE.split("\\.")[1];
        } else {
            beforePoint = beforeE;
        }
        double number = 1.0 * (Long) castIntOrLong(beforePoint, (beforePoint.charAt(0) == '-'), false);
        for (int i = 0; i < afterPoint.length(); i++) {
            number *= 10;
            number += getDigit(afterPoint.charAt(i));
        }
        number /= Math.pow(10, afterPoint.length());
        number *= Math.pow(10, 1.0 * (Long) castIntOrLong(afterE, (afterE.charAt(0) == '-'), false));
        return number;
    }

    public static int getDigit(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return '1' - '0';
            case '2':
                return '2' - '0';
            case '3':
                return '3' - '0';
            case '4':
                return '4' - '0';
            case '5':
                return '5' - '0';
            case '6':
                return '6' - '0';
            case '7':
                return '7' - '0';
            case '8':
                return '8' - '0';
            case '9':
                return '9' - '0';
            default:
                return -1;
        }
    }
}
