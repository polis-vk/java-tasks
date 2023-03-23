package ru.mail.polis.homework.objects;

import java.util.Objects;

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
        Number result = null;
        if (Objects.nonNull(str) && !str.isEmpty()) {
            char prevSymbol = ' ';
            boolean hasDot = false;
            boolean hasEpsilon = false;
            boolean isNegative = false;
            boolean hasDigit = false;
            StringBuilder numberBuilder = new StringBuilder();
            for (int currSymbolIndex = 0; currSymbolIndex < str.length(); currSymbolIndex++) {
                char currSymbol = str.charAt(currSymbolIndex);
                if (isValidNumberSymbol(currSymbol)) {
                    if (Character.isDigit(currSymbol)) {
                        hasDigit = true;
                    } else if ((currSymbol == '-') && (prevSymbol != 'e')) {
                        if (isNegative || (numberBuilder.length() != 0)) {
                            return null;
                        }
                        isNegative = true;
                    } else if (currSymbol == '.') {
                        if (hasDot || hasEpsilon) {
                            return null;
                        }
                        hasDot = true;
                    } else if (currSymbol == 'e') {
                        if (hasEpsilon || (numberBuilder.length() == 0)) {
                            return null;
                        }
                        hasEpsilon = true;
                    }
                    prevSymbol = currSymbol;
                    numberBuilder.append(currSymbol);
                }
            }
            if (Character.isDigit(prevSymbol) || ((prevSymbol == '.') && hasDigit)) {
                String resultNumberString = numberBuilder.toString();
                if (hasDot || hasEpsilon) {
                    result = Double.parseDouble(resultNumberString);
                } else {
                    result = Long.valueOf(resultNumberString);
                    if ((result.longValue() <= Integer.MAX_VALUE) && (result.longValue() >= Integer.MIN_VALUE)) {
                        result = Integer.valueOf(resultNumberString);
                    }
                }
            }
        }
        return result;
    }

    private static boolean isValidNumberSymbol(char symbol) {
        boolean result = Character.isDigit(symbol);
        switch (symbol) {
            case '-':
            case '.':
            case 'e':
                result = true;
                break;
        }
        return result;
    }
}
