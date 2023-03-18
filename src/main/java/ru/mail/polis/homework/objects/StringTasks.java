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
        String copiedStr = new String(str);
        int counterPoint = 0;
        int counterE = 0;
        boolean isDigit = false;
        boolean isPoint = false;
        boolean isE = false;
        for (int counter = 0; counter < str.length(); counter++) {
            if (Character.isLetter(str.charAt(counter)) && str.charAt(counter) != 'e') {
                copiedStr = copiedStr.replace(Character.toString(str.charAt(counter)), "");
            }
            if (Character.isDigit(str.charAt(counter))) {
                isDigit = true;
            }
            if (str.charAt(counter) == '.') {
                isPoint = true;
                counterPoint++;
            }
            if (str.charAt(counter) == 'e') {
                isE = true;
                counterE++;
                if (counter == str.length() - 1) {
                    return null;
                }
            }
            if (counterPoint >= 2 || counterE >= 2) {
                return null;
            }
            if (str.charAt(counter) == '-') {
                if ((counter == str.length() - 1 || str.charAt(counter + 1) == '-')) {
                    return null;
                }
                if (counter != 0 && isDigit && !isE) {
                    return null;
                }
            }
        }
        if (!copiedStr.isEmpty()) {
            if (isE || isPoint) {
                return Double.valueOf(copiedStr);
            }
            if (Long.valueOf(copiedStr) > (long) Integer.MAX_VALUE || Long.valueOf(copiedStr) < (long) Integer.MIN_VALUE) {
                return Long.valueOf(copiedStr);
            }
            return Integer.valueOf(copiedStr);
        }
        return null;
    }
}
