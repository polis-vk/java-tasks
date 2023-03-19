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
        StringBuilder copiedStr = new StringBuilder(str);
        int counterPoint = 0;
        int counterE = 0;
        boolean isPoint = false;
        boolean isE = false;
        for (int counter = 0; counter < copiedStr.length(); counter++) {
            while (counter != copiedStr.length() && Character.isLetter(copiedStr.charAt(counter)) && copiedStr.charAt(counter) != 'e') {
                copiedStr.deleteCharAt(counter);
            }
            if (counter == copiedStr.length()) {
                if (copiedStr.charAt(counter - 1) == 'e' || copiedStr.charAt(counter - 1) == '.' || copiedStr.charAt(counter - 1) == '-') {
                    return null;
                }
                break;
            }
            if (copiedStr.charAt(counter) == '.') {
                isPoint = true;
                counterPoint++;
                if (counter == 0) {
                    return null;
                }
                if (counterPoint >= 2) {
                    return null;
                }
                if (counter == copiedStr.length() - 1) {
                    return null;
                }
            }
            if (copiedStr.charAt(counter) == 'e') {
                isE = true;
                counterE++;
                if (counter == 0) {
                    return null;
                }
                if (!Character.isDigit(copiedStr.charAt(counter - 1)) || counterE >= 2) {
                    return null;
                }
                if (counter == copiedStr.length() - 1) {
                    return null;
                }
            }
            if (copiedStr.charAt(counter) == '-') {
                if (counter != 0) {
                    if (copiedStr.charAt(counter - 1) != 'e') {
                        return null;
                    }
                    if (counter == copiedStr.length() - 1) {
                        return null;
                    }
                }
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
