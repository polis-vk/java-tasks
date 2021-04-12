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
    private static final int REPETITION_NUMBER = 2;
    private static final int RADIX = 10;

    public static Number valueOf(String str) {
        int countDot = 0;
        int countE = 0;
        int countMinus = 0;
        StringBuilder newStr = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char character = str.charAt(i);
            switch (character) {
                case 'e':
                    newStr.append(character);
                    countE++;
                    break;
                case '-':
                    newStr.append(character);
                    countMinus++;
                    break;
                case '.':
                    newStr.append(character);
                    countDot++;
                    break;
                default:
                    newStr.append(Character.isDigit(character) ? character : "");
            }
        }
        String numberStr = newStr.toString();

        if (countDot >= REPETITION_NUMBER || countE >= REPETITION_NUMBER || countMinus > REPETITION_NUMBER ||
                numberStr.contains("-e") || numberStr.contains("--") || numberStr.startsWith(".") ||
                numberStr.startsWith("e") || numberStr.endsWith("-") || numberStr.endsWith("e")) {
            return null;
        }
        return toNumber(numberStr);
    }

    private static Number toNumber(String str) {
        int dotPlace = str.indexOf('.');
        int expPlace = str.indexOf('e');
        if (dotPlace != -1 && expPlace != -1) {
            return (double) toDouble(str.substring(0, expPlace), dotPlace)
                    * Math.pow(10, countIntegerPart(str, str.length() - 1, expPlace + 1));
        }
        if (expPlace != -1) {
            return countIntegerPart(str, expPlace - 1, 0) *
                    Math.pow(10, countIntegerPart(str, str.length() - 1, expPlace + 1));
        }
        if (dotPlace != -1) {
            return toDouble(str, dotPlace);
        }

        return toInt(countIntegerPart(str, str.length() - 1, 0));
    }

    private static double countIntegerPart(String str, int start, int end) {
        double number = 0;
        long rank = 1;
        for (int i = start; i >= end; --i, rank *= 10) {
            if (str.charAt(i) == '-') {
                number *= -1;
                continue;
            }
            number += Character.digit(str.charAt(i), RADIX) * rank;
        }
        return number;
    }

    private static Number toInt(double num) {
        long number = (long) num;
        if (number <= Integer.MAX_VALUE && number >= Integer.MIN_VALUE) {
            return (int) number;
        }
        return number;
    }

    private static Number toDouble(String str, int dotPlace) {
        return countIntegerPart(str, dotPlace - 1, 0) + countAfterDot(str, dotPlace);
    }

    private static double countAfterDot(String str, int dotPlace) {
        double exp = 0.0;
        double rank = Math.pow(10, dotPlace - str.length() + 1);
        for (int i = str.length() - 1; i > dotPlace; --i, rank *= 10) {
            exp += Character.digit(str.charAt(i), RADIX) * rank;
        }
        return exp;
    }
}
