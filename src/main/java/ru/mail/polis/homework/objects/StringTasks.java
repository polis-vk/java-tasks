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
        StringBuilder copiedStr = new StringBuilder();
        int counterPoint = 0;
        int counterE = 0;
        int counterCopiedStr = 0;
        boolean isPoint = false;
        boolean isE = false;
        for (int counter = 0; counter < str.length(); counter++) {
            if (Character.isDigit(str.charAt(counter))) {
                copiedStr.append(str.charAt(counter));
                counterCopiedStr++;
            }
            if (str.charAt(counter) == '.') {
                isPoint = true;
                counterPoint++;
                if (counterCopiedStr == 0) {
                    return null;
                }
                if (counterPoint >= 2) {
                    return null;
                }
                if (counter == str.length() - 1) {
                    return null;
                }
                if (!Character.isDigit(copiedStr.charAt(counterCopiedStr - 1))) {
                    return null;
                }
                copiedStr.append(str.charAt(counter));
                counterCopiedStr++;
            }
            if (str.charAt(counter) == 'e') {
                isE = true;
                counterE++;
                if (counterCopiedStr == 0) {
                    return null;
                }
                if (!Character.isDigit(copiedStr.charAt(counterCopiedStr - 1)) || counterE >= 2) {
                    return null;
                }
                if (counter == str.length() - 1) {
                    return null;
                }
                copiedStr.append(str.charAt(counter));
                counterCopiedStr++;
            }
            if (str.charAt(counter) == '-') {
                if (counterCopiedStr != 0) {
                    if (copiedStr.charAt(counterCopiedStr - 1) != 'e') {
                        return null;
                    }
                    if (counter == str.length() - 1) {
                        return null;
                    }
                }
                copiedStr.append(str.charAt(counter));
                counterCopiedStr++;
            }
        }
        String result = copiedStr.toString();
        if (!result.isEmpty()) {
            if (isE || isPoint) {
                return Double.valueOf(result);
            }
            if (Long.valueOf(result) > (long) Integer.MAX_VALUE || Long.valueOf(result) < (long) Integer.MIN_VALUE) {
                return Long.valueOf(result);
            }
            return Integer.valueOf(result);
        }
        return null;
    }
}
