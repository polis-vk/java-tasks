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

    private static final char[] SYMBOLS = { '-', '.', 'e' };

    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        int dotCount = str.length() - str.replace(".", "").length();
        int expCount = str.length() - str.replace("e", "").length();
        int minusCount = str.length() - str.replace("-", "").length();
        if (dotCount > 1 || expCount > 1 || minusCount > 2) {
            return null;
        }

        // Избавляемся от лишних символов
        StringBuilder strBuilder = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (containChar(ch, SYMBOLS) || Character.isDigit(ch)) {
                strBuilder.append(ch);
            }
        }
        String formattedString = strBuilder.toString();

        // Проверка недопустимых комбинаций символов
        if (formattedString.contains("--") || formattedString.contains("-e") || formattedString.contains(".e") ||
                formattedString.contains("e.") || formattedString.endsWith("-") ||
                formattedString.endsWith("e") || formattedString.startsWith(".")) {
            return null;
        }

        //
        if (dotCount + expCount > 0) {
            return parseDouble(formattedString);
        }

        Double result = parseIntNumber(formattedString);
        if (result <= Integer.MAX_VALUE && result >= Integer.MIN_VALUE) {
            return result.intValue();
        }
        if (result <= Long.MAX_VALUE && result >= Long.MIN_VALUE) {
            return result.longValue();
        }
        return result;
    }

    // Поиск символа в массиве
    private static boolean containChar(char symbol, char[] src) {
        for (char ch : src) {
            if (ch == symbol) {
                return true;
            }
        }
        return false;
    }

    // Преобразование строки в Double
    private static Double parseDouble(String str) {
        int expIndex = str.indexOf('e');
        int dotIndex = str.indexOf('.');

        String power;
        String mantissa;
        if (expIndex != -1 && dotIndex != -1) {
            if (dotIndex > expIndex) {
                return null;
            }
            power = str.substring(expIndex + 1);
            mantissa = str.substring(0, expIndex);
            Double mant = parseDoubleWithDot(mantissa);
            return mant * Math.pow(10, parseIntNumber(power));
        } else if (expIndex != -1) {
            power = str.substring(expIndex + 1);
            mantissa = str.substring(0, expIndex);
            Double mant = parseIntNumber(mantissa);
            return mant * Math.pow(10, parseIntNumber(power));
        } else if (dotIndex != -1) {
            return parseDoubleWithDot(str);
        }
        return null;
    }

    // Вспомогательный метод для метода parseDouble()
    // Данный метод работает с числами, у которых есть точка
    private static Double parseDoubleWithDot(String str) {
        int dotIndex = str.indexOf('.');
        String intMantissa = str.substring(0, dotIndex);
        String fractMantissa = str.substring(dotIndex + 1, str.length());
        Double whole = parseIntNumber(intMantissa);
        Double fract = parseIntNumber(fractMantissa);
        while (fract > 1) {
            fract /= 10;
        }
        return whole + fract;
    }

    // Вспомогательный метод для метода parseDouble()
    // Данный метод работает с числами без точки
    private static Double parseIntNumber(String str) {
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return null;
        }
        int minusIndex = str.indexOf('-');
        if (minusIndex > 0) {
            return null;
        }
        if (minusIndex == 0 && str.length() == 1) {
            return null;
        }

        Double result = 0.;
        for (char ch : str.toCharArray()) {
            if (ch != '-') {
                result = result * 10 + Character.getNumericValue(ch);
            }
        }
        if (minusIndex == 0) {
            result = -result;
        }
        return result;
    }
}
