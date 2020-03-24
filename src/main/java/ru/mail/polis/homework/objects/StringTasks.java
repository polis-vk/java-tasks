package ru.mail.polis.homework.objects;
import java.util.ArrayList;
import java.util.Arrays;

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

        int pointCounts = str.length() - str.replace(".", "").length();
        int eCounts = str.length() - str.replace("e", "").length();
        int minusCounts = str.length() - str.replace("-", "").length();
        int doubleSeparators = pointCounts + eCounts;

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

        if (doubleSeparators == 0) {
            long longResult = longValueOf(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                int result = integerValueOf(digitString);
                return integerValueOf(digitString);
            }
            return longResult;

        }

        return doubleValueOf(digitString);
    }

    private static boolean isValidDigitString(String digitString) {
        return !(digitString.length() - digitString.replace("--", "").length() != 0
                || digitString.length() - digitString.replace("-e", "").length() != 0
                || digitString.length() - digitString.replace("-.", "").length() != 0
                || digitString.endsWith("-"));
    }

    public static long longValueOf(String str) {
        long result = 0;
        char[] charArray;
        boolean isNegative = false;

        if (str.startsWith("-")) {
            charArray = str.substring(1).toCharArray();
            isNegative = true;
        } else {
            charArray = str.toCharArray();
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

    public static int integerValueOf(String str) {
        return (int) longValueOf(str);
    }

    public static double doubleValueOf(String str) {
        String[] separateStrings = StringTasks.separateString(str);
        double result = 0.0;
        double integerPart = integerValueOf(separateStrings[0]);
        double fractionalPart = 1.;
        double exponentPart;

        if (str.startsWith("-")) {
            fractionalPart *= -1;
        }

        if (str.contains(".") && str.contains("e")) {
            fractionalPart *= Math.pow(10, -separateStrings[1].length()) * integerValueOf(separateStrings[1]);
            exponentPart = Math.pow(10, integerValueOf(separateStrings[2]));
            result = (integerPart + fractionalPart) * exponentPart;
        } else if (str.contains(".")) {
            fractionalPart *= Math.pow(10, -separateStrings[1].length()) * integerValueOf(separateStrings[1]);
            result = integerPart + fractionalPart;
        } else if (str.contains("e")) {
            exponentPart = Math.pow(10, integerValueOf(separateStrings[1]));
            result = integerPart * exponentPart;
        }
        return result;
    }

    private static String[] separateString(String str) {
        int countOfParts;
        if (str.contains(".") && str.contains("e")) {
            countOfParts = 3;
        } else if (str.contains(".") || str.contains("e")) {
            countOfParts = 2;
        } else {
            return new String[]{str};
        }
        String[] separateStrings = new String[countOfParts];

        if (countOfParts == 3) {
            String[] tmp = str.split("\\.");
            separateStrings[0] = tmp[0];
            tmp = tmp[1].split("e");
            separateStrings[1] = tmp[0];
            separateStrings[2] = tmp[1];
        }

        if (countOfParts == 2 && str.contains(".")) {
            separateStrings = str.split("\\.");
        }

        if (countOfParts == 2 && str.contains("e")) {
            separateStrings = str.split("e");
        }
        return separateStrings;
    }
}
