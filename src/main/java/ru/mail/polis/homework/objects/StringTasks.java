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
    private final static int CHARACTER_RADIX = 10;
    private final static int MAX_ALLOWED_DOT_EXP_COUNT = 1;
    private final static int MAX_ALLOWED_MINUS_COUNT = 2;

    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        str = formatString(str);
        if (str == null) {
            return null;
        }
        return getValue(str);
    }

    private static String formatString(String str) {
        int dotCount = 0;
        int expCount = 0;
        int minusCount = 0;
        StringBuilder tmpStr = new StringBuilder(str);

        for (int i = 0; i < tmpStr.length(); ++i) {
            switch (tmpStr.charAt(i)) {
                case '.':
                    if (++dotCount > MAX_ALLOWED_DOT_EXP_COUNT) {
                        return null;
                    }
                    break;
                case 'e':
                    if (++expCount > MAX_ALLOWED_DOT_EXP_COUNT) {
                        return null;
                    }
                    if (i != 0 && tmpStr.charAt(i - 1) == '-') {
                        return null;
                    }
                    if (i == tmpStr.length() - 1) {
                        return null;
                    }
                    break;
                case '-':
                    if (++minusCount > MAX_ALLOWED_MINUS_COUNT) {
                        return null;
                    }
                    if (i != 0 && (tmpStr.charAt(i - 1) == '-' || tmpStr.charAt(i - 1) != 'e')) {
                        return null;
                    }
                    if (i == tmpStr.length() - 1) {
                        return null;
                    }
                    break;
                default:
                    if (!Character.isDigit(tmpStr.charAt(i))) {
                        tmpStr.deleteCharAt(i);
                        --i;
                    }
            }
        }
        return tmpStr.toString();
    }

    private static Number getValue(String str) {
        int expIndex = str.indexOf('e');
        int dotIndex = str.indexOf('.');

        if (dotIndex != -1 && expIndex != -1) {
            return getExpDotValue(str, dotIndex, expIndex);
        }
        if (expIndex != -1) {
            return getExpValue(str, expIndex);
        }
        if (dotIndex != -1) {
            return getDoubleValue(str, dotIndex);
        }
        return getIntValue(str);
    }

    private static Number getExpValue(String str, int expIndex) {
        return parseValue(str.substring(0, expIndex))
                * Math.pow(10, parseValue(str.substring(expIndex + 1)));
    }

    private static Number getExpDotValue(String str, int dotIndex, int expIndex) {
        return getDoubleValue(str.substring(0, expIndex), dotIndex)
                * Math.pow(10, parseValue(str.substring(expIndex + 1)));
    }

    private static double getDoubleValue(String str, int dotIndex) {
        double beforeFloatingPart = parseValue(str.substring(0, dotIndex));
        double floatingPart = getFloatingPart(str.substring(dotIndex + 1));
        return beforeFloatingPart < 0 ? beforeFloatingPart - floatingPart : beforeFloatingPart + floatingPart;
    }

    private static double getFloatingPart(String str) {
        double exponent = 0.0;
        double rank = Math.pow(10, -str.length());
        for (int i = str.length() - 1; i >= 0; i--, rank *= 10) {
            exponent += Character.digit(str.charAt(i), CHARACTER_RADIX) * rank;
        }
        return exponent;
    }

    private static double parseValue(String str) {
        double result = 0;
        long rank = 1;
        for (int i = str.length() - 1; i >= 0; i--, rank *= 10) {
            if (str.charAt(i) == '-') {
                result *= -1;
                continue;
            }
            result += Character.digit(str.charAt(i), CHARACTER_RADIX) * rank;
        }
        return result;
    }

    private static Number getIntValue(String str) {
        return convertToIntValue(parseValue(str));
    }

    private static Number convertToIntValue(double value) {
        long result = (long) value;
        if (result <= Integer.MAX_VALUE && result >= Integer.MIN_VALUE) {
            return (int) result;
        }
        return result;
    }
}
