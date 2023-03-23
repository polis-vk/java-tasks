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
        int minusAppearanceAfterE = 0;
        int digitCount = 0;
        boolean minusAfterE = true;
        boolean validMinus = true;
        boolean validE = true;
        int length = str.length();
        boolean flagCastToDouble = false;
        StringBuilder tempStr = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // считаем количество 'e' в строке
            if (str.charAt(i) == 'e') {
                eAppearance++;
                // проверяем чтобы e не стояло в начале/конце
                if (i == 0 || i == str.length() - 1) {
                    validE = false;
                }
            }
            // считаем количество '.' в строке
            if (str.charAt(i) == '.') {
                dotAppearance++;
            }
            if (str.charAt(i) == '-') {
                // проверяем, что минус не стоит в конце
                if (i == str.length() - 1) {
                    validMinus = false;
                }
                // считаем количество '-' которые не после 'e'
                if (eAppearance == 0) {
                    minusAppearance++;
                }
                // считаем количество '-' которые после 'e'
                if (eAppearance > 0) {
                    minusAppearanceAfterE++;
                }
                // проверяем стоит ли минус после 'e', если он встречается после цифр
                if (eAppearance == 0 && digitCount > 0) {
                    minusAfterE = false;
                }
            }
            // флаг для проверки - кастить ли к Double (если . или 'e' - ДА)
            if (str.charAt(i) == '.' || str.charAt(i) == 'e') {
                flagCastToDouble = true;
            }
            // считаем число цифр
            if (Character.isDigit(str.charAt(i))) {
                digitCount++;
            }
            // добавляем в SB валидный элемент
            if (Character.isDigit(str.charAt(i)) || str.charAt(i) == '-' || str.charAt(i) == '.' || str.charAt(i) == 'e') {
                tempStr.append(str.charAt(i));
            }
        }
        String resultStr = tempStr.toString();
        // проверяем полученную строку, используя все переменные для проверок
        if (eAppearance <= 1 && minusAfterE && dotAppearance <= 1 && minusAppearance <= 1 && minusAppearanceAfterE < 2 && validE && validMinus) {
            // если проверки пройдены выбираем числовой тип, к которому нужно привести строку
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
        return null;
    }
}
