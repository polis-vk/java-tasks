package ru.mail.polis.homework.objects;

public class StringTasks {

    public static long stringToNumber(String str) {
        long longNumber = 0;
        if (str.startsWith("-")) {
            for (int i = 1; i <= str.length() - 1; i++) {
                longNumber += (long) ((((byte) str.charAt(i)) - 48) * Math.pow(10, str.length() - i - 1));
            }
            longNumber = longNumber * (-1);
        } else {
            for (int i = 0; i <= str.length() - 1; i++) {
                longNumber += (long) ((((byte) str.charAt(i)) - 48) * Math.pow(10, str.length() - i - 1));
            }
        }
        return longNumber;
    }

    public static double stringToRealNumber(String str) { /* почему не работает String.split(".",2) ? Искажается первый элемент массива,
                                                           а второй элемент включает в себя точку */
        double doubleNumber = 0;
        if (str.startsWith("-")) {
            for (int i = 1; i <= str.indexOf('.') - 1; i++) {
                doubleNumber += ((((byte) str.charAt(i)) - 48) * Math.pow(10, str.length() - i - 1));
            }
            for (int i = str.indexOf('.') + 1; i < str.length() - 1; i++) {
                doubleNumber += ((((byte) str.charAt(i)) - 48) * Math.pow(10, -(i - str.indexOf('.'))));
            }
            doubleNumber = doubleNumber * (-1);
        } else {
            for (int i = 0; i <= str.indexOf('.') - 1; i++) {
                doubleNumber += ((((byte) str.charAt(i)) - 48) * Math.pow(10, str.indexOf('.') - i - 1));
            }
            for (int i = str.indexOf('.') + 1; i <= str.length() - 1; i++) {
                doubleNumber += ((((byte) str.charAt(i)) - 48) * Math.pow(10, -(i - str.indexOf('.'))));
            }
        }
        return doubleNumber;
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
        if (str == null || str.equals("")) {
            return null;
        }

        if ((str.lastIndexOf('.') != str.indexOf('.'))
                && (str.lastIndexOf('e') != str.indexOf('e'))) {
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

        if ((digitString.indexOf('.') == -1) && (digitString.indexOf('e') == -1)) {
            long longNumber = stringToNumber(digitString);
            if ((longNumber > Integer.MAX_VALUE) || (longNumber < Integer.MIN_VALUE)) {
                return longNumber;
            }
            return (int) longNumber;
        }

        if (digitString.indexOf('e') != -1) {
            String firstPart = digitString.split("e", 2)[0];
            String secondPart = digitString.split("e", 2)[1];
            if (digitString.indexOf(".") == -1) {
                return (stringToNumber(firstPart) * Math.pow(10, stringToNumber(secondPart)));
            } else {
                return (stringToRealNumber(firstPart) * Math.pow(10, stringToNumber(secondPart)));
            }
        } else {
            return (stringToRealNumber(digitString));
        }
    }
}


