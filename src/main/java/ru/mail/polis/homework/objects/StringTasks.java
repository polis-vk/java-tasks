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
        
        int[] countOfAllowedCharacters = {0, 0, 0};

        StringBuilder inputStr = new StringBuilder(str);
        int i = 0;
        while (i < inputStr.length()) {
            switch (inputStr.charAt(i)) {
                case '.':
                    if (countOfAllowedCharacters[0]++ >= 1) {
                        return null;
                    }
                    break;
                case 'e':
                    if (countOfAllowedCharacters[1]++ >= 1) {
                        return null;
                    }
                    break;
                case '-':
                    if (countOfAllowedCharacters[2]++ >= 2) {
                        return null;
                    }
                    break;
                default:
                    if (!Character.isDigit(inputStr.charAt(i))) {
                        inputStr.delete(i, i + 1);
                        i--;
                    }
            }
            i++;
        }

        for (int j = 1; j < inputStr.length(); j++) {
            if (inputStr.charAt(j) == '-' && inputStr.charAt(j - 1) != 'e') {
                return null;
            }
        }

        if (countOfAllowedCharacters[0] != 0 || countOfAllowedCharacters[1] != 0) {
            return parseDouble(inputStr.toString());
        }

        Long longNumber = parseLong(inputStr.toString());

        if (longNumber == null) {
            return parseDouble(inputStr.toString());
        }
        if (longNumber > Integer.MAX_VALUE || longNumber < Integer.MIN_VALUE) {
            return longNumber;
        }

        return longNumber.intValue();
    }

    private static Long parseLong(String str) {
        double longNumber = parseLongHowDouble(str);
        if (longNumber> Long.MAX_VALUE) {
            return null;
        }
        return (long) longNumber;
    }

    private static double parseLongHowDouble(String str) {
        char[] charArray = str.toCharArray();
        int i = 0;
        double doubleNumber = Character.getNumericValue((charArray[i] == '-') ?
                charArray[++i] : charArray[i]);
        i++;

        while (i < charArray.length) {
            doubleNumber = doubleNumber * 10 + Character.getNumericValue(charArray[i++]);
        }

        return (charArray[0] == '-') ? -doubleNumber : doubleNumber;
    }

    private static Double parseDouble(String str) {
        int dotIndex = str.indexOf('.');
        int eIndex = str.indexOf('e');
        
        if (eIndex == str.length() - 1) {
            return null;
        }

        if (dotIndex != -1 && eIndex != -1) {
            String fractionalPartOfNumber = str.substring(dotIndex + 1, eIndex);
            return (parseLongHowDouble(str.substring(0, dotIndex)) +
                    parseLongHowDouble(fractionalPartOfNumber) /
                            Math.pow(10, fractionalPartOfNumber.length())) *
                    Math.pow(10, parseLongHowDouble(str.substring(eIndex + 1)));
        }

        if (dotIndex == -1 && eIndex != -1) {
            return parseLongHowDouble(str.substring(0, eIndex)) * 
                    Math.pow(10, parseLongHowDouble(str.substring(eIndex + 1)));
        }

        if (dotIndex != -1) {
            String fractionalPartOfNumber = str.substring(dotIndex + 1);
            return parseLongHowDouble(str.substring(0, dotIndex)) +
                    parseLongHowDouble(fractionalPartOfNumber) / Math.pow(10, fractionalPartOfNumber.length());
        }
        
        return null;
    }
}
