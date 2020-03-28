package ru.mail.polis.homework.objects;

public class StringTasks {
    public static final int UNICODE_FORMAT = 48;

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
        if (str == null || str.equals("")) {
            return null;
        }

        int indexOfPoint = str.indexOf('.');
        int indexOfExp = str.indexOf('e');
        if ((str.lastIndexOf('.') != indexOfPoint)
                && (str.lastIndexOf('e') != indexOfExp)) {
            return null;
        }

        StringBuilder strBldr = new StringBuilder();
        for (char symbol : str.toCharArray()) {
            if (Character.isDigit(symbol) || (symbol == 'e') || (symbol == '-') || (symbol == '.')) {
                strBldr.append(symbol);
            }
        }
        String digitString = new String(strBldr);

        if ((digitString.indexOf("--") != -1)
                || (digitString.indexOf(".-") != -1)
                || ((digitString.endsWith("-")))
                || (digitString.indexOf("-e") != -1)) {
            return null;
        }

        boolean isRealWithExp = indexOfExp != -1;
        boolean isRealWithPoint = indexOfPoint != -1;

        if (isRealWithExp) {
            String[] twoPartsOfDigit = digitString.split("e", 2);
            if (!isRealWithPoint) {
                return (stringToNumber(twoPartsOfDigit[0]) * Math.pow(10, stringToNumber(twoPartsOfDigit[1])));
            } else {
                return (stringToRealNumber(twoPartsOfDigit[0]) * Math.pow(10, stringToNumber(twoPartsOfDigit[1])));
            }
        } else if (isRealWithPoint) {
            return (stringToRealNumber(digitString));
        } else {
            long longNumber = stringToNumber(digitString);
            if ((longNumber > Integer.MAX_VALUE) || (longNumber < Integer.MIN_VALUE)) {
                return longNumber;
            }
            return (int) longNumber;
        }
    }

    private static long extractDigit(String str, int i, int maxIndex) {
        long longNumber = 0;
        for (; i <= maxIndex; i++) {
            longNumber += (long) ((((byte) str.charAt(i)) - UNICODE_FORMAT) * Math.pow(10, maxIndex - i));
        }
        return longNumber;
    }

    private static long stringToNumber(String str) {
        if (str.startsWith("-")) {
            return ((-1) * extractDigit(str, 1, str.length() - 1));
        } else {
            return extractDigit(str, 0, str.length() - 1);
        }
    }

    private static double stringToRealNumber(String str) {
        int pointPosition = str.indexOf('.');
        if (str.startsWith("-")) {
            return (-1) * (extractDigit(str, 1, pointPosition - 1) + extractDigit(str, pointPosition + 1, str.length() - 1) / Math.pow(10, str.length() - pointPosition));
        } else {
            return (extractDigit(str, 0, pointPosition - 1) + extractDigit(str, pointPosition + 1, str.length() - 1) / Math.pow(10, str.length() - pointPosition - 1));
        }
    }

}


