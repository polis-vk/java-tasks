package ru.mail.polis.homework.objects;

import javax.swing.text.NumberFormatter;

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
        if ((str.indexOf("e") != str.lastIndexOf("e")) || (str.indexOf(".") != str.lastIndexOf(".")) ||
                        str.contains("--") || str.endsWith("-") || str.endsWith("e") || str.endsWith(".")) {
            return null;
        }
        StringBuilder resultString = new StringBuilder();
        boolean isDouble = false;
        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) == 'e') || (str.charAt(i) == '.') || (str.charAt(i) == '-') || Character.isDigit(str.charAt(i))) {
                resultString.append(str.charAt(i));
                if ((str.charAt(i) == 'e') || (str.charAt(i) == '.')) {
                    isDouble = true;
                }
            }
        }
        String checkString = resultString.toString();
        if (checkString.contains("e.") || checkString.contains(".e") || checkString.contains("-e") ||
            checkString.contains("--") || checkString.endsWith(".") || checkString.endsWith("e") ||
            checkString.endsWith("-") || checkString.contains(".-") || checkString.contains("-.")) {
            return null;
        }
        if (isDouble) {
            return parseDouble(resultString.toString());
        }
        long resultNumber = parseLong(checkString);
        if ((resultNumber < Integer.MIN_VALUE) || (resultNumber > Integer.MAX_VALUE)) {
            return resultNumber;
        }
        return (int) resultNumber;
    }

    private static int parseChar(char parChar) {
        switch (parChar) {
            case '0': return 0;
            case '1': return 1;
            case '2': return 2;
            case '3': return 3;
            case '4': return 4;
            case '5': return 5;
            case '6': return 6;
            case '7': return 7;
            case '8': return 8;
            case '9': return 9;
            default: return Integer.MIN_VALUE;
        }
    }

    private static long parseLong(String parString) {
        long resultLong = 0;
        boolean isNegative = false;
        String numString;
        if (parString.charAt(0) == '-') {
            isNegative = true;
            numString = parString.substring(1);
        }
        else {
            numString = parString;
        }
        for (int i = 0; i < numString.length(); i++) {
            resultLong += parseChar(numString.charAt(i)) * Math.pow(10, numString.length() - i - 1);
        }
        if (isNegative) {
            resultLong *= -1;
        }
        return resultLong;
    }

    private static double parseDouble(String parString) {
        double resultDouble = 0;
        boolean isNegative = false;
        String numString;
        if (parString.charAt(0) == '-') {
            isNegative = true;
            numString = parString.substring(1);
        }
        else {
            numString = parString;
        }
        String[] stringParts = numString.split("\\.|e");
        for (int i = 0; i < stringParts[0].length(); i++) {
            resultDouble += parseChar(stringParts[0].charAt(i)) * Math.pow(10, stringParts[0].length() - i - 1);
        }
        if (isNegative) {
            resultDouble *= -1;
        }
        if (numString.contains(".")) {
            for (int i = 0; i < stringParts[1].length(); i++) {
                resultDouble += parseChar(stringParts[1].charAt(i)) * Math.pow(10, - i - 1);
            }
        }
        if (numString.contains("e")) {
            isNegative = false;
            int ePower = 0;
            String stringPower;
            if (stringParts[stringParts.length - 1].charAt(0) == '-') {
                isNegative = true;
                stringPower = stringParts[stringParts.length - 1].substring(1);
            }
            else {
                stringPower = stringParts[stringParts.length - 1];
            }
            for (int i = 0; i < stringPower.length(); i++) {
                ePower += parseChar(stringPower.charAt(i)) * Math.pow(10,stringPower.length() - i - 1);
            }
            if (isNegative) {
                ePower *= -1;
            }
            resultDouble *= Math.pow(10, ePower);
        }
        return resultDouble;
    }
}
