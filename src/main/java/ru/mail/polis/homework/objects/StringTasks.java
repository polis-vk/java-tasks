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
        if (str == null || str.isEmpty() || str.charAt(str.length() - 1) == '-'
                || str.charAt(str.length() - 1) == 'e' || str.charAt(str.length() - 1) == '.') {
            return null;
        }
        StringBuilder strBuilder = new StringBuilder(str);
        for (int i = 0; Character.isLetter(strBuilder.charAt(i)) && strBuilder.charAt(i) != 'e'; ) {
            strBuilder.deleteCharAt(i);
        }
        for (int i = 1; i < strBuilder.length() - 1; i++) {
            char previousElem = strBuilder.charAt(i - 1);
            char currentElem = strBuilder.charAt(i);
            char nextElem = strBuilder.charAt(i + 1);
            boolean previousElemIsDigit = Character.isDigit(previousElem);
            if (Character.isLetter(currentElem) && currentElem != 'e') {
                strBuilder.deleteCharAt(i);
                i--;
                continue;
            }
            if (Character.isLetter(nextElem) && nextElem != 'e') {
                strBuilder.deleteCharAt(i + 1);
                i--;
                continue;
            }
            boolean nextElemIsDigit = Character.isDigit(nextElem);
            if ((currentElem == '-') && (previousElem != 'e') && nextElemIsDigit) {
                return null;
            } else if ((currentElem == '.') && !previousElemIsDigit && !nextElemIsDigit) {
                return null;
            } else if ((currentElem == 'e') && !previousElemIsDigit
                    && (!nextElemIsDigit || (nextElem != '-'))) {
                return null;
            }
        }
        if (strBuilder.indexOf("e") != -1 || strBuilder.indexOf(".") != -1) {
            return Double.parseDouble(strBuilder.toString());
        }
        long resultLong = Long.parseLong(strBuilder.toString());
        if (resultLong < Integer.MIN_VALUE || resultLong > Integer.MAX_VALUE) {
            return resultLong;
        }
        return Integer.parseInt(strBuilder.toString());
    }
}
