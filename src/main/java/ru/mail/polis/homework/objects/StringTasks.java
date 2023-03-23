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
        StringBuilder inputString = new StringBuilder(str);
        StringBuilder requireString = new StringBuilder();
        byte occurrenceExponent = 0;
        byte occurrencePoint = 0;
        byte occurrenceMinus = 0;
        boolean mantissa = true;
        boolean minusOpportunity = true;
        boolean needDigit = true;


        for (int i = 0; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);

            if (Character.isDigit(currentChar)) {
                minusOpportunity = false;
                needDigit = false;
            } else if (currentChar == 'e') {
                occurrenceExponent++;
                if (occurrenceExponent == 2 || needDigit) {
                    return null;
                }
                mantissa = false;
                occurrenceMinus = 0;
                minusOpportunity = true;
                needDigit = true;
            } else if (currentChar == '.') {
                occurrencePoint++;
                if ((occurrencePoint == 2) || !mantissa) {
                    return null;
                }
            } else if (currentChar == '-') {
                occurrenceMinus++;
                if (occurrenceMinus == 2 || !minusOpportunity) {
                    return null;
                }
            } else {
                continue;
            }
            requireString.append(currentChar);
        }
        if (needDigit) {
            return null;
        }
        String result = requireString.toString();
        if (occurrencePoint != 0 || occurrenceExponent != 0) {
            return Double.valueOf(result);
        } else {
            long finalNumber = Long.parseLong(result);
            if (finalNumber > Integer.MAX_VALUE || finalNumber < Integer.MIN_VALUE) {
                return finalNumber;
            } else {
                return (int) finalNumber;
            }
        }
    }
}
