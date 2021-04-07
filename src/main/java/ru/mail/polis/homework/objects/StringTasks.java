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
    private static final char[] ALLOWED_CHAR = {'-', '.', 'e'};

    public static Number valueOf(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        int eCount = 0;
        int dotCount = 0;
        int minusCount = 0;

        StringBuilder stringWithAllowedSymbol = new StringBuilder();
        for (char symbol : str.toCharArray()) {
            if (isContains(symbol, ALLOWED_CHAR) || Character.isDigit(symbol)) {
                stringWithAllowedSymbol.append(symbol);
            }
        }

        int index = 0;
        while (index < stringWithAllowedSymbol.length()) {
            switch (stringWithAllowedSymbol.charAt(index)) {
                case 'e':
                    eCount++;
                    if (eCount > 1) {
                        return null;
                    }
                    break;
                case '.':
                    dotCount++;
                    if (dotCount > 1) {
                        return null;
                    }
                    break;
                case '-':
                    minusCount++;
                    if (minusCount > 2) {
                        return null;
                    }
                    break;
            }
            index++;
        }

        String numberString = stringWithAllowedSymbol.toString();

        if (numberString.contains("-e") || numberString.contains(".e")
                || numberString.contains("e.") || numberString.contains("--")
                || numberString.endsWith("-") || numberString.endsWith("e")
                || numberString.startsWith(".") || numberString.startsWith("e")) {
            return null;
        }

        if (eCount > 0 || dotCount > 0) {
            return stringToDouble(numberString);
        }

        Double number = parseIntegerPart(numberString);

        if (number > Integer.MAX_VALUE || number < Integer.MIN_VALUE) {
            return number.longValue();
        }
        return number.intValue();
    }

    private static boolean isContains(char ch, char[] allowedSymbols) {
        for (char symbol : allowedSymbols) {
            if (ch == symbol) {
                return true;
            }
        }
        return false;
    }


    private static Double stringToDouble(String str) {
        int eIndex = str.indexOf('e');
        int dotIndex = str.indexOf('.');

        if (eIndex != -1 && dotIndex != -1 && dotIndex > eIndex) {
            return null;
        }

        if (eIndex == -1) {
            return toDoubleWithDot(str);
        }

        Double mantissa;
        Double exp = parseIntegerPart(str.substring(eIndex + 1));

        if (dotIndex != -1) {
            mantissa = toDoubleWithDot(str.substring(0, eIndex));
        } else {
            mantissa = parseIntegerPart(str.substring(0, eIndex));
        }

        return mantissa * Math.pow(10, exp);
    }

    private static Double toDoubleWithDot(String mantissaWithDot) {
        int dotIndex = mantissaWithDot.indexOf('.');

        String integerPart = mantissaWithDot.substring(0, dotIndex);
        String fractionalPart = mantissaWithDot.substring(dotIndex + 1);
        Double intNumberPart = parseIntegerPart(integerPart);
        Double fractionalNumberPart = parseIntegerPart(fractionalPart);

        fractionalNumberPart *= Math.pow(10, -(fractionalPart.length()));
        return intNumberPart + fractionalNumberPart;
    }

    private static Double parseIntegerPart(String number) {
        int minusIndex = number.indexOf('-');

        if (minusIndex == 0 && number.length() == 1) {
            return null;
        }

        if (minusIndex > 0) {
            return null;
        }

        double resultNumber = 0.0;

        for (char symbol : number.toCharArray()) {
            if (symbol != '-') {
                resultNumber = resultNumber * 10 + Character.getNumericValue(symbol);
            }
        }

        if (minusIndex == 0) {
            resultNumber *= -1;
        }

        return resultNumber;
    }
}
