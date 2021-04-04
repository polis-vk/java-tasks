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
        if (str == null || str.isEmpty()) {
            return null;
        }
        int expCount = numberOfChar('e', str);
        int dotCount = numberOfChar('.', str);
        if (dotCount > 1 || expCount > 1 || numberOfChar('-', str) > 2) {
            return null;
        }
        String eNotation = toENotation(str);
        if (eNotation == null) {
            return null;
        }
        if (expCount + dotCount == 0) {
            long result = (long) toNumber(eNotation);
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                return result;
            } else {
                return (int) result;
            }
        }
        return toDouble(eNotation);
    }

    private static int numberOfChar(char requiredSymbol, String str) {
        int count = 0;
        for (char symbol : str.toCharArray()) {
            if (symbol == requiredSymbol) {
                count++;
            }
        }
        return count;
    }

    private static String toENotation(String str) {
        StringBuilder correctsSymbols = new StringBuilder();
        for (char symbol : str.toCharArray()) {
            if (Character.isDigit(symbol) ||
                    symbol == '-' ||
                    symbol == 'e' ||
                    symbol == '.') {
                correctsSymbols.append(symbol);
            }
        }
        String eNotation = correctsSymbols.toString();
        if (eNotation.contains("--")
                || eNotation.contains("-e")
                || eNotation.contains(".e")
                || eNotation.contains("e.")
                || eNotation.contains("-.")
                || eNotation.contains(".-")
                || eNotation.endsWith("-")
                || eNotation.endsWith("e")
                || eNotation.endsWith(".")
                || eNotation.startsWith("e")
                || eNotation.startsWith(".")) {
            return null;
        }
        return eNotation;
    }

    private static double toNumber(String str) {
        int i = str.charAt(0) == '-' ? 1 : 0;
        double result = str.charAt(i) - '0';
        for (i++; i < str.length(); i++) {
            result = result * 10 + (str.charAt(i) - '0');
        }
        return result * (str.charAt(0) == '-' ? -1 : 1);
    }

    private static double toDouble(String str) {
        int ePosition = str.indexOf("e");
        int dotPosition = str.indexOf(".");
        double exp = ePosition > 0 ? toNumber(str.substring(ePosition + 1)) : 0;
        double mantissa;
        if (dotPosition > 0) {
            String floorAndDecimal = str.substring(0, ePosition > 0 ? ePosition : str.length());
            double floor = toNumber(floorAndDecimal.substring(0, dotPosition));
            double decimal = toNumber(floorAndDecimal.substring(dotPosition + 1));
            decimal = decimal / Math.pow(10, ((int) Math.log10(decimal) + 1));
            mantissa = floor < 0 ? -1 * (-1 * floor + decimal) : floor + decimal;
        } else {
            mantissa = toNumber(str.substring(0, ePosition));
        }
        return mantissa * Math.pow(10, exp);
    }
}
