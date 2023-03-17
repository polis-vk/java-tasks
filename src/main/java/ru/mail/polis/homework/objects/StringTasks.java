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
        if (str == "" || str == null) {
            return null;
        }
        String newStr = "";
        int digetCount = 0;
        int dotCount = 0;
        int eCount = 0;
        int minusCount = 0;
        for (int i = 0; i < str.length(); i++) {
            char strNumber = str.charAt(i);
            if (strNumber == '.' || strNumber == '-' || strNumber == 'e' || Character.isDigit(strNumber)) {
                switch (strNumber) {
                    case '.':
                        dotCount++;
                        break;
                    case '-':
                        if (i != 0 & digetCount > 0) {
                            if (str.charAt(i - 1) != 'e') {
                                return null;
                            }
                        }
                        minusCount++;
                        break;
                    case 'e':
                        eCount++;
                        break;
                    default:
                        digetCount++;
                }
                if (dotCount > 1 || minusCount > 1 || eCount > 1) {
                    return null;
                }
                newStr += strNumber;
            }
        }
        if (!newStr.contains(".")) {
            return Integer.valueOf(newStr);
        }
        return Double.valueOf(newStr);

    }
}
