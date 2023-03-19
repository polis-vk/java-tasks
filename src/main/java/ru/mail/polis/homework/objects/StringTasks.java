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
        if (str == null || str.isEmpty()) {
            return null;
        }

        String number = representNumeric(str);
        if (number == null) {
            return null;
        }

        return convertToNumber(number);
    }

    private static String representNumeric(String str) {
        StringBuilder number = new StringBuilder();
        boolean metExp = false;
        boolean metPoint = false;
        for (char ch : str.toCharArray()) {
            if (Character.isDigit(ch)) {
                number.append(ch);
            } else if (ch == 'e') {
                if (metExp) {
                    return null;
                }
                number.append(ch);
                metExp = true;
            } else if (ch == '.') {
                if (metPoint || metExp) {
                    return null;
                }
                number.append(ch);
                metPoint = true;
            } else if (ch == '-') {
                if (number.length() != 0 && number.charAt(number.length() - 1) != 'e') {
                    return null;
                }
                number.append(ch);
            }
        }

        if (!Character.isDigit(number.charAt(number.length() - 1))) {
            return null;
        }
        if (metExp || metPoint) {
            number.append('D');
        }
        return number.toString();
    }

    private static Number convertToNumber(String numeric) {
        if (numeric.charAt(numeric.length() - 1) == 'D') {
            return Double.parseDouble(numeric);
        }
        Long converted = Long.valueOf(numeric);
        if (converted > Integer.MAX_VALUE || converted < Integer.MIN_VALUE) {
            return converted;
        }
        return converted.intValue();
    }
}
