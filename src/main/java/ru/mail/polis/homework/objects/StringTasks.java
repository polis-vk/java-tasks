package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Если вы используете функции типа Double.valueOf() -- получите только половину тугриков.
     * Для полного количества тугриков надо парсить в ручную.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {
        Number value = null;
        if (str != null && !str.isEmpty()) {
            String copiedStr = new String(str);
            int counterPoint = 0, counterE = 0;
            boolean isDigit = false, isPoint = false, isE = false;
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
                        copiedStr = null;
                        break;
                    }
                }
                if (counterPoint >= 2 || counterE >= 2) {
                    copiedStr = null;
                    break;
                }
                if (str.charAt(counter) == '-') {
                    if ((counter == str.length() - 1 || str.charAt(counter + 1) == '-')) {
                        copiedStr = null;
                        break;
                    }
                    if (counter != 0 && isDigit && !isE) {
                        copiedStr = null;
                        break;
                    }
                }
            }
            if (copiedStr != null && !copiedStr.isEmpty()) {
                if (isE || isPoint) {
                    value = Double.valueOf(copiedStr);
                } else {
                    Long value1 = Long.valueOf(copiedStr);
                    if (value1 > (long) Integer.MAX_VALUE || value1 < (long) Integer.MIN_VALUE) {
                        value = Long.valueOf(copiedStr);
                    } else {
                        value = Integer.valueOf(copiedStr);
                    }
                }
            }
        }
        return value;
    }
}
