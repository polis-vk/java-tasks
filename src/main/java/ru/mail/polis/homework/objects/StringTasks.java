package ru.mail.polis.homework.objects;

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
        if (str == null || str.equals("") || str.indexOf('.') != str.lastIndexOf('.') || str.indexOf('e') != str.lastIndexOf('e')) {
            return null;
        }
        String newStr = "";
        char current;
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            current = str.charAt(i);
            if (Character.isDigit(current) || current == '-' || current == '.' || current == 'e') {
                newStr = newStr + current;
                if (current == '-') {
                    count++;
                }
                if (count > 2) {
                    return null;
                }
            }
        }
        if (newStr.contains("--")) {
            return null;
        }
        if (newStr.indexOf('-') != -1 && newStr.indexOf('-') != 0 && newStr.indexOf('-') - 1 != newStr.indexOf('e')) {
            return null;
        }
        if (newStr.contains("e")) {
            if (newStr.indexOf('e') < newStr.indexOf('.') || newStr.indexOf('e') == newStr.length() - 1) {
                return null;
            } else {
                return ToDouble(newStr);
            }
        }
        if (newStr.contains(".")) {
            return ToDouble(newStr);
        }
        long l = ToLong(newStr);
        if (l <= Integer.MAX_VALUE && l >= Integer.MIN_VALUE) {
            return ((int) l);
        }
        return l;
    }

    private static long ToLong(String str) {
        long number = 0;
        for (int i = 0; i < str.length(); i++) {
            number = number * 10;
            for (int j = 0; j < 10; j++) {
                if ((int) str.charAt(i) == j + 48) {
                    number = (number + j);
                }
            }
        }
        if (str.charAt(0) == '-') {
            number = -number;
        }
        return number;
    }

    private static double ToDouble(String str) {
        double number = 0;
        int i = 0;
        while (i < str.length() && str.charAt(i) != 'e' ) {
            for (int j = 0; j < 10; j++) {
                if ((int) str.charAt(i) == j + 48) {
                    number = number * 10 + j;
                }
            }
            i++;
        }
        if (str.charAt(0) == '-') {
            number = -number;
        }
        if (str.contains(".")) {
            int j = str.indexOf('.');
            while (j < str.length() && str.charAt(j) != 'e') {
                j++;
            }
            j = j - str.indexOf('.') - 1;
            number = number / Math.pow(10, j);
        }
        if (i < str.length() && str.charAt(i) == 'e') {
            int pow = 0;
            for (int k = i + 1; k < str.length(); k++) {
                pow = pow * 10;
                for (int j = 0; j < 10; j++) {
                    if ((int) str.charAt(k) == j + 48) {
                        pow = (pow + j);
                    }
                }
            }
            if (str.charAt(i + 1) == '-') {
                number = number / Math.pow(10, pow);
            } else {
                number = number * Math.pow(10, pow);
            }
        }
        return number;
    }
}
