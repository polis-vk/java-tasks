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
        // переменные для проверки валидности потенциально переводимой в число строки
        int eAppearance = 0;
        int dotAppearance = 0;
        int minusAppearance = 0;
        boolean minusAfterE = true;
        boolean validEnding = true;
        //
        int length = str.length();
        boolean flagCastToDouble = false;
        StringBuilder tempStr = new StringBuilder(str);
        for (int i = 0; i < length; i++) {
            if (tempStr.charAt(i) == 'e') {
                eAppearance++;
            }
            if (tempStr.charAt(i) == '.') {
                dotAppearance++;
            }
            if (tempStr.charAt(i) == '-' && i != 0) {
                minusAppearance++;
                if (eAppearance == 0) {
                    minusAfterE = false;
                }
            }
            if (i == length - 1 && (tempStr.charAt(i) == '-' || tempStr.charAt(i) == 'e')) {
                validEnding = false;
            }
            // флаг для проверки - кастить ли к Double (если . или 'e' - ДА)
            if (tempStr.charAt(i) == '.' || tempStr.charAt(i) == 'e') {
                flagCastToDouble = true;
            }
            if (!(Character.isDigit(tempStr.charAt(i)) || tempStr.charAt(i) == '-' || tempStr.charAt(i) == '.' || tempStr.charAt(i) == 'e')) {
                tempStr.delete(i, i + 1);
                i--;
                length--;
            }
        }
        String resultStr = tempStr.toString();
        if (eAppearance > 1 || !minusAfterE || dotAppearance > 1 || minusAppearance > 1 || !validEnding) {
            return null;
        } else {
            if (flagCastToDouble) {
                return Double.parseDouble(resultStr);
            } else {
                long result = Long.parseLong(resultStr);
                if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                    return result;
                } else {
                    return (int) result;
                }
            }
        }
    }
}
