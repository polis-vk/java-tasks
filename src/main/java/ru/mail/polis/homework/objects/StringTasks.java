package ru.mail.polis.homework.objects;

public class StringTasks {

    /**
     * Убрать все лишние символы из строки и вернуть получившееся число.
     * Разрешенные символы: цифры, '-', '.', 'e'
     * Если '.' и 'e' больше чем 1, возвращаем null
     * Правила на '-' является валидность числа. --3 не валидно. -3e-1 валдино
     * Любой класс-обертка StringTasksTest над примитивами наследуется от Number
     * Нельзя использовать функции Double.valueOf() и другие такие же.
     * <p>
     * Работайте со строкой, НЕ надо ее переводить в массив байт (это можно использовать только для цикла)
     * У класса Character есть полезные методы, например Character.isDigit()
     */
    public static Number valueOf(String str) {

        StringBuilder sb = new StringBuilder();
        int kMinus = 0;
        int ke = 0;
        int kDot = 0;

        for (int i = 0; i < str.length(); i++) {

            if (Character.isDigit(str.charAt(i))) {
                sb.append(str.charAt(i));
            }

            if (str.charAt(i) == '.') {
                kDot++;
                sb.append(str.charAt(i));
                if (kDot > 1) {
                    return null;
                }
            }

            if (str.charAt(i) == 'e') {
                ke++;
                sb.append(str.charAt(i));
                if (ke > 1) {
                    return null;
                }
            }

            if (str.charAt(i) == '-') {
                kMinus++;
                if (((kMinus == 1) && (sb.length() == 0)) || (((kMinus == 1) || (kMinus == 2)) && (sb.charAt(sb.length() - 1) == 'e'))) {
                    sb.append(str.charAt(i));
                } else {
                    return null;
                }
            }

        }
        if ((sb.charAt(sb.length() - 1) == 'e') || (sb.charAt(sb.length() - 1) == '-') || (sb.charAt(sb.length() - 1) == '.')) {
            return null;
        }


        if ((kDot == 1) || (ke == 1)) {
            return Double.parseDouble(sb.toString());
        }
        else {

            long number = Long.parseLong(sb.toString());
            if ((number <= Integer.MAX_VALUE) && (number >= Integer.MIN_VALUE)) {
                return (int) number;
            } else {
                return number;
            }
        }


    }
}
