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
        StringBuilder tempStr = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char tempChar = str.charAt(i);
            if (Character.isDigit(tempChar)) {
                tempStr.append(tempChar);
            }
            if (str.charAt(i) == 'e' || str.charAt(i) == '-' || str.charAt(i) == '.') {
                tempStr.append(str.charAt(i));
            }
        }
        int dot = str.length() - str.replace(".", "").length();
        int e = str.length() - str.replace("e", "").length();
        int minus = str.length() - str.replace("-", "").length();
        if (dot > 1 || e > 1 || minus > 2) {
            return null;
        }
        if (tempStr.toString().contains("--") || tempStr.toString().contains(".e") ||
                tempStr.toString().contains("e.") || tempStr.toString().contains("-e") ||
                tempStr.toString().startsWith(".") || tempStr.toString().endsWith("e") ||
                tempStr.toString().endsWith("-")) {
            return null;
        }
        if (dot == 0 && e == 0) {
            long result = convertToLong(tempStr.toString());
            if ((int) result == result) {
                return (int) result;
            } else {
                return result;
            }
        } else {
            return convertToDouble(tempStr.toString());
        }
    }

    private static long convertToLong(String tempStr) {
        long result = 0L;
        byte sign = 1;
        int index = 0;
        if (tempStr.charAt(index) == '-') {
            sign = -1;
            index = 1;
        }
        for (int i = index; i < tempStr.length(); i++) {
            result = result * 10 + Character.getNumericValue(tempStr.charAt(i));
        }
        return result * sign;
    }

    private static double convertToDouble(String tempStr) {
        int expIndex = tempStr.indexOf('e');
        int dotIndex = tempStr.indexOf('.');
        long intPart;
        int exponent;
        String afterDotStr;
        double doublePart = 0.0;
        double tempDouble;
        if (dotIndex != -1) {
            intPart = convertToLong(tempStr.substring(0, dotIndex));
            if (expIndex != -1) {
                exponent = (int) convertToLong(tempStr.substring((expIndex + 1)));
                afterDotStr = tempStr.substring((dotIndex + 1), expIndex);
                for (int i = 0; i < afterDotStr.length(); i++) {
                    tempDouble = ((double) Character.getNumericValue(tempStr.substring(dotIndex + 1).charAt(i))) /
                            Math.pow(10, (i + 1));
                    doublePart += tempDouble;
                }
                return (intPart + doublePart) * Math.pow(10, exponent);
            }
            afterDotStr = tempStr.substring(dotIndex + 1);
            doublePart = convertToLong(afterDotStr) / Math.pow(10, afterDotStr.length());
            return intPart + doublePart;
        }
        long mantissa = convertToLong(tempStr.substring(0, expIndex));
        exponent = (int) convertToLong(tempStr.substring((expIndex + 1)));
        return mantissa * Math.pow(10, exponent);
    }
}
