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
        if (digitString.equals("e")
                || digitString.equals("-")
                || digitString.equals(".")
                || digitString.equals("--")
                || digitString.length() == 0) {
            return null;
        }

        if (eCounts + pointCounts == 0) {
            if (!checkMinuses(minusCounts, digitString)) {
                return null;
            }
            long longResult = Long.valueOf(digitString);
            if (longResult <= Integer.MAX_VALUE && longResult >= Integer.MIN_VALUE) {
                return Integer.valueOf(digitString);
            }
            return longResult;
        }

        if (eCounts == 0) {
            if (!checkMinuses(minusCounts, digitString)) {
                return null;
            }
            if (digitString.charAt(digitString.indexOf('-') + 1) == '.') {
                return null;
            }
            if (digitString.charAt(digitString.length() - 1) == '.') {
                return null;
            }
            return Double.valueOf(digitString);
        }

        if (pointCounts == 0 || pointCounts + eCounts == 2) {
            if (minusCounts == 2) {
                if (digitString.charAt(0) != '-' || digitString.charAt(digitString.indexOf('e') + 1) != '-') {
                    return null;
                }
            }
            if (minusCounts == 1) {
                if (digitString.charAt(0) != '-' && digitString.charAt(digitString.indexOf('e') + 1) != '-') {
                    return null;
                }
            }
            if (digitString.charAt(digitString.indexOf('-') + 1) == 'e' || digitString.indexOf('-') == digitString.length()) {
                return null;
            }
            if (pointCounts == 1) {
                if (digitString.indexOf('.') > digitString.indexOf('e')
                        || digitString.indexOf('.') == 0) {
                    return null;
                }
            }
            return Double.valueOf(digitString);
        }

        return Double.valueOf(digitString);
    }

    public static boolean checkMinuses(int minusCounts, String digitString) {
        if (minusCounts == 2) {
            return false;
        }
        if (minusCounts == 1) {
            int firstMinus = digitString.indexOf('-');
            if (firstMinus > -1 && firstMinus != 0) {
                return false;
            }
        }
        return true;
    }
}
