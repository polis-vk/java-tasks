package ru.mail.polis.homework.objects;

import static java.lang.Character.isLetter;

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

        String stringCopy = str;
        stringCopy = removeAllLettersExceptE(stringCopy);

        if (stringCopy.matches("[-]?[0-9]+[.]?[0-9]*[e][-][0-9]+")
                || stringCopy.matches("[-]?[0-9][e][0-9]+")
                || stringCopy.matches("[-]?[0-9]*[.]?[0-9]+")) {
            return stringToNumber(stringCopy);
        }

        return null;
    }

    private static String removeAllLettersExceptE(String string) {
        for (int i = 0; i < string.length(); i++) {
            char symbol = string.charAt(i);

            if (isLetter(symbol) && symbol != 'e') {
                String symbolString = Character.toString(symbol);
                string = string.replace(symbolString, "");
                i--;
            }
        }

        return string;
    }

    private static Number stringToNumber(String string) {
        int eCount = string.length() - string.replace("e", "").length();
        int pointCount = string.length() - string.replace(".", "").length();

        if (eCount == 0 && pointCount == 0) {
            long longResult = stringToLong(string);
            int intResult = stringToInteger(string);

            if (longResult - intResult == 0) {
                return intResult;
            } else {
                return longResult;
            }
        }

        if (eCount == 0 && pointCount == 1) {
            return stringToDouble(string);
        }

        if (eCount != 0) {
            double beforeE;
            String[] stringSplitByE = string.split("e");

            if (pointCount != 0) {
                beforeE = stringToDouble(stringSplitByE[0]);
            } else {
                beforeE = stringToInteger(stringSplitByE[0]);
            }

            double afterE = stringToInteger(stringSplitByE[1]);
            return beforeE * Math.pow(10, afterE);
        }

        return null;
    }

    private static double stringToDouble(String string) {
        String[] stringSplitByPoint = string.split("\\.");
        double beforePoint = stringToInteger(stringSplitByPoint[0]);
        double afterPoint = getNumberFromFraction(stringSplitByPoint[1]);

        return beforePoint + afterPoint;
    }

    private static double getNumberFromFraction(String string) {
        double power = 1;
        double resultNumber = 0;

        for (int i = 0; i < string.length(); i++) {
            resultNumber += charToNumber(string.charAt(i)) * Math.pow(0.1, power++);
        }

        return resultNumber;
    }

    private static long stringToLong(String string) {
        int power = 0;
        long resultNumber = 0;

        if (string.charAt(0) == '-') {
            for (int i = string.length() - 1; i > 0; i--) {
                resultNumber -= charToNumber(string.charAt(i)) * Math.pow(10, power++);
            }
        } else {
            for (int i = string.length() - 1; i >= 0; i--) {
                resultNumber += charToNumber(string.charAt(i)) * Math.pow(10, power++);
            }
        }

        return resultNumber;
    }

    private static int stringToInteger(String string) {
        int power = 0;
        int resultNumber = 0;

        if (string.charAt(0) == '-') {
            for (int i = string.length() - 1; i > 0; i--) {
                resultNumber -= charToNumber(string.charAt(i)) * Math.pow(10, power++);
            }
        } else {
            for (int i = string.length() - 1; i >= 0; i--) {
                resultNumber += charToNumber(string.charAt(i)) * Math.pow(10, power++);
            }
        }

        return resultNumber;
    }

    private static double charToNumber(char digit) {
        switch (digit) {
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
                throw new IllegalArgumentException();
        }
    }
}
