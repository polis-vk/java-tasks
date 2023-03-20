package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валидино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Можно использовать функции типа Double.valueOf()
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     * БЕЗ РЕГУЛЯРОК!
     * 6 тугриков
     */
    public static Number valueOf(String str) {
        if (str == null || str.equals(""))
            return null;
        Character value;

        for (int i = 0; i < str.length(); i++) {
            value = str.charAt(i);
            if (value != '-' && value != '.' && (value > '9' || value < '0') && value != 'e') {
                str = str.replace(value.toString(), "");
                i = 0;
            }
        }
        for (int i = 0; i < str.length() - 1; i++) {
            value = str.charAt(i);
            if (value == '-' && str.charAt(i + 1) == '-')
                return null;

            if (value != 'e' && str.charAt(i + 1) == '-')
                return null;
            if (str.charAt(str.length() - 1) == 'e')
                return null;
        }

        return turn_into_number(str);
    }

    private static Number turn_into_number(String str) {
        if (str.contains("e")) {
            return Double.valueOf(str);
        }
        int length = str.length();
        double number = 0;
        boolean is_negative = false;
        boolean decimal = false;
        int decimalPoint = 0;
        for (int i = 0; i < length; i++) {
            if (str.charAt(i) == '-') {
                is_negative = true;
                continue;
            }
            if (str.charAt(i) == '.') {
                decimal = true;
                decimalPoint = i;
                number /= 100;
                continue;
            }
            if (decimal) {
                number += (str.charAt(i) - '0') * Math.pow(0.1, i - decimalPoint);
            } else
                number += (str.charAt(i) - '0') * Math.pow(10, length - i - 1);

        }
        if (is_negative)
            number = -1 * number;

        if (!str.contains(".") && !str.contains("e")) {
            if ((number >= -2147483648 && number <= 0) || (number > 0 && number <= 2147483647))
                return (int) number;
            return (long) number;
        }
        return number;
    }
}
