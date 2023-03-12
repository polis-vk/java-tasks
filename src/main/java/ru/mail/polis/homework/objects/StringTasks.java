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
        if (str == null || str.length() == 0) {
            return null;
        }
        StringBuilder resultNumber = new StringBuilder();
        int strLength = str.length();
        char newChar;
        for (int i = 0; i < strLength; i++) {
            newChar = str.charAt(i);
            if (Character.isDigit(newChar) || newChar == 'e' || newChar == '-' || newChar == '.') {
                resultNumber.append(newChar);
            }
        }
        try {
            return Integer.valueOf(resultNumber.toString());
        } catch (NumberFormatException ignored) {
        }
        try {
            return Long.valueOf(resultNumber.toString());
        } catch (NumberFormatException ignored) {
        }
        try {
            return Double.valueOf(resultNumber.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
