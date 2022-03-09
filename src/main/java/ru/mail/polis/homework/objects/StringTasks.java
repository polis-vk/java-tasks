package ru.mail.polis.homework.objects;

import java.util.Arrays;

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
        if (str == null || str.equals("")) {
            return null;
        }
        String strFiltered = str.replaceAll("[^0-9-.e]", "");
        if (!strFiltered.matches("-?[0-9]+(\\.?[0-9]+)?(e?-?[0-9]+)?")) {
            return null;
        }
        char[] charArray = strFiltered.toCharArray();
        if (strFiltered.contains(".") || strFiltered.contains("e")) {
            return charArrayToDouble(charArray, strFiltered.indexOf("."), strFiltered.indexOf("e"));
        } else if (isLong(charArray)) {
            return charArrayToLong(charArray);
        } else {
            return charArrayToInt(charArray);
        }
    }

    private static boolean isLong(char[] array) {
        char[] sample = new char[]{'2', '1', '4', '7', '4', '8', '3', '6', '4', '7'};
        int startDigitNum = 0;
        if (array[0] == '-') {
            startDigitNum = 1;
            sample = new char[]{'2', '1', '4', '7', '4', '8', '3', '6', '4', '8'};
        }
        int arrayLength = array.length;
        int sampleLength = sample.length;
        if (arrayLength - startDigitNum > sampleLength) {
            return true;
        } else if (arrayLength - startDigitNum < sampleLength) {
            return false;
        }
        for (int i = startDigitNum; i < arrayLength; i++) {
            if (array[i] > sample[i - startDigitNum]) {
                return true;
            }
        }
        return false;
    }

    private static Double charArrayToDouble(char[] array, int dotPosition, int ePosition) {
        int startDigitNum = 0;
        boolean isNegative = false;
        if (array[0] == '-') {
            isNegative = true;
            startDigitNum = 1;
        }
        long wholePart;
        long fractionalPart = 0;
        int fractionalPartLength = 0;
        long degreeIndicator = 0;
        int arrayLength = array.length;
        if (ePosition != -1) {
            if (dotPosition != -1) {
                wholePart = charArrayToLong(Arrays.copyOfRange(array, startDigitNum, dotPosition));
                fractionalPartLength = ePosition - dotPosition - 1;
                int fractionalPartLastDigitNum = ePosition - 1;
                if (fractionalPartLength > 18) {
                    fractionalPartLastDigitNum = dotPosition + 18;
                    fractionalPartLength = 18;
                }
                fractionalPart = charArrayToLong(Arrays.copyOfRange(array, dotPosition + 1, fractionalPartLastDigitNum + 1));
            } else {
                wholePart = charArrayToLong(Arrays.copyOfRange(array, startDigitNum, ePosition));
            }
            boolean isDegreeNegative = false;
            int startDegreeDigitNum = ePosition + 1;
            if (array[ePosition + 1] == '-') {
                isDegreeNegative = true;
                startDegreeDigitNum += 1;
            }
            degreeIndicator = charArrayToLong(Arrays.copyOfRange(array, startDegreeDigitNum, arrayLength));
            degreeIndicator = isDegreeNegative ? -degreeIndicator : degreeIndicator;
        } else {
            wholePart = charArrayToLong(Arrays.copyOfRange(array, startDigitNum, dotPosition));
            fractionalPartLength = arrayLength - dotPosition - 1;
            int fractionalPartLastDigitNum = arrayLength - 1;
            if (fractionalPartLength > 18) {
                fractionalPartLastDigitNum = dotPosition + 18;
                fractionalPartLength = 18;
            }
            fractionalPart = charArrayToLong(Arrays.copyOfRange(array, dotPosition + 1, fractionalPartLastDigitNum + 1));
        }
        double result = (wholePart + fractionalPart / Math.pow(10, fractionalPartLength)) * Math.pow(10, degreeIndicator);
        return isNegative ? -result : result;
    }

    private static Integer charArrayToInt(char[] array) {
        int startDigitNum = 0;
        boolean isNegative = false;
        if (array[0] == '-') {
            isNegative = true;
            startDigitNum = 1;
        }
        int result = 0;
        int arrayLength = array.length;
        for (int i = startDigitNum; i < arrayLength; i++) {
            int digit = array[i] - '0';
            result *= 10;
            result += digit;
        }
        return isNegative ? -result : result;
    }

    private static Long charArrayToLong(char[] array) {
        int startDigitNum = 0;
        boolean isNegative = false;
        if (array[0] == '-') {
            isNegative = true;
            startDigitNum = 1;
        }
        long result = 0;
        int arrayLength = array.length;
        for (int i = startDigitNum; i < arrayLength; i++) {
            int digit = array[i] - '0';
            result *= 10;
            result += digit;
        }
        return isNegative ? -result : result;
    }
}
