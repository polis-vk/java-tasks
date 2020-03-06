package ru.mail.polis.homework.objects;

import java.util.Arrays;
import java.util.List;

public class StringTasks {

    /**
     * @param digitString which contains only digits
     * @return long value of digitStirng
     */
    public static long longValueOf(String digitString) {
        long result = 0;
        char[] charArray;
        boolean isNegative = false;

        if (digitString.startsWith("-")) {
            charArray = digitString.substring(1).toCharArray();
            isNegative = true;
        } else {
            charArray = digitString.toCharArray();
        }

        for (int i = charArray.length - 1; i >= 0; i--) {
            if (Character.isDigit(charArray[i])) {
                result += Character.getNumericValue(charArray[i]) * Math.pow(10, charArray.length - i - 1);
            }
        }

        if (isNegative) {
            result *= -1;
        }
        return result;
    }

    /**
     * @param digitString which contains only digits
     * @return integer value of digitString
     */
    public static int integerValueOf(String digitString) {
        return (int) longValueOf(digitString);
    }

    /**
     * @param digitString which contains only digits, "e" and "."
     * @return double value of digitString
     */
    public static double doubleValueOf(String digitString) {
        List<String> separateStrings = separateStringToParts(digitString);
        double result = 0.0;
        double integerPart = integerValueOf(separateStrings.get(0));
        double fractionalPart = 1.;
        double exponentPart;

        if (digitString.startsWith("-")) {
            fractionalPart *= -1;
        }

        if (digitString.contains(".") && digitString.contains("e")) {
            fractionalPart *= Math.pow(10, -separateStrings.get(1).length()) * integerValueOf(separateStrings.get(1));
            exponentPart = Math.pow(10, integerValueOf(separateStrings.get(2)));
            result = (integerPart + fractionalPart) * exponentPart;
        } else if (digitString.contains(".")) {
            fractionalPart *= Math.pow(10, -separateStrings.get(0).length()) * integerValueOf(separateStrings.get(1));
            result = integerPart + fractionalPart;
        } else if (digitString.contains("e")) {
            exponentPart = Math.pow(10, integerValueOf(separateStrings.get(1)));
            result = integerPart * exponentPart;
        }
        return result;
    }

    /**
     * @param digitString which contains only digits, 'e' and '.'
     * @return List<String> of parts of input string
     * E.g. "123.2e-3" -> List.of("123", "2", "-3")
     */
    private static List<String> separateStringToParts(String digitString) {
        int countOfParts;
        if (digitString.contains(".") && digitString.contains("e")) {
            countOfParts = 3;
        } else if (digitString.contains(".") || digitString.contains("e")) {
            countOfParts = 2;
        } else {
            return Arrays.asList(digitString);
        }
        String[] parts = new String[countOfParts];

        if (countOfParts == 3) {
            String[] temp = digitString.split("\\.");
            parts[0] = temp[0];
            temp = temp[1].split("e");
            parts[1] = temp[0];
            parts[2] = temp[1];
        }

        if (countOfParts == 2 && digitString.contains(".")) {
            parts = digitString.split("\\.");
        }

        if (countOfParts == 2 && digitString.contains("e")) {
            parts = digitString.split("e");
        }
        return Arrays.asList(parts);
    }

    /**
     * @param digitString
     * @return true if digitString is valid
     */
    private static boolean isValidDigitString(String digitString) {
        return !(digitString.length() - digitString.replace("--", "").length() != 0
                || digitString.length() - digitString.replace("-e", "").length() != 0
                || digitString.length() - digitString.replace("-.", "").length() != 0
                || digitString.endsWith("-"));
    }

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

        int pointCounts = str.length() - str.replace(".", "").length();
        int eCounts = str.length() - str.replace("e", "").length();
        int minusCounts = str.length() - str.replace("-", "").length();

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
        if (!isValidDigitString(digitString)) {
            return null;
        }

        if (pointCounts + eCounts == 0) {
            long longResult = longValueOf(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return integerValueOf(digitString);
            }
            return longResult;

        }

        return doubleValueOf(digitString);
    }


}
